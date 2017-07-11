var url = "save"
function UploadFile() {
    var files = document.getElementById("my_file").files;//массив файлов тут
    var  xhr = new XMLHttpRequest();
    xhr.open("POST",url,true);
    xhr.send(files);
}