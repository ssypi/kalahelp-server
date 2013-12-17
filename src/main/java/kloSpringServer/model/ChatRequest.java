package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * Creates a ChatRequest object for the specified nickname.<br>
     * Generates a new ID for the chat and sets the date to current date
     * @param nickname nickname of the person who requested the chat
     */
    public ChatRequest(String nickname) {
        setId(generateId());
        setDate(new Date());
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

    @JsonIgnore
    public static int generateId() {
        Random rand = new Random();
        return rand.nextInt(Integer.MAX_VALUE);
    }
}
