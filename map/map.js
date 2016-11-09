function myMap() {
  var mapCanvas = document.getElementById("map");
  var myCenter= new google.maps.LatLng(31.6341600,-7.9999400);
  var mapOptions = {center: myCenter, zoom: 2};
  var map = new google.maps.Map(mapCanvas, mapOptions);
  var infowindow = new google.maps.InfoWindow();

  
  google.maps.event.addListenerOnce(map, 'click', function(event) {
    placeMarker(map, infowindow, event.latLng, function(country){
      country;
    });
  });
}

function placeMarker(map,infowindow, location, fn) {
  var marker = new google.maps.Marker({
    position: location,
    map: map,
    draggable: true
  });
  var latLng = new google.maps.LatLng(marker.position.lat(),marker.position.lng());

  infowindow.open(map,marker);
  geocoderLatLng(map,infowindow,latLng);
  
  google.maps.event.addListener(marker,'dragend', function(event) {
    latLng = new google.maps.LatLng(marker.position.lat(),marker.position.lng());
    geocoderLatLng(map,infowindow,latLng);
  });

  google.maps.event.addListener(marker,'rightclick', function(event) {
    marker.setDraggable(false);
    console.log(infowindow.getContent());  
  })
} 

function geocoderLatLng(map, infowindow,latLng) {
  var geocoder = new google.maps.Geocoder;
  geocoder.geocode({'location':latLng}, function(results, status) {
    if(status === google.maps.GeocoderStatus.OK) {
      if (results[1]) {
        var i = 0;
        while(results[1].address_components[i].types[0] != "country") {
          i++;
        }
        infowindow.setContent(results[1].address_components[i].long_name);        
      } else {
        console.error('No results found');
      }
    } else {
      console.error('Geocoder failed due to: ' + status);
    }
  });  
}
