package io.excercise.rss.services;

import io.excercise.rss.dao.AnalysisResultRepository;
import io.excercise.rss.dao.FeedRepository;
import io.excercise.rss.model.AnalysisResult;
import io.excercise.rss.model.EntityTransformer;
import io.excercise.rss.model.Status;
import io.excercise.rss.services.client.FeedRetrievingClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FeedAnalysingService {
    private static Logger logger = getLogger(FeedAnalysingService.class);

    @Autowired
    private FeedRetrievingClient feedRetrievingClient;

    @Autowired
    private EntityTransformer transformer;

    @Autowired
    private FeedRepository feedDAO;

    @Autowired
    private AnalysisResultRepository frequencyRespDAO;

    public void analyseURLsForRequest(String requestId, Set<URI> urisToAnalyse) {
        try {
            AnalysisResult analysisResult = new AnalysisResult(requestId);
            analysisResult.setStatus(Status.RUNNING);

            // Save immediately in case client will ask for his result just after he posted the request
            frequencyRespDAO.save(analysisResult);

            List<Feed> allRetrievedFeeds = new ArrayList<>();

            for (URI uri : urisToAnalyse) {
                allRetrievedFeeds.addAll(feedRetrievingClient.getFeeds(uri));
            }

            if (allRetrievedFeeds.isEmpty()) {
                analysisResult.setStatus(Status.ERROR);
            } else {
                List<Feed> topFeeds = getFeedsOrderedByFrequency(allRetrievedFeeds);
                analysisResult.setStatus(Status.READY);
                analysisResult.setTopNews(transformer.toEntities(topFeeds));
            }

            frequencyRespDAO.save(analysisResult);
        } catch (Exception e){
            logger.error("Caught exception" ,e);
        }
    }

    private List<Feed> getFeedsOrderedByFrequency(List<Feed> allRetrievedFeeds) {

        return List.of(allRetrievedFeeds.iterator().next());
    }
}
