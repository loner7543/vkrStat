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
</head>
<body>
<label>Исходный файл для построения профиля: </label><br>
<label id="radius">Радиус фотоприемника: </label><br>
<label id="delta">Смещение осветителя: </label><br>
<label id="zmin">Zmin осветителя:</label><br>
<label id="step">Шаг осветителя: </label><br>
<label id="points">Количество точек на сечение: </label><br>
<label id="count">Количество сечений: </label><br>
<input type="button" value="Рассчитать профиль" onclick="calculateProfile()">
<input type="button" value="Рассчитать круглограмму">
<ul>


</ul>
<script>
    var calcProfileURL = "calculateProfile";
    function calculateProfile() {
        var data = {
            fileName:"BEM_120.DAT"// todo брать его с поля ввода сверху
        };
                $.ajax({
                    url: calcProfileURL,
                    data: JSON.stringify(data),
                    type: 'POST',
                    //dataType: "json",
                    contentType: 'application/json', //charset=utf-8,
                    success: function (json, textStatus, jqXHR) {
                        if(textStatus!=200){// todo почему
                            console.log(json);
                            var jsonObject = JSON.parse(json);
                            var radius = document.getElementById("radius");
                            var delta = document.getElementById("delta");
                            var zmin = document.getElementById("zmin");
                            var step = document.getElementById("step");
                            var points = document.getElementById("points");
                            var countLabel = document.getElementById("count");

                            radius.innerText = radius.innerText+jsonObject["surfaceRadius"];
                            delta.innerText = delta.innerText+jsonObject["emitterYOffset"];
                            zmin.innerText = zmin.innerText+jsonObject["initialEmitterHeightPosition"];
                            step.innerText = step.innerText+jsonObject["emitterHeightStep"];
                            points.innerText = points.innerText+jsonObject["discretePoints"];
                            countLabel.innerText = countLabel.innerText+jsonObject["radialProfilesNumber"];
                        }
                    },
                    error:function (jqXHR, textStatus, errorThrown) {
                        alert(errorThrown);
                    }
                })
    }
</script>
</body>
</html>
