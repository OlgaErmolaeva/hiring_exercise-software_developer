package io.excercise.rss.services.client;

import io.excercise.rss.services.Feed;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedRetrievingClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Feed> getFeeds(URI uri) {
        Rss rss = restTemplate.getForObject(uri, Rss.class);

        if (rss != null && rss.getChannel() != null && rss.getChannel().getItems() != null) {
            return rss.getChannel().getItems().stream()
                    .map(this::buildFeed)
                    .collect(Collectors.toList());
        } else {
            // TODO : logger.error
        }
        return Collections.emptyList();
    }

    private Feed buildFeed(Item item) {
        Feed feed = new Feed();
        feed.setLink(item.getLink());
        feed.setTitle(item.getTitle());

        return feed;
    }
}
