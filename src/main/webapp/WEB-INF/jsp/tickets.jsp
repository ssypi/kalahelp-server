<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: kala
  Date: 21.12.2013
  Time: 0:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>tickets</h2>

<c:forEach items="${ticketList}" var="ticket">
    Ticket#<c:out value="${ticket.ticketNumber}"/>
    <c:out value="${ticket.subject}"/>
    <br>
</c:forEach>

</body>
</html>
