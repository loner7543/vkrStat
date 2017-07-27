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
</head>
<body class="wraper">

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
    <ul id="fileNamesList">

    </ul>
</div><br>
<input type="button" value="Рассчет профиля" onclick="calculateProfile()" class="btn"><br>
    <div id="amplitudesHistogram"></div><br>
<div id="int"></div>
<script>
    var url = "save";
    var getStstUrl = "calculate";


    function sendToServer() {
        var data = new FormData();
        var formFiles = document.getElementById("my_file");
        var files = formFiles.files;
        debugger;
        for (var i = 0;i<files.length;i++){
            data.append('file-'+i, files[i]);
        }
        $.ajax({
            url: 'save',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(data){
                alert('Файл успешно отправлен на сервер');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

    }

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

                    var amplitudes = json["amplitudes"];
                    var xLabelData = 1;
                    var xLabelValues =[];
                    for(var i = 0;i<amplitudes.length;i++){
                        xLabelValues.push(''+xLabelData);
                        xLabelData++;
                    }
                    var layout = {
                        title: 'Гистограмма амплитуд',
                        showlegend: true,
                        xaxis: {
                            tickangle: -45
                        },
                        yaxis: {
                            zeroline: false,
                            gridwidth: 2
                        },
                        bargap :0.05

                    };
                    var graphData = [
                        {
                            x: xLabelValues,
                            y:amplitudes,
                            type: 'bar',
                            name:'Амплитуды'
                        }
                    ];
                    Plotly.newPlot('amplitudesHistogram', graphData,layout);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }

        });
    }
</script>
</body>
</html>
