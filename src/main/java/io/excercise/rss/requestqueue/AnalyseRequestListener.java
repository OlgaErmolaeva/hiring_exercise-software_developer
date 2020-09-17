package io.excercise.rss.requestqueue;

import io.excercise.rss.services.FeedAnalysingService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class AnalyseRequestListener {
    private static Logger logger = getLogger(AnalyseRequestListener.class);

    @Autowired
    private FeedAnalysingService feedAnalysingService;

    @JmsListener(destination = "requestQueue", containerFactory = "concurrentContainerFactory")
    public void onMessage(RequestMessage message) {
        logger.debug("Received message with requestId: {}", message);
        feedAnalysingService.analyseURLsForRequest(message.getRequestId(), message.getUrisToAnalyse());

    }
}
