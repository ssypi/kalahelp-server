package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kalamies
 * Date: 21.11.2012
 * Time: 21:10
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsItem implements Serializable {
    private static final long serialVersionUID = 3823808L;
    private String writer;
    private String content;
    private Date date;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NewsItem() {
//        this("Nobody","Never","Empty"); // initialize variables
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsItem newsItem = (NewsItem) o;

        if (!content.equals(newsItem.content)) return false;
        if (!writer.equals(newsItem.writer)) return false;

        return true;
    }

    public NewsItem(String writer, String content) {
        this.writer = writer;
        this.content = content;
        this.date = new Date();
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * if date is null, sets date to current time and returns that
     * @return Date date
     */
    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

//    public void setDate(String date) {
//        // TODO: parse java.util.Date from String
//        this.date = new Date();
//    }

    public void setDate(Date date) {
        this.date = date;
    }
}
