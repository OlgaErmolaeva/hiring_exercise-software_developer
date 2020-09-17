package io.excercise.rss.services;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorService {

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
