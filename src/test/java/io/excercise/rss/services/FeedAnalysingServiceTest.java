package io.excercise.rss.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedAnalysingServiceTest {

    @Autowired
    private FeedAnalysingService feedAnalysingService;

    @Test
    void get10TopFeeds() {
        // GIVEN
        //URL 1
        Feed feed_1 = new Feed();
        feed_1.setTitle("Biden, Facing Voters in a 2020 Rarity, Attacks Trump From a Battleground State - The New York Times."); // 4 Times
        feed_1.setLink("feed_1_link");

        Feed feed_2 = new Feed();
        feed_2.setTitle("Miss out on PS5 preorders? There's one for sale on eBay for $25,000 - CNET"); // 3 times
        feed_2.setLink("feed_2_link");

        Feed feed_3 = new Feed();
        feed_3.setTitle("SpaceX delays next Starlink satellite fleet launch due to rocket 'recovery issue' - Space.com"); // 2 times
        feed_3.setLink("feed_3_link");

        //URL 2
        Feed feed_4 = new Feed();
        feed_4.setTitle("Biden, Facing Voters in a 2020 Rarity, Attacks Trump From a Battleground State - The New York Times.");
        feed_4.setLink("feed_4_link");

        Feed feed_5 = new Feed();
        feed_5.setTitle("Miss out on PS5 preorders? There's one for sale on eBay for $25,000 - CNET");
        feed_5.setLink("feed_5_link");

        Feed feed_6 = new Feed();
        feed_6.setTitle("The New Apple Watch Measures Your Blood Oxygen. Now What? - The New York Times");
        feed_6.setLink("feed_6_link");

        //URL 3
        Feed feed_7 = new Feed();
        feed_7.setTitle("Biden, Facing Voters in a 2020 Rarity, Attacks Trump From a Battleground State - The New York Times.");
        feed_7.setLink("feed_7_link");

        Feed feed_8 = new Feed();
        feed_8.setTitle("'Big Brother' All-Stars Recap: [Spoiler] Evicted, Joins Season 22 Jury - TVLine");
        feed_8.setLink("feed_8_link");

        Feed feed_9 = new Feed();
        feed_9.setTitle("SpaceX delays next Starlink satellite fleet launch due to rocket 'recovery issue' - Space.com");
        feed_9.setLink("feed_9_link");

        //URL 4
        Feed feed_10 = new Feed();
        feed_10.setTitle("Biden, Facing Voters in a 2020 Rarity, Attacks Trump From a Battleground State - The New York Times.");
        feed_10.setLink("feed_10_link");

        Feed feed_11 = new Feed();
        feed_11.setTitle("Miss out on PS5 preorders? There's one for sale on eBay for $25,000 - CNET"); // 3 times
        feed_11.setLink("feed_11_link");

        //WHEN
        List<Feed> topFeeds = feedAnalysingService.get10TopFeeds(List.of(List.of(feed_1, feed_2, feed_3), List.of(feed_4, feed_5, feed_6) ,
                List.of(feed_7, feed_8, feed_9), List.of(feed_10, feed_11)));

        //THEN
        assertEquals("Biden, Facing Voters in a 2020 Rarity, Attacks Trump From a Battleground State - The New York Times.",topFeeds.get(0).getTitle());
        assertEquals(4,topFeeds.get(0).getFrequency());
        assertEquals("Miss out on PS5 preorders? There's one for sale on eBay for $25,000 - CNET",topFeeds.get(1).getTitle());
        assertEquals(3,topFeeds.get(1).getFrequency());
        assertEquals("SpaceX delays next Starlink satellite fleet launch due to rocket 'recovery issue' - Space.com",topFeeds.get(2).getTitle());
        assertEquals(2,topFeeds.get(2).getFrequency());
    }
}