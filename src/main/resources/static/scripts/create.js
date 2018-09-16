function addTextfields() {

    var numberOfElements = Number($("input").val());
    var formElement = $("form");

    $("p").remove();
    $("input").remove();
    $("button").remove();

    for (var i = 0; i < numberOfElements; i++) {
        formElement.append("<div></div>");
        var divNumber = i+1;
        $("form div:nth-of-type(" + divNumber + ")").addClass("form-group");
        $("form div:nth-of-type(" + divNumber + ")").addClass("element");
        var labelValue = "Element" + (i+1);
        $("form div:nth-of-type(" + divNumber + ")").append("<label></label>");
        document.getElementsByTagName("label")[i].setAttribute("for", labelValue);
        document.getElementsByTagName("label")[i].textContent = labelValue;
        $("form div:nth-of-type(" + divNumber + ")").append("<input>");
        $("input").addClass("form-control");
        document.getElementsByTagName("input")[i].setAttribute("id", labelValue);
        $("input").attr("type", "text");
    }
    $(".container").append("<button type=submit id=confirm class=buttons></button>");
    $("#confirm").text("Confirm");
    $("#confirm").addClass("btn");
    $("#confirm").addClass("btn-info");

    $(".container").append("<a class=buttons id=reset></a>");
    $("#reset").attr("href", "/create");
    $("#reset").text("Reset");
    $("#reset").addClass("btn");
    $("#reset").addClass("btn-info");
}

$("button").on("click", addTextfields);
