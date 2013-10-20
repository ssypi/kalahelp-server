package kloSpringServer.data;

import kloSpringServer.model.NewsItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 10.10.2013
 * Time: 0:36
 */
public interface NewsDao {
    public NewsItem getNewsById(int newsId);
    public List<NewsItem> getLatestNews(int count);
    public NewsItem addNews(NewsItem newsItem);
}
