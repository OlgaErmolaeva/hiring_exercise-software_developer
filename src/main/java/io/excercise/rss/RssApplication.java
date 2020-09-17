package io.excercise.rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class RssApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssApplication.class, args);
    }

}
