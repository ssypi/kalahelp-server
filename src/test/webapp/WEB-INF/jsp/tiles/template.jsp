<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="head"/>
</head>
<body>
<div id="container">
    <div id="header"><tiles:insertAttribute name="header"/></div>
    <div id="body"><tiles:insertAttribute name="body"/></div>
    <div id="footer"><tiles:insertAttribute name="footer"/></div>
</div>
<tiles:insertAttribute name="javascript"/>
</body>
</html>