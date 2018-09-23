function checkForStart() {
    var roomID = $("input[type=hidden]").attr("value");
    $.get("http://localhost:8080/session/" + roomID + "/status", function (data) {
        if (data === "true") {
            clearInterval(interval);
            $("body").load("http://localhost:8080/session/" + roomID + "/enterRoom");
        }
    })
}

var interval = setInterval(checkForStart, 3000);
