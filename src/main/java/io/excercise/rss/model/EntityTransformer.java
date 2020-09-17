package io.excercise.rss.model;

import io.excercise.rss.api.FrequencyResponse;
import io.excercise.rss.services.Feed;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityTransformer {

    public List<FeedEntity> toEntities(List<Feed> feeds) {
        if (feeds == null) {
            return Collections.emptyList();
        }
        return feeds.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public FeedEntity toEntity(Feed feed) {
        FeedEntity feedEntity = new FeedEntity();

        feedEntity.setTitle(feed.getTitle());
        feedEntity.setLink(feed.getLink());
        feedEntity.setFrequency(feed.getFrequency());

        return feedEntity;
    }

    private List<Feed> toFeeds(List<FeedEntity> feedEntities) {
        if (feedEntities == null) {
            return Collections.emptyList();
        }
        return feedEntities.stream()
                .map(this::toFeed)
                .collect(Collectors.toList());
    }

    public Feed toFeed(FeedEntity entity) {
        Feed feed = new Feed();
        feed.setTitle(entity.getTitle());
        feed.setLink(entity.getLink());
        feed.setFrequency(entity.getFrequency());

        return feed;
    }

    public FrequencyResponse toResponse(AnalysisResult analysisResult) {
        FrequencyResponse response = new FrequencyResponse();
        response.setStatus(analysisResult.getStatus());
        response.setTopNews(toFeeds(analysisResult.getTopNews()));

        return response;
    }


}
