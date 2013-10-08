package kloSpringServer.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kalamies
 * Date: 21.11.2012
 * Time: 21:10
 */
public class NewsItem implements Serializable {
    private static final long serialVersionUID = 3823808L;
    private String writer;
    private String content;
    private Date date;


    public NewsItem() {
        this("Nobody","Never","Empty"); // initialize variables
    }


    public NewsItem(String writer, String content) {
        this(writer, "", content);
        //todo generate current date
        this.date = new Date();
    }


    public NewsItem(String writer, String date, String content) {
        this.writer = writer;
        // todo: parse Date from String
//        this.date = date;
        this.date = new Date();
        this.content = content;
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

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        // TODO: parse java.util.Date from String
        this.date = new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
