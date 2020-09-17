package io.excercise.rss.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;

    @Column
    private String requestId;
    @Column
    private Status status;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FeedEntity> topNews;

    public AnalysisResult() {
    }

    public AnalysisResult(String requestId) {
        this.requestId = requestId;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
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
        if (topNews != null) {
            topNews.forEach(e -> e.setResult(this)); // For bidirectional Hibernate mapping
        }
        this.topNews = topNews;
    }
}
