package io.excercise.rss.services.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rss {
    @XmlElement(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
