var rowsCreated = 0;
var elementsDisplayed = 0;
var elementHeadings = ["Elements", "Total grades", "Average grade", "Top graded user"];


function createTables() {
    var id = $("#instanceID").val();
    $.get("http://localhost:8080/summary/users/" + id, function (users) {
        $.get("http://localhost:8080/summary/elements/" + id, function (elements) {
            var numberofusers = users.length;
            var numberofelements = elements.length;
            $("#userstable thead").append("<tr id='usersheadtr'></tr>");
            $("#usersheadtr").append("<th scope='col'>User</th>");
            for (var i = 0; i < numberofelements; i++) {
                $("#usersheadtr").append("<th scope='col'>Element #" + (i + 1) + "</th>");
            }
            for (var j = 0; j < numberofusers; j++) {
                $.get("http://localhost:8080/summary/grades/" + id + "/" + users[j].userID, function (grades) {
                    $("#userstable tbody").append("<tr></tr>");
                    var userstd = document.createElement("td");
                    var userID = grades[0].userID;
                    for (var i = 0; i < numberofusers; i++) {
                        if (users[i].userID === userID) {
                            userstd.append(users[i].displayname);
                        }
                    }
                    $("#userstable tbody tr")[rowsCreated].append(userstd);
                    for (var k = 0; k < numberofelements; k++) {
                        userstd = document.createElement("td");
                        if (grades[k].deleted === "N") {
                            userstd.append(grades[k].gradeScore);
                        }
                        else {
                            userstd.append("-");
                        }
                        $("#userstable tbody tr")[rowsCreated].append(userstd);
                    }
                    rowsCreated++;
                });
            }
            $("#elementstable thead").append("<tr id='elementsheadtr'></tr>");
            for (var u = 0; u < elementHeadings.length; u++) {
                $("#elementsheadtr").append("<th scope='col'>" + elementHeadings[u] + "</th>");
            }
            for (var m = 0; m < numberofelements; m++) {
                $.get("http://localhost:8080/summary/elementdetails/" + elements[m].elementID, function (details) {
                    $("#elementstable tbody").append("<tr></tr>");
                    var elementstd = document.createElement("td");
                    elementstd.append("Element #" + (elementsDisplayed + 1));
                    $("#elementstable tbody tr")[elementsDisplayed].append(elementstd);
                    for (var l = 0; l < 3; l++) {
                        elementstd = document.createElement("td");
                        elementstd.append(details[l]);
                        $("#elementstable tbody tr")[elementsDisplayed].append(elementstd);
                    }
                    elementsDisplayed++;
                });
            }
        });
    });

}

function tabSwitch() {
    $("#userstab").click(function () {
        $("#byelements").hide();
        $("#byusers").show();
        $("#userdiv").addClass("active");
        $("#userdiv").addClass("show");
        $("#elementdiv").removeClass("active");
        $("#elementdiv").removeClass("show");
    });
    $("#elementstab").click(function () {
        $("#byusers").hide();
        $("#byelements").show();
        $("#userdiv").removeClass("active");
        $("#userdiv").removeClass("show");
        $("#elementdiv").addClass("active");
        $("#elementdiv").addClass("show");
    });
}

$(document).ready(function () {
    createTables();
    tabSwitch();
});

