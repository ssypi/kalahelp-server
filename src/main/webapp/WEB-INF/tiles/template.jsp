<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="head"/>
</head>
<body>
<div id="container">
    <tiles:insertAttribute name="header"/>
    <div id="helpdesk">
        <div id="template">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
    <tiles:insertAttribute name="footer"/>
</div>
<tiles:insertAttribute name="javascript"/>
</body>
</html>