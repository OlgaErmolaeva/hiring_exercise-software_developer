package io.excercise.rss.services.client;

import io.excercise.rss.services.Feed;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class FeedRetrievingClient {
    private static final Logger logger = getLogger(FeedRetrievingClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public List<Feed> getFeeds(URI uri) {
        Rss rss;
        try {
            rss = restTemplate.getForObject(uri, Rss.class);
        } catch (ResourceAccessException e) {
            logger.error("Impossible to retrieve rss.", e);
            return Collections.emptyList();
        }

        if (rss != null && rss.getChannel() != null && rss.getChannel().getItems() != null) {
            return rss.getChannel().getItems().stream()
                    .map(this::buildFeed)
                    .collect(Collectors.toList());
        } else {
            logger.error("Response from URI {} is empty or null.", uri);
            return Collections.emptyList();
        }
    }

    private Feed buildFeed(Item item) {
        Feed feed = new Feed();
        feed.setLink(item.getLink());
        feed.setTitle(item.getTitle());

        return feed;
    }
}
