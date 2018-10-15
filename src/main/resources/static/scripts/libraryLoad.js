function loadFrameworks() {
    if (typeof jQuery !== 'function') {
        loadResource("/scripts/jquery.min.js");
    }
    loadResource("https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js");
    setTimeout(function () {
        if (typeof $.fn.modal !== 'function') {
            loadResource("/scripts/bootstrap.min.js");
        }
    }, 2000);

}

window.onload = loadFrameworks;

function loadResource(source) {
    var scriptTag = document.createElement('script');
    var allScriptTags = document.getElementsByTagName('script');
    var lastScriptTag = allScriptTags[allScriptTags.length - 1];
    scriptTag.src = source;
    if (lastScriptTag !== undefined) {
        lastScriptTag.parentNode.insertBefore(scriptTag, lastScriptTag);
    } else {
        document.head.appendChild(scriptTag);
    }
}


