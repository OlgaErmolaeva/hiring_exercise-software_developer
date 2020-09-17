package io.excercise.rss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String requestId;
    @Column
    private Status status;
    @OneToMany
    private List<FeedEntity> topNews;

    public AnalysisResult() {
    }

    public AnalysisResult(String requestId) {
        this.requestId = requestId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<FeedEntity> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<FeedEntity> topNews) {
        this.topNews = topNews;
    }
}
