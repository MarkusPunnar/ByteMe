function getRoomUsers() {
    var instanceID = $("input[type=hidden]").val();
    var newUrl = "http://localhost:8080/session/" + instanceID + "/getUsers";
    $.get(newUrl, function (data) {
        var userCount = data.userCount;
        if (userCount === 0) {
            if ($("#userlist").text().trim() === "") {
                $("#userlist").append("<p class='nousers'>No users are currently in the room.</p>");
            }
            return;
        }
        var displayedUsers = document.querySelectorAll(".roomuser").length;
        for (var i = displayedUsers; i < userCount; i++) {
            if ($("#userlist p") !== null) {
                $(".nousers").remove();
            }
            $("#userlist").append("<i class='user fa fa-trash'></i><span id='" + i +
                "' class='roomuser'>" + data.usernames[i] + "</span><br>");
            var spanID = "#" + i;
            $(spanID).on("click", getUserGrades);
            $(".user").off("click", deleteData);
            $(".user").on("click", deleteData);
        }
    })
}

function deleteData() {
    var instanceID = $("input[type=hidden]").val();
    var icons = document.getElementsByTagName("i");
    for (var j = 0; j < icons.length; j++) {
        if ($(event.target)[0] === icons[j]) {
            var id = "#" + j;
            var user = $(id).text();
            $.get("http://localhost:8080/room/" + instanceID + "/delete/" + user);
            $(id).prev().remove();
            $(id).remove();
        }
    }
}

function getUserGrades() {
    var instanceID = $("input[type=hidden]").val();
    var user = $(event.target).text();
    $.get("http://localhost:8080/room/" + instanceID + "/getUserData/" + user, function (data) {
        for (var i = 0; i < data.length; i++) {
            var dataDiv = $("#usergrade");
            dataDiv.append("<p class='heading'>Element #" + (i + 1) + "</p>");
            dataDiv.append("<p class='grade'>Grade: " + data[i] + "</p>");
        }
    });
}

$(document).ready(function () {
    getRoomUsers();
    setInterval(getRoomUsers, 2000);
});

