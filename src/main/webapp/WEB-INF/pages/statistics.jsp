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
    <script
            src="http://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="<c:url value="/js/statistics.js"/>"></script>
</head>
<body>

<form:form method="post" action="save.html" enctype="multipart/form-data">
    <input type="file" id="my_file" name="files[]" multiple="multiple"> />
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
</div>
</body>

<script>
    var url = "save"
    var getStstUrl = "calculate"

    function UploadFile() {
        var files = document.getElementById("my_file").files;//массив файлов тут
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        xhr.send(files);
    }

    function calculateStatistics() {
        $.ajax({
            url: getStstUrl,
            data: "",
            type: 'POST',
            //dataType: "json",
            contentType: 'application/json', //charset=utf-8,
            success: function (data, textStatus, jqXHR) {
                if (jqXHR.status == 200) {
                    console.log(data);
                    var json = JSON.parse(data)
                    var sko = document.getElementById("sko");
                    var psist = document.getElementById("psist");
                    var psum = document.getElementById("psum");
                    var dov = document.getElementById("dov");
                    var PinPoint = document.getElementById("PinPoint");
                    var opt = document.getElementById("opt");

                    sko.innerText = sko.innerText+json["sko"];
                    psist.innerText = psist.innerText+json["syst"];
                    psum.innerText = psum.innerText+json["psum"];
                    dov.innerText = dov.innerText+json["dov"];
                    PinPoint.innerText = PinPoint.innerText+json["point"];
                    opt.innerText = opt.innerText+json["closest"];

                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }

        });
    }
</script>
</html>
