package io.excercise.rss.requestqueue;


import java.io.Serializable;
import java.net.URI;
import java.util.Set;

public class RequestMessage implements Serializable {
    private static final long serialVersionUID = -295422703255886286L;

    private String requestId;
    private Set<URI> urisToAnalyse;

    // Must be constructor without arguments for message converter
    public RequestMessage() {
    }

    public RequestMessage(String requestId, Set<URI> urisToAnalyse) {
        this.requestId = requestId;
        this.urisToAnalyse = urisToAnalyse;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Set<URI> getUrisToAnalyse() {
        return urisToAnalyse;
    }

    public void setUrisToAnalyse(Set<URI> urisToAnalyse) {
        this.urisToAnalyse = urisToAnalyse;
    }
}
