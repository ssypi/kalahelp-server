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
    private Integer writer;
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

    public NewsItem(Integer writer, String content) {
        this(writer, "", content);
        //todo generate current date
        this.date = new Date();
    }


    public NewsItem(Integer writer, String date, String content) {
        this.writer = writer;
        // todo: parse Date from String
//        this.date = date;
        this.date = new Date();
        this.content = content;
    }

    public Integer getWriter() {
        return writer;
    }

    public void setWriter(Integer writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
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
