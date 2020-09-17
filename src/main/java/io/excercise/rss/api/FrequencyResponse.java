package io.excercise.rss.api;

import io.excercise.rss.model.FeedEntity;
import io.excercise.rss.model.Status;
import io.excercise.rss.services.Feed;

import java.util.List;

public class FrequencyResponse {

    private Status status;
    private List<Feed> topNews;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Feed> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<Feed> topNews) {
        this.topNews = topNews;
    }
}
