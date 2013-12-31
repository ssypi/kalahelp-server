<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="subTitle" value="Tiketti"/>

<div id="ticket-form">
    <form:form modelAttribute="ticket" method="POST" action="ticket/" cssClass="pure-form pure-form-stacked">
        <fieldset>
            <legend>Support Ticket</legend>
            <div class="pure-g-r">
                <div class="pure-u-1-2">
                    <label for="senderName">Name</label>
                    <input name="senderName" id="senderName" placeholder="Your name" type="text" tabindex="1"
                           required autofocus="">
                    <form:errors path="senderName" cssClass="error"/>
                </div>

                <div class="pure-u-1-2">
                    <label for="senderEmail">E-mail</label>
                    <input name="senderEmail" id="senderEmail" type="email" placeholder="user@host.com" type="text"
                           tabindex="2" required>
                    <form:errors path="senderEmail" cssClass="error"/>
                </div>

                <div class="pure-u-1-2">
                    <label for="subject">Subject</label>
                    <input name="subject" id="subject" placeholder="Short title for your ticket" type="text"
                           tabindex="3">
                    <form:errors path="subject" cssClass="error"/>
                </div>

                <div class="pure-u-1-2">
                    <form:label path="category">Category</form:label>
                    <form:select path="category" items="${categoryList}"/>
                </div>
            </div>

            <div class="pure-g">
                <div class="pure-u-1-2">
                    <label for="message">Message</label>
                    <textarea name="message" id="message"
                              placeholder="Explanation of your problem."
                              type="text" cols="70" rows="5"
                              tabindex="4" required></textarea>
                    <form:errors path="message" cssClass="error"/>
                </div>
            </div>
        </fieldset>
        <button type="submit" class="pure-button pure-button-primary">
            Submit
        </button>
        <a href="" class="pure-button pure-button secondary">Back</a>
        <%--<button class="pure-button pure-button-secondary" type="button" data-bind="click: pressCancel">--%>
        <%--Back--%>
        <%--</button>--%>
        <form:errors path="*" element="div" cssClass="errorBox"/>
    </form:form>
    <div class="progress">
        <c:out value="${message}"/>
    </div>
</div>
