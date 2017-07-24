var url = "save"
var getStstUrl = "calculate"
function UploadFile() {
    var files = document.getElementById("my_file").files;//массив файлов тут
    var  xhr = new XMLHttpRequest();
    xhr.open("POST",url,true);
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
            console.log(data);
            if(jqXHR.status==200)
            {
              console.log(data);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }

    });
}