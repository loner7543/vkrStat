<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 010 10.07.17
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Статистика</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/plotly/plotly.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-theme.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/ststistics.css"/>">
    <script type="text/javascript" src="<c:url value="/js/statistics.js"/>"></script>
</head>
<body class="wraper">
<ul>
    <li><a>Главная</a></li>
    <li><a href="/krugstat/Statistics">Расчет статистики></a></li>
    <li><a href="/krugstat//Profile">Расчет профиля</a></li>
    <li><a href="/krugstat//Help">Помощь</a></li>
</ul>

<form:form method="post" action="save.html" enctype="multipart/form-data">
    <input type="file" id="my_file" name="files[]" multiple="multiple">
    <br/><input type="submit" value="Upload"/>
</form:form>
<input type="button" value="Рассчитать статистику" onclick="calculateStatistics()">
<div id="statBlock">
    <label id="sko">Среднеквадратическое отклонение:</label><br>
    <label id="psist">Систематическая составляющая:</label><br>
    <label id="psum">Суммарная погрешность: </label><br>
    <label id="dov">Доверительный интервал: </label><br>
    <label id="PinPoint">Погрешность в точке: </label><br>
    <label id="opt">Ближайший к оптимальному профиль:</label><br>
    <label id="kxSqare"></label>
    <ul id="fileNamesList">

    </ul>
</div><br>
<input type="button" value="Рассчет профиля" onclick="onCalculateProfile()" class="btn-default"><br>
    <div id="amplitudesHistogram"></div><br>
<div id="int"></div>
</body>
</html>
