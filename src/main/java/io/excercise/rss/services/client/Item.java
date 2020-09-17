package io.excercise.rss.services.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "link")
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
