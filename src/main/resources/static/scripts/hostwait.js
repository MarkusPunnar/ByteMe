function getConnectedUsers() {
    var roomID = $("#roomID").text();
    $.get("http://localhost:8080/session/" + roomID + "/getUsers", function (data) {
        var displayedUsers = document.querySelectorAll(".roomuser").length;
        for (var i = displayedUsers; i < data.userCount; i++) {
            $("p+div").append("<p class='roomuser'>" + data.usernames[i] + "</p>");
        }
    });
}

setInterval(getConnectedUsers, 3000);