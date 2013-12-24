<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Helpdesk web-client</title>
    <link rel="stylesheet" href="../css/reset.css">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/main.css">
    <link rel="stylesheet" href="css/helpdesk.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">
</head>
<body>
<div id="container">

    <header id="header">
        <a href="#">
            <div class="arrow-left nav-arrow"></div>
            <!--<img id="nav-image" src="img/home-icon-hi.png">-->
            <h2 class="nav-title" data-bind="text: title"></h2>
        </a>

        <!-- ko with: currentTemplate -->
        <h2 class="nav-title" data-bind="text: '> ' + title"></h2>
        <!-- /ko -->
    </header>

    <div id="helpdesk" data-bind="with: currentTemplate">
        <div id="template" data-bind="template: { name: template, data: data}"></div>
    </div>
    <footer id="footer"></footer>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.0.0.js"></script>
<script src="js/lib/koExternalTemplateEngine_all.js"></script>
<script src="js/model/ticket.js"></script>
<script src="js/viewmodel/chooser-viewmodel.js"></script>
<script src="js/viewmodel/form-viewmodel.js"></script>
<script src="js/viewmodel/chat-viewmodel.js"></script>
<script src="js/viewmodel/ticket-viewmodel.js"></script>
<script src="js/gateway.js"></script>
<script src="js/main.js"></script>
</body>
</html>