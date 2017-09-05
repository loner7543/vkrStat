<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 011 11.07.17
  Time: 0:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Помощь</title>
    <script type="text/javascript" src="<c:url value="/js/react.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/react-dom.min.js" />"></script>
</head>
<body>
<ul>
    <li><a>Главная</a></li>
    <li><a href="/krugstat/Statistics">Расчет статистики></a></li>
    <li><a href="/krugstat//Profile">Расчет профиля</a></li>
    <li><a href="/krugstat//Help">Помощь</a></li>
</ul>
<div id="app"></div>
</body>
<script>
    ReactDOM.render(
               React.DOM.p(null,"Это абзац"),
                document.getElementById("app")
            );
</script>
</html>
