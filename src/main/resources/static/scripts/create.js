function addTextfields() {

    var numberOfElements = Number($("input[type=number]").val());
    var formElement = $("#create");

    $("p").remove();
    $("input[type=number]").remove();
    $("button").remove();

    for (var i = 0; i < numberOfElements; i++) {
        formElement.append("<div></div>");
        var divNumber = i+1;
        $("#create div:nth-of-type(" + divNumber + ")").addClass("form-group");
        var labelValue = "Element" + (i+1);
        $("#create div:nth-of-type(" + divNumber + ")").append("<label></label>");
        document.getElementsByTagName("label")[i].setAttribute("for", labelValue);
        document.getElementsByTagName("label")[i].textContent = labelValue;
        $("form div:nth-of-type(" + divNumber + ")").append("<input class=element>");
        $(".element").addClass("form-control");
        document.getElementsByTagName("input")[i+2].setAttribute("id", labelValue);
        $(".element").attr("type", "text");
        $(".element").attr("name", "assessment");
    }
    formElement.append("<button type=submit id=confirm class=buttons></button>");
    $("#confirm").text("Confirm");
    $("#confirm").addClass("btn");
    $("#confirm").addClass("btn-primary");
    $("#confirm").addClass("btn-lg");

    $(".container").append("<a class=buttons id=reset></a>");
    $("#reset").attr("href", "/create");
    $("#reset").text("Reset");
    $("#reset").addClass("btn");
    $("#reset").addClass("btn-primary");
    $("#reset").addClass("btn-lg");
}

$(document).ready(function () {
    $("button").on("click", addTextfields);
});
