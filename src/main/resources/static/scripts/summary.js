function getTimeStamps() {
    var roomID = $("#roomID").attr("value");
    $.get("http://localhost:8080/room/" + roomID + "/timestamps", function (data) {
        $("#creation").text(data.creation);
        $("#closure").text(data.closure);
    });
}

$(document).ready(function () {
    setInterval(getTimeStamps, 1000);
});