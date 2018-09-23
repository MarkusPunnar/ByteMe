function checkForStart() {
    var roomID = $("input[type=hidden]").attr("value");
    $.get("http://localhost:8080/session/" + roomID + "/status", function (data) {
        console.log(data)
    })
}

setInterval(checkForStart, 3000);
