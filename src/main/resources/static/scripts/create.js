function addTextfields() {

    var numberOfElements = Number(input.val());

    $("#confirmButton").remove();
    $("#inputBox").remove();
    $("#description").remove();

    for (var i = 0; i < numberOfElements; i++) {

        var txt = $("<p></p>").text("Text " + (i+1));
        $("form").append(txt);

        var inputBox = $("<input></input>");
        inputBox.attr("type", "text");
        inputBox.attr("name", "assessment");
        inputBox.addClass("textfield");
        $("form").append(inputBox);
    }

    var div = $("<div></div>");
    div.addClass("buttons");
    $("form").append(div);

    var reset = $("<a></a>").text("Reset");
    reset.attr("th:href", "@{/create}")
    reset.addClass("room");
    $("form").append(reset);

    var create = $("<a></a>").text("Create");
    //create.attr("th:href", "@{/create}")
    create.addClass("room");
    $("form").append(create);
}

var button = $("#confirmButton");
var input = $("input");

button.click(addTextfields);
