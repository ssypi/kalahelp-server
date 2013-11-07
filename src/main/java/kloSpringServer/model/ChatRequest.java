package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Random;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRequest {
    private String nickname;
    private int id;
    private Date date;

    public ChatRequest() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static int generateId() {
        Random rand = new Random();
        return rand.nextInt(Integer.MAX_VALUE);
    }
}
