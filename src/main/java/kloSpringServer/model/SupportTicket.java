package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kloSpringServer.controller.ValidationException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kala
 * Date: 9.10.2013
 * Time: 1:33
 */
public class SupportTicket {
    private int ticketNumber;
    private String senderName;
    private String senderEmail;
    private String subject;
    private String status;
    private Date date;
    private String message;
    private String reply;
    private Date replyDate;
    private String category;
    private String replyBy;

    public SupportTicket() {
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    @JsonIgnore
    public void validate() throws ValidationException {
        if (senderEmail == null || senderEmail.length() < 5 || !senderEmail.contains("@")) {
            throw new ValidationException("Invalid email-address.");
        }
        if (message == null || senderName == null) {
            throw new ValidationException("All required fields not filled");
        }
    }
}