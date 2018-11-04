var langbutton = $(".languagebutton");
langbutton.on("click", addElementChoices());
var locale = $("html").attr("lang");

function addElementChoices() {

    var numberOfElements = Number($("input[type=number]").val());
    var formElement = $("#create");

    $("p").remove();
    $("input[type=number]").remove();
    $("#confirm").remove();

        for (var i = 0; i < numberOfElements; i++) {
            formElement.append("<div></div>");
            var divNumber = i + 1;
            $("#create div:nth-of-type(" + divNumber + ")").addClass("form-group");
            var labelValue = "Element " + (i + 1);
            $("#create div:nth-of-type(" + divNumber + ")").append("<label></label>");
            document.getElementsByTagName("label")[3*i].setAttribute("for", labelValue);
            document.getElementsByTagName("label")[3*i].textContent = labelValue;
            $("form div:nth-of-type(" + divNumber + ")").append("<input class=element type='text' name='assessment'>");
            $(".element").addClass("form-control");
            document.getElementsByTagName("input")[3*i + 3].setAttribute("id", labelValue);
            var radioDiv = $('<div class="radio"></div>');
            $("form div:nth-of-type(" + divNumber + ")").append(radioDiv);
            if(locale==="et"){
            document.getElementsByClassName("radio")[i].innerHTML ="<label><input class='textRadio' type='radio' checked>Tekst</label>" +
                "<label><input class='picRadio' type='radio'>Pilt</label>";}
            else{
                document.getElementsByClassName("radio")[i].innerHTML ="<label><input class='textRadio' type='radio' checked>Text</label>" +
                "<label><input class='picRadio' type='radio'>Picture</label>";}
            var radioName = "elementType" + i;
            document.getElementsByClassName("textRadio")[i].setAttribute("name", radioName);
            document.getElementsByClassName("picRadio")[i].setAttribute("name", radioName);
        }

    formElement.append("<button type=submit id=confirm class=buttons></button>");
        if(locale==="et")
            $("#confirm").text("Kinnita");
        else{
            $("#confirm").text("Confirm");
        }
    $("#confirm").addClass("btn");
    $("#confirm").addClass("btn-primary");
    $("#confirm").addClass("btn-lg");

    $(".container").append("<a class=buttons id=reset></a>");
    $("#reset").attr("href", "/create");
    if(locale==="et")
        $("#reset").text("Algusesse");
    else{
        $("#reset").text("Reset");
    }
    $("#reset").addClass("btn");
    $("#reset").addClass("btn-primary");
    $("#reset").addClass("btn-lg");
    addRadioButtonListener();
}

function textfieldToFileupload() {
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("type", "file");
    document.getElementById("Element" + (this.elementNumber+1)).classList.remove("form-control");
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("name", "picture");
}

function fileuploadToTextfield() {
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("type", "text");
    document.getElementById("Element" + (this.elementNumber+1)).classList.add("form-control");
    document.getElementById("Element" + (this.elementNumber+1)).setAttribute("name", "assessment");
}

function addRadioButtonListener() {
    var textButtons = document.getElementsByClassName("textRadio");
    var pictureButtons = document.getElementsByClassName("picRadio");
    for(var i = 0; i < pictureButtons.length; i++) {
        var picNameAttribute = pictureButtons[i].getAttribute("name");
        var pictureNumber = picNameAttribute[picNameAttribute.length - 1];
        pictureButtons[i].addEventListener("click",textfieldToFileupload);
        pictureButtons[i].elementNumber = Number(pictureNumber);
    }

    for(var j = 0; j < textButtons.length; j++) {
        var textNameAttribute = textButtons[j].getAttribute("name");
        var textNumber = textNameAttribute[textNameAttribute.length - 1];
        textButtons[j].addEventListener("click",fileuploadToTextfield);
        textButtons[j].elementNumber = Number(textNumber);
    }
}

$(document).ready(function () {
    $("#confirm").on("click", addElementChoices);
});

