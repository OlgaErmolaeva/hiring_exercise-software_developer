package io.excercise.rss.services;

import com.google.common.collect.Lists;
import io.excercise.rss.api.FrequencyResponse;
import io.excercise.rss.dao.AnalysisResultRepository;
import io.excercise.rss.model.AnalysisResult;
import io.excercise.rss.model.EntityTransformer;
import io.excercise.rss.model.Status;
import io.excercise.rss.services.client.FeedRetrievingClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FeedAnalysingService {
    private static final Logger logger = getLogger(FeedAnalysingService.class);
    private static final String NON_WORD_PATTERN = "[ \\W]*";

    @Value("${stop.word.list}")
    private List<String> stopWords;
    @Value("${source.title.list}")
    private List<String> sourceList;

    @Autowired
    private FeedRetrievingClient feedRetrievingClient;

    @Autowired
    private EntityTransformer transformer;

    @Autowired
    private AnalysisResultRepository frequencyRespDAO;

    public FrequencyResponse getAnalysisResult(String requestId) {
        AnalysisResult analysisResult = frequencyRespDAO.getByRequestId(requestId);
        return transformer.toResponse(analysisResult);
    }

    public void analyseURLsForRequest(String requestId, Set<URI> urisToAnalyse) {
        try {
            AnalysisResult analysisResult = new AnalysisResult(requestId);
            analysisResult.setStatus(Status.RUNNING);

            // Save immediately in case client will ask for his result just after he posted the request
            frequencyRespDAO.save(analysisResult);

            List<List<Feed>> allRetrievedFeeds = new ArrayList<>();

            for (URI uri : urisToAnalyse) {
                allRetrievedFeeds.add(feedRetrievingClient.getFeeds(uri));
            }

            if (allRetrievedFeeds.isEmpty()) {
                analysisResult.setStatus(Status.ERROR);
            } else {
                List<Feed> topFeeds = get10TopFeeds(allRetrievedFeeds);
                analysisResult.setStatus(Status.READY);
                analysisResult.setTopNews(transformer.toEntities(topFeeds));
            }

            frequencyRespDAO.save(analysisResult);
        } catch (Exception e) {
            logger.error("Caught exception", e);
        }
    }

    List<Feed> get10TopFeeds(List<List<Feed>> allRetrievedFeeds) {
        List<List<Feed>> cleanFeeds = allRetrievedFeeds.stream()
                .map(this::filterOutUselessCharacters)
                .collect(Collectors.toList());

        Map<String, CountTitleTuple> countTitleTupleMap = countWords(cleanFeeds);

        return get10TopFeedsFromTitleCountTuples(countTitleTupleMap);
    }

    private List<Feed> get10TopFeedsFromTitleCountTuples(Map<String, CountTitleTuple> countTitleTupleMap) {
        PriorityQueue<CountTitleTuple> tuplesQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.count, o1.count));
        tuplesQueue.addAll(countTitleTupleMap.values());

        List<Feed> topNews = new ArrayList<>();

        while (!tuplesQueue.isEmpty() && topNews.size() < 10) {   // Get top 10 from binary heap
            CountTitleTuple countTitleTuple = tuplesQueue.remove();
            if (countTitleTuple.count > 1) {  // 1 means it was only in 1 feed, so it is not in top news
                Feed feed = countTitleTuple.feed;
                feed.setFrequency(countTitleTuple.count);

                if (!topNews.contains(feed)) { // If topNews contains this feed taken by another popular word
                    topNews.add(feed);
                }
            }
        }

        return topNews;
    }

    private Map<String, CountTitleTuple> countWords(List<List<Feed>> feedsWithCleanedTiles) {
        Map<String, CountTitleTuple> wordToCountTitle = new HashMap<>();

        int i = 1;
        for (List<Feed> feedList : feedsWithCleanedTiles) {
            for (Feed feed : feedList) {
                for (String word : feed.getCleanedTitle()) {
                    CountTitleTuple tuple = wordToCountTitle.get(word);
                    if (tuple != null) {
                        if (tuple.count < i) { // Second condition is necessary not to count twice word in the same feed
                            tuple.count++;
                        }
                    } else {
                        wordToCountTitle.put(word, new CountTitleTuple(feed, 1));
                    }
                }
            }
            i++;
        }

        return wordToCountTitle;
    }

    private List<Feed> filterOutUselessCharacters(List<Feed> feeds) {
        feeds.stream()
                .filter(feed -> feed.getTitle() != null)
                .forEach(feed -> {
                            String title = feed.getTitle();
                            for (String source : sourceList) {
                                title = title.replaceAll(source, "");
                            }

                            title = title.toLowerCase();

                            List<String> titleArray = Lists.newArrayList(title.split(" "));
                            titleArray.removeAll(stopWords); // Simple solution without converting words to their first form and replacing synonyms

                            List<String> cleanedTitleArray =
                                    titleArray.stream()
                                            .map(word -> word.replaceAll(NON_WORD_PATTERN, ""))
                                            .filter(word -> !word.isBlank())
                                            .collect(Collectors.toList());

                            feed.setCleanedTitle(cleanedTitleArray);
                        }

                );
        return feeds;
    }

    private static class CountTitleTuple {
        private Feed feed;
        private int count;

        public CountTitleTuple(Feed feed, int count) {
            this.feed = feed;
            this.count = count;
        }
    }
}
