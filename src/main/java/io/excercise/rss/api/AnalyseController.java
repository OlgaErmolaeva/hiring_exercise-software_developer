package io.excercise.rss.api;

import io.excercise.rss.requestqueue.MessageSender;
import io.excercise.rss.requestqueue.RequestMessage;
import io.excercise.rss.services.FeedAnalysingService;
import io.excercise.rss.services.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AnalyseController {

    // TODO: add logger

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private MessageSender messageSender;
    //TODO : Service to get result from DB


    @PostMapping("/analyse/new")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String submitFeed(@RequestBody List<String> rssURLs) {
        if (rssURLs.size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided less then 2 URLs to analyse.");
            // logger.error
        }

        Set<URI> urisToAnalyse = rssURLs.stream()
                .map(s -> {
                    try {
                        return new URI(s);
                    } catch (URISyntaxException e) {
                        // logger.error
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided URL is in a wrong format.");
                    }
                })
                .collect(Collectors.toSet());

        String requestId = idGeneratorService.generateId();

        messageSender.sendMessage(new RequestMessage(requestId, urisToAnalyse));

        return requestId;
    }

    @GetMapping("/frequency/{id}")
    public FrequencyResponse getFrequency(@PathVariable String id) {
        return new FrequencyResponse();
    }
}
