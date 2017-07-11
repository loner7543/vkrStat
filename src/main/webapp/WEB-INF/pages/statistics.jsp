<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 010 10.07.17
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>11111Статистика</title>
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.4.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/statistics.js"/>"></script>
</head>
<body>

<form:form method="post" action="save.html" enctype="multipart/form-data">
    <input type="file" id="my_file" name="files[]" multiple="multiple"> />
    <br/><input type="submit" value="Upload" />
</form:form>
<input type="button" value="Добавить файл данных" onclick="UploadFile()">
</body>
</html>
