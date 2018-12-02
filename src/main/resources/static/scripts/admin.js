function getRoomUsers() {
    var instanceID = $("#id").val();
    var newUrl = "http://localhost:8080/session/" + instanceID + "/getUsers";
    $.get(newUrl, function (data) {
        var userCount = data.userCount;
        if (userCount === 0) {
            var text = $("#userlist").text().trim();
            if (text === "Users that have been in this room" || text === "") {
                $("h3").remove();
                $("#userlist").append("<p class='nousers'>No users have been in this room.</p>");
            }
            return;
        }
        if ($("h3").length === 0) {
            $("#userlist").append("<h3>Users that have been in this room</h3>");
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
    var instanceID = $("#id").val();
    var icons = document.getElementsByClassName("user");
    for (var j = 0; j < icons.length; j++) {
        if ($(event.target)[0] === icons[j]) {
            var id = "#" + j;
            var user = $(id).text();
            $.get("http://localhost:8080/room/" + instanceID + "/delete/" + user);
            $(id).prev().remove();
            $(id).remove();
            $("#usergrade").html("");
        }
    }
    $("p").remove();
}

function getUserGrades() {
    var instanceID = $("#id").val();
    var user = $(event.target).text();
    $.get("http://localhost:8080/room/" + instanceID + "/getUserData/" + user, function (data) {
        $("#usergrade").text("");
        var dataDiv = $("#usergrade");
        dataDiv.append("<input id='clickeduser' type='hidden' value='" + user + "'/>");
        for (var i = 0; i < data.length; i++) {
            dataDiv.append("<div id='" + (i + 1) + "'></div>");
            var id = "#" + (i + 1);
            if (data[i].deleted === "N") {
                $(id).append("<i class='element fa fa-trash'></i><span class='heading'>Element #" + (i + 1) + "</span><br>");
                $(id).append("<i class='grade fa fa-pencil'></i><span class='grade'>Grade: " + data[i].grade + "</span><br>");
                $(".element").off("click", deleteElementData);
                $(".element").on("click", deleteElementData);
                $(".grade").off("click", editElementData);
                $(".grade").on("click", editElementData);
            }
        }
    });
}

function deleteElementData() {
    var instanceID = $("#id").val();
    var icons = document.getElementsByClassName("element");
    var clickedIcon = $(event.target)[0];
    var user = $("#clickeduser").val();
    for (var i = 0; i < icons.length; i++) {
        if (clickedIcon === icons[i]) {
            var text = icons[i].nextSibling.textContent;
            var id = "#" + text[text.length - 1];
            $(id).remove();
            $.get("http://localhost:8080/room/" + instanceID + "/deleteElementData/" + user + "/" + i);
        }
    }
}

function editElementData() {
    $(".editbutton").remove();
    $(".editgrade").remove();
    $("p").remove();
    var gradeText = $(event.target).prev().prev().text();
    var number = gradeText.length - 1;
    var id = "#" + gradeText[number];
    $(id).append("<input type='number' class='editgrade'/>");
    $(id).append("<button type='submit' class='editgrade editbutton'>Edit</button>");
    $(".editbutton").click(saveEdit);
}

function saveEdit() {
    var info = $(this).parent().attr("id");
    var instanceID = $("#id").val();
    var user = $("#clickeduser").val();
    var newGrade = $(".editbutton").prev().val();
    if (Number(newGrade) < 1 || Number(newGrade) > 10) {
        $("h1").append("<p>The grade must be between 1 and 10</p>");
        return;
    }
    if (newGrade !== "") {
        $.get("http://localhost:8080/room/" + instanceID + "/getUserData/" + user, function (data) {
            var numberId = Number(info);
            var newId = "#" + numberId + " span:nth-of-type(2)";
            var element = data[numberId - 1].elementID;
            $.get("http://localhost:8080/room/" + instanceID + "/" + element + "/edit/" + user + "/" + newGrade, function () {
                console.log($(newId));
                $(newId).text("Grade: " + newGrade);
            });
        });
        $("p").remove();
        $(".editbutton").remove();
        $(".editgrade").remove();
    }
}

$(document).ready(function () {
    getRoomUsers();
    setInterval(getRoomUsers, 2000);
});

