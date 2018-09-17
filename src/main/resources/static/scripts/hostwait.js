function getConnectedUsers() {
    var roomID = $("#roomID").text();
    $.get("http://localhost:8080/session/" + roomID + "/getUsers");
}

setInterval(getConnectedUsers, 3000);