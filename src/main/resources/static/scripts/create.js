function addElementChoices() {

    var numberOfElements = Number($("input[type=number]").val());
    var formElement = $("#create");

    $("p").remove();
    $("input[type=number]").remove();
    $("button").remove();

        for (var i = 0; i < numberOfElements; i++) {
            formElement.append("<div></div>");
            var divNumber = i + 1;
            $("#create div:nth-of-type(" + divNumber + ")").addClass("form-group");
            var labelValue = "Element" + (i + 1);
            $("#create div:nth-of-type(" + divNumber + ")").append("<label></label>");
            document.getElementsByTagName("label")[3*i].setAttribute("for", labelValue);
            document.getElementsByTagName("label")[3*i].textContent = labelValue;
            var radioDiv = $('<div class="radio"></div>');
            $("form div:nth-of-type(" + divNumber + ")").append(radioDiv);
            document.getElementsByClassName("radio")[i].innerHTML ="<label><input class='textRadio' type='radio' checked>Text</label>" +
                "<label><input class='picRadio' type='radio'>Picture</label>";
            var radioName = "elementType" + i;
            document.getElementsByClassName("textRadio")[i].setAttribute("name", radioName);
            document.getElementsByClassName("picRadio")[i].setAttribute("name", radioName);
            $("form div:nth-of-type(" + divNumber + ")").append("<input class=element type='text' name='assessment'>");
            $(".element").addClass("form-control");
            document.getElementsByTagName("input")[3*i + 1].setAttribute("id", labelValue);
        }

    formElement.append("<button type=submit id=confirm class=buttons></button>");
    $("#confirm").text("Confirm");
    $("#confirm").action("/upload");
    $("#confirm").addClass("btn");
    $("#confirm").addClass("btn-primary");
    $("#confirm").addClass("btn-lg");

    $(".container").append("<a class=buttons id=reset></a>");
    $("#reset").attr("href", "/create");
    $("#reset").text("Reset");
    $("#reset").addClass("btn");
    $("#reset").addClass("btn-primary");
    $("#reset").addClass("btn-lg");
    addRadioButtonListener();
    addConfirmButtonListener();
}

function addConfirmButtonListener(){
    $("#confirm").on("click", createPage);
}

function createPage(){
    var inputlist = document.getElementsByName("assessment");
    for(i = 0; i < inputlist; i++){
        if(inputlist[i].getAttribute("type")==="file"){
            saveFile(inputlist[i]);
        }
    }
}

function saveFile(inputlistElement){

}

function textfieldToFileupload() {
    var parent = document.getElementById("Element" + (this.elementNumber+1)).parentNode;
    var fileInput = $("<input type='file'>");
    console.log(parent);
    console.log(fileInput);
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("type", "file");
    document.getElementById("Element" + (this.elementNumber+1)).classList.remove("form-control");
}

function fileuploadToTextfield() {
    var parent = document.getElementById("Element" + (this.elementNumber+1)).parentNode;
    var fileInput = $("<input type='file'>");
    console.log(parent);
    console.log(fileInput);
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("type", "text");
    document.getElementById("Element" + (this.elementNumber+1)).classList.add("form-control");
}

function addRadioButtonListener() {
    var textButtons = document.getElementsByClassName("textRadio");
    var pictureButtons = document.getElementsByClassName("picRadio");
    for (i = 0; i < pictureButtons.length; i++) {
        var nameAttribute = pictureButtons[i].getAttribute("name");
        var elementNumber = nameAttribute[nameAttribute.length - 1];
        pictureButtons[i].addEventListener("click", textfieldToFileupload);
        pictureButtons[i].elementNumber = Number(elementNumber);
    }

    for(i = 0; i < textButtons.length; i++) {
        var nameAttribute = textButtons[i].getAttribute("name");
        var elementNumber = nameAttribute[nameAttribute.length - 1];
        textButtons[i].addEventListener("click",fileuploadToTextfield);
        textButtons[i].elementNumber = Number(elementNumber);
    }
}

$(document).ready(function () {
    $("#confirm").on("click", addElementChoices);
});

