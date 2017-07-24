// var url = "save";
// var getStstUrl = "calculate";
// var calcProfileURL = "calculateProfile";
//
// function sendToServer() {
//     var data = new FormData();
//     var formFiles = document.getElementById("my_file");
//     var files = formFiles.files;
//     debugger;
//     for (var i = 0;i<files.length;i++){
//         data.append('file-'+i, files[i]);
//     }
//     $.ajax({
//         url: 'save',
//         data: data,
//         cache: false,
//         contentType: false,
//         processData: false,
//         type: 'POST',
//         success: function(data){
//             alert('Файл успешно отправлен на сервер');
//         },
//         error: function (jqXHR, textStatus, errorThrown) {
//             console.log(errorThrown);
//         }
//     });
//
// }
//
// function UploadFile() {
//     var files = document.getElementById("my_file").files;//массив файлов тут
//     var xhr = new XMLHttpRequest();
//     xhr.open("POST", url, true);
//     xhr.send(files);
// }
//
// function calculateStatistics() {
//     $.ajax({
//         url: getStstUrl,
//         data: "",
//         type: 'POST',
//         //dataType: "json",
//         contentType: 'application/json', //charset=utf-8,
//         success: function (data, textStatus, jqXHR) {
//             if (jqXHR.status == 200) {
//                 console.log(data);
//                 var json = JSON.parse(data)
//                 var sko = document.getElementById("sko");
//                 var psist = document.getElementById("psist");
//                 var psum = document.getElementById("psum");
//                 var dov = document.getElementById("dov");
//                 var PinPoint = document.getElementById("PinPoint");
//                 var opt = document.getElementById("opt");
//
//                 sko.innerText = sko.innerText+json["sko"];
//                 psist.innerText = psist.innerText+json["syst"];
//                 psum.innerText = psum.innerText+json["psum"];
//                 dov.innerText = dov.innerText+json["dov"];
//                 PinPoint.innerText = PinPoint.innerText+json["point"];
//                 opt.innerText = opt.innerText+json["closest"];
//
//             }
//         },
//         error: function (jqXHR, textStatus, errorThrown) {
//             console.log(errorThrown);
//         }
//
//     });
// }
//
// function calculateProfile() {
//
// }