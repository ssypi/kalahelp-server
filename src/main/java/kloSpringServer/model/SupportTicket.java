package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kloSpringServer.ValidationException;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportTicket {
    private int ticketNumber;

    @NotNull @Size(max = 25)
    private String senderName;

    @NotNull @Email
    private String senderEmail;

    @NotNull @Size(max = 50)
    private String subject;

    private String status;
    private Date date;

    @NotNull @Size(max = 500)
    private String message;

    @NotNull
    private String category;

    private String reply;
    private Date replyDate;
    private String replyBy;

    public SupportTicket() {
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
        if (status == null) {
            return "New";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        if (date == null) {
            return new Date();
        }
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
        if (replyDate == null) {
            return new Date();
        }
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

    @Override
    @JsonIgnore
    public String toString() {
        return "SupportTicket{" +
                "ticketNumber=" + ticketNumber +
                ", senderName='" + senderName + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", senderEmail='" + senderEmail + '\'' +
                '}';
    }
}