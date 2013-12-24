<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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