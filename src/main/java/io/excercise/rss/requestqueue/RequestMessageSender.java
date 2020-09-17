package io.excercise.rss.requestqueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(RequestMessage requestMessage) {
        jmsTemplate.convertAndSend("requestQueue", requestMessage);
    }
}
