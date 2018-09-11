var mymap = L.map('mapid').setView([58.3782485, 26.7146733], 15);
var marker = L.marker([58.3782485, 26.7146733]).addTo(mymap);
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 50,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1IjoibGF1cmlyYW4iLCJhIjoiY2psd2RxeTZsMHZ6eTNwbGk0czBzMzJvayJ9.VrluXQudTwJGLB40yYXRYQ'
}).addTo(mymap);