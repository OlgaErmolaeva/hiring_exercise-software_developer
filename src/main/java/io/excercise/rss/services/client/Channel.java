package io.excercise.rss.services.client;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {
    @XmlElement(name = "item")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
