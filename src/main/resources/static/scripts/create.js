function addTextfields() {
    var numberOfElements = Number(input.value);
    var form = document.querySelector("form");
    for (var i = 0; i < numberOfElements; i++) {
        var descriptiveText = document.createElement("p");
        var node = document.createTextNode("Text " + (i+1));
        descriptiveText.appendChild(node);
        var lineBreak = document.createElement("br");
        var inputBox = document.createElement("input");
        inputBox.setAttribute("type", "text");
        inputBox.setAttribute("name", "assessment")
        inputBox.classList.add("textfield");
        descriptiveText.classList.add("descriptiveText");
        form.appendChild(lineBreak);
        form.appendChild(descriptiveText);
        form.appendChild(inputBox);
    }
    var button = document.createElement("input");
    button.setAttribute("type", "submit");
    button.setAttribute("value", "Create");
    var lineBreak2 = document.createElement("br");
    form.appendChild(lineBreak2);
    form.appendChild(button);
    this.removeEventListener("click", addTextfields);
}

var button = document.getElementById("confirmButton");
var input = document.querySelector("input");

button.addEventListener("click", addTextfields);
