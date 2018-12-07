var numberofelements = 3;


function createTables() {
    var id = $("#instanceID").val();
    $.get("http://localhost:8080/summary/users/" + id, function (users) {
        var numberofusers = users.length;
        $("#userstable thead").append("<tr id='usersheadtr'></tr>");
        $("#usersheadtr").append("<th scope='col'>Users</th>");
        for (var i = 0; i < numberofelements; i++) {
            $("#usersheadtr").append("<th scope='col'>BLA</th>");
        }

        for (var j = 0; j < numberofusers; j++) {
            $("#userstable tbody").append("<tr></tr>");
            var userstd = document.createElement("td");
            userstd.append(users[j]);
            $("#userstable tbody tr")[j].append(userstd);
            for (var k = 0; k < numberofelements; k++) {
                var userstd = document.createElement("td");
                userstd.append("Grade");
                $("#userstable tbody tr")[j].append(userstd);
            }
        }
        $("#elementstable thead").append("<tr id='elementsheadtr'></tr>");
        for (var i = 0; i < numberofusers; i++) {
            $("#elementsheadtr").append("<th scope='col'>" + users[i] +
                "</th>");
        }

        for (var j = 0; j < numberofelements; j++) {
            $("#elementstable tbody").append("<tr></tr>");
            for (var k = 0; k < numberofusers; k++) {
                var elementstd = document.createElement("td");
                elementstd.append("Grade");
                $("#elementstable tbody tr")[j].append(elementstd);
            }
        }
    });
}

function tabSwitch() {
    $("#userstab").click(function () {
        $("#byelements").hide();
        $("#byusers").show();
        $("#userdiv").toggleClass("active");
        $("#userdiv").toggleClass("show");
        $("#elementdiv").toggleClass("active");
        $("#elementdiv").toggleClass("show");
    });
    $("#elementstab").click(function () {
        $("#byusers").hide();
        $("#byelements").show();
        $("#userdiv").toggleClass("active");
        $("#userdiv").toggleClass("show");
        $("#elementdiv").toggleClass("active");
        $("#elementdiv").toggleClass("show");
    });
}

$(document).ready(function () {
    createTables();
    tabSwitch();
});

