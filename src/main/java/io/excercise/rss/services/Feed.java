package io.excercise.rss.services;

import java.util.List;
import java.util.Objects;

public class Feed {

    private String title;
    private List<String> cleanedTitle;
    private String link;
    private Integer frequency;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCleanedTitle() {
        return cleanedTitle;
    }

    public void setCleanedTitle(List<String> cleanedTitle) {
        this.cleanedTitle = cleanedTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feed that = (Feed) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link);
    }
}
