<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<form:form method="POST" action="../">
    <table>
        <tr>
            <td><form:label path="senderName">Name</form:label></td>
            <td><form:input path="senderName"/></td>
        </tr>
        <tr>
            <td><form:label path="senderEmail">Email</form:label></td>
            <td><form:input path="senderEmail"/></td>
        </tr>
        <tr>
            <td><form:label path="message">message</form:label></td>
            <td><form:input path="message"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
<head>
    <title></title>
</head>
</html>
