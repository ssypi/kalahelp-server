package kloSpringServer.data;

import kloSpringServer.model.NewsItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 18.10.2013
 * Time: 23:57
 */
@SuppressWarnings("SpellCheckingInspection")
@ContextConfiguration (locations = {"/testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcNewsDaoTest {

    @Autowired
    public NewsDao newsDao;

    @Test
    @Transactional
    public void testGetNewsById() throws Exception {
        String content = "Kala kala kala mies, olipa kerran kuka ties.";
        String writer = "1";

        NewsItem newsItem = new NewsItem();
        newsItem.setContent(content);
        newsItem.setWriter(writer);

        NewsItem returnedNews = newsDao.addNews(newsItem);
        assertTrue(returnedNews.getId() != 0);

        NewsItem second = newsDao.getNewsById(returnedNews.getId());
        assertEquals(second, returnedNews);
    }

    @Test
    @Transactional
    public void testGetLatestNews() throws Exception {
        String content = "kala";
        String writer = "1";
        NewsItem newsItem = new NewsItem();
        newsItem.setContent(content);
        newsItem.setWriter(writer);

        for (int i = 0; i < 5; i++) {
            newsDao.addNews(newsItem);
            content += "kala";
            writer += 1;
            newsItem.setContent(content);
            newsItem.setWriter(writer);
        }

        List<NewsItem> newsList = newsDao.getLatestNews(5);
        assertTrue(newsList.size() == 5);
    }

    @Test
    @Transactional
    public void test() throws Exception {
        NewsItem news = newsDao.getNewsById(1);
        String expectedContent = "Kalamiehen testi_uutinen";
        assertThat(expectedContent, equalTo(news.getContent()));
        assertThat(1, equalTo(news.getId()));
    }

    @Test
    @Transactional
    public void addNewsShouldReturnId() throws Exception {
        String content = "Kala kala kala mies, olipa kerran kuka ties.";
        String writer = "1";

        NewsItem newsItem = new NewsItem();
        newsItem.setContent(content);
        newsItem.setWriter(writer);

        NewsItem returnedNews = newsDao.addNews(newsItem);

        assertTrue(returnedNews.getId() != 0);
    }
}
