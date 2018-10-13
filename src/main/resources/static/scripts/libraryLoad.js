function loadFrameworks() {
    kontrollija(jQuery, "/scripts/jquery.min.js");
    var bodyColor = $("body").css("color");
    if (bodyColor !== 'rgb(51, 51, 51)') {
        var linkTag = document.createElement('link');
        var firstLinkTag = document.getElementsByTagName('link')[0];
        linkTag.href = "/css/bootstrap.min.css";
        linkTag.rel = "stylesheet";
        linkTag.type = "text/css";
        if (firstLinkTag !== undefined) {
            firstLinkTag.parentNode.insertBefore(linkTag, firstLinkTag);
        } else {
            document.getElementsByTagName("head")[0].appendChild(linkTag);
        }
    }
    $.getScript("https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js");
    setTimeout(function () {
        kontrollija($.fn.modal, "/scripts/bootstrap.min.js");
    }, 2000);

}

window.onload = loadFrameworks;

function kontrollija(jstype, lib){
    if (typeof jstype !== 'function') {
        var scriptTag = document.createElement('script');
        var firstScriptTag = document.getElementsByTagName('script')[0];
        scriptTag.src = lib;
        if (firstScriptTag !== undefined) {
            firstScriptTag.parentNode.insertBefore(scriptTag, firstScriptTag);
        } else {
            document.getElementsByTagName("head")[0].appendChild(scriptTag);
        }
    }
}


