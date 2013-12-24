<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="chooser">
    <a href="chat/new/" class="nav-button choose-button">Chat</a>
    <a href="ticket/new/" class="nav-button choose-button">Ticket</a>
        <%--<button class="nav-button choose-button">Chat</button>--%>
        <%--<button class="nav-button choose-button">Ticket</button>--%>
    <div id="message">
        <c:out value="${message}"/>
    </div>
</div>


