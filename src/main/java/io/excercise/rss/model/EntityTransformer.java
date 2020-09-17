package io.excercise.rss.model;

import io.excercise.rss.services.Feed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityTransformer {

    public List<FeedEntity> toEntities(List<Feed> feeds) {
        return feeds.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public FeedEntity toEntity(Feed feed) {
        FeedEntity feedEntity = new FeedEntity();

        feedEntity.setTitle(feed.getTitle());
        feedEntity.setContent(feed.getContent());
        feedEntity.setLink(feed.getLink());
        feedEntity.setFrequency(feed.getFrequency());

        return feedEntity;
    }
}
