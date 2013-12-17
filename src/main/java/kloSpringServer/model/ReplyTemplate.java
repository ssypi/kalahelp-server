package kloSpringServer.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created with IntelliJ IDEA.
 * User: Evige
 * Date: 17.12.2013
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyTemplate {


    private String beginningPart = null;
    private String endPart = null;
    private String templateName = null;
    private String creator;

    public ReplyTemplate() {
    }

    public ReplyTemplate(String beginningPart, String endPart) {
        this.beginningPart = beginningPart;
        this.endPart = endPart;
    }

    public String getEndPart() {
        return endPart;
    }

    public void setEndPart(String endPart) {
        this.endPart = endPart;
    }

    public String getBeginningPart() {
        return beginningPart;
    }

    public void setBeginningPart(String beginningPart) {
        this.beginningPart = beginningPart;
    }

    public String getName() {
        return templateName;
    }

    public void setName(String templateName) {
        this.templateName = templateName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}

