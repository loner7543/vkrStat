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
    <title>Профиль</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/plotly/plotly.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/profile.css"/>">
    <script type="text/javascript" src="<c:url value="/js/profile.js"/>"></script>
</head>
<body>
<ul>
    <li><a>Главная</a></li>
    <li><a href="/krugstat/Statistics">Расчет статистики></a></li>
    <li><a href="/krugstat//Profile">Расчет профиля</a></li>
    <li><a href="/krugstat//Help">Помощь</a></li>
</ul>
<label>Исходный файл для построения профиля: </label><br>
<label id="radius">Радиус фотоприемника: </label><br>
<label id="delta">Смещение осветителя: </label><br>
<label id="zmin">Zmin осветителя:</label><br>
<label id="step">Шаг осветителя: </label><br>
<label id="points">Количество точек на сечение: </label><br>
<label id="count">Количество сечений: </label><br>
<input type="button" value="Рассчитать профиль" onclick="calculateProfile()">

<p>Настройки</p><br>
<p><input type="radio" id="gran" name="settings" value="grann">Гранность</p>
<p><input type="radio" id="voln" name="settings" value="voln">Волнистость</p><br>
<input type="button" value="Рассчитать круглограмму" onclick="calculateCruglogramm()"><br>
<h2>Отклонения</h2>
<ul id="cruglogrammeResult">
</ul>
<div id="circlePlot"></div>
</body>
</html>
