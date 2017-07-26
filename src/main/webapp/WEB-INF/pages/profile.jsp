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
    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/global.css"/>">
</head>
<body>
<label>Исходный файл для построения профиля: </label><br>
<label>Радиус фотоприемника: </label><br>
<label>Смещение осветителя: </label><br>
<label>Zmin осветителя:</label><br>
<label>Шаг осветителя: </label><br>
<label>Количество точек на сечение: </label><br>
<label>Количество сечений: </label><br>
<input type="button" value="Рассчитать круглограмму">
<ul>


</ul>
</body>
</html>
