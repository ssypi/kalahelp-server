package kloSpringServer.data;

import kloSpringServer.model.NewsItem;

import java.util.List;

public interface NewsDao {
    public NewsItem getNewsById(int newsId);
    public List<NewsItem> getLatestNews(int count);
    public NewsItem addNews(NewsItem newsItem);
    boolean deleteNewsById(int newsId);
}
