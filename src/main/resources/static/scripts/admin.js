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
    $("p").remove();
    var instanceID = $("input[type=hidden]").val();
    var icons = document.getElementsByTagName("i");
    $("#usergrade").empty();
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
    $("p").remove();
    $("#usergrade").empty();
    var instanceID = $("input[type=hidden]").val();
    var user = $(event.target).text();
    $("#usergrade").append("<input id='user' type='hidden' value='" +
        user + "'/>");
    $.get("http://localhost:8080/room/" + instanceID + "/getUserData/" + user, function (data) {
        for (var i = 0; i < data.length; i++) {
            var dataDiv = $("#usergrade");
            dataDiv.append("<div id='div" + i +"'></div>");
            if (data[i].deleted === "N") {
                var id = "#div" + i;
                $(id).append("<i class='delete fa fa-trash'></i>");
                $(id).append("<span class='heading'>Element #" + (i + 1) + "</span><input type='hidden' value='" +
                    data[i].elementID + "'/><br>");
                $(id).append("<i class='edit fa fa-pencil'></i>");
                $(id).append("<span class='grade'>Grade: " + data[i].gradeScore + "</span><br>");
            }
        }
        $(".delete").click(deleteUserGrade);
        $(".edit").click(generateEdit);
    });
}

function generateEdit() {
    $("p").remove();
    $("input[type=number]").remove();
    $("button").remove();
    var clickedIcon = $(event.target);
    clickedIcon.parent().append("<input type='number'/>");
    clickedIcon.parent().append("<button type='button'>Edit</button>");
    $("button").click(editGrade);
}

function editGrade() {
    var grade = Number($(event.target).prev().val());
    if (grade < 1 || grade > 10) {
        $("h1").after("<p>Please enter a grade between 1 and 10</p>");
        return;
    }
    $(event.target).prev().val("");
    var div = $(event.target).parent().attr("id");
    var divId = "#" + div + " input[type=hidden]";
    var elementID = $(divId).val();
    var user = $("#user").val();
    $.get("http://localhost:8080/room/editGrade/" + elementID + "/" + grade + "/" + user, function () {
        var gradeId = "#" + div + " span";
        document.querySelectorAll(gradeId)[1].textContent = "Grade: " + grade;
        $("input[type=number]").remove();
        $("button").remove();
    });
}

function deleteUserGrade() {
    $("p").remove();
    var elementID = $(event.target).next().next().val();
    var user = $("#user").val();
    $(event.target).parent().empty();
    $.get("http://localhost:8080/room/deleteGrade/" + user + "/" + elementID);
}

$(document).ready(function () {
    getRoomUsers();
    setInterval(getRoomUsers, 2000);
});

