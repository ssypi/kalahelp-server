<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="ticket-form">
    <form:form method="POST" action="../" cssClass="pure-form pure-form-stacked">
        <fieldset>
            <legend>Support Ticket</legend>

            <div class="pure-g-r">
                <div class="pure-u-1-2">
                    <label for="senderName">Name</label>
                    <input id="senderName" placeholder="Your name" type="text" tabindex="1"
                           required autofocus="">
                </div>

                <div class="pure-u-1-2">
                    <label for="senderEmail">E-mail</label>
                    <input id="senderEmail" type="email" placeholder="user@host.com" type="text"
                           tabindex="2" required>
                </div>

                <div class="pure-u-1-2">
                    <label for="subject">Subject</label>
                    <input id="subject" placeholder="Short title for your ticket" type="text"
                           tabindex="3">
                </div>

                <div class="pure-u-1-2">
                    <label for="category">Category</label>
                    <form:select path="category" items="${categoryList}"/>
                    </select>
                </div>
            </div>

            <div class="pure-g">
                <div class="pure-u-1-2">
                    <label for="message">Message</label>
                    <textarea id="message" data-bind="value: message"
                              placeholder="Explanation of your problem."
                              type="text" cols="70" rows="5"
                              tabindex="4" required></textarea>
                </div>
            </div>
        </fieldset>
        <!--<div class="pure-controls">-->
            <button type="submit" class="pure-button pure-button-primary" data-bind="enable: isEnabled">
                Submit
                <!-- ko if: (isLoading() == true) -->
                <img src="img/ajax-loader.gif"/>
                <!-- /ko -->
            </button>
            <button class="pure-button pure-button-secondary" type="button" data-bind="click: pressCancel">
                Back
            </button>
        <!--</div>-->
        <form:errors path="*" element="div"/>
    </form:form>
    <div class="progress">
        <c:out value="${message}"/>
    </div>
</div>
