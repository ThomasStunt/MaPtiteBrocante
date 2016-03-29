//Variables globales
var uri = "/v1/brocante";
var map;
var directionDisplay;
var directionsService;

//Bouton map
$('#map-button').click( function () {
	changeView("#map");
	initializeMap();
	getInformationBraderie();
});


// RECUPERER LES INFORMATIONS DES BRADERIES
function getInformationBraderie (){
	$.ajax({
		url: "/v1/brocante",
		type: "GET",
		dataType: "json",
		success: function(json) {
			if(json[0] == null) {
				//a afficher il n'y a pas de braderie aux alentours
			} else {
				var braderies = [];
				for(i in json) {
					var info = {
						'codepostal' : json[i].codePostal,
						'ville' : json[i].ville,
						'rue' : json[i].rue,
						'date' : json[i].date,
						'heureDeb' : json[i].heure_debut,
						'heureFin' : json[i].heure_fin,
						'name' : json[i].libelle
					};
					braderies.push(info);
					}
					getGeolocalisations(braderies);
			}
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
};

function trimSplitJoin(chaine){
	return $.trim(chaine).split(' ').join('+');
};

// RECUPERER LA LATITUDE ET LA LONGITUDE D'UNE BRADERIE
function getGeolocalisations(braderies){
	for(var i in braderies){
		getGeolocalisation(braderies[i]);
	}
};

function getGeolocalisation(braderie){
	$.ajax({
		url: trimSplitJoin("https://maps.googleapis.com/maps/api/geocode/json?address="+ braderie.rue +",+" + braderie.codePostal + "+"+braderie.ville+",+"+"France"),
		type: "GET",
		dataType: "json",
		success: function(json) {
			console.log(braderie);
			addMarker(json, braderie);
		},
		error: function(xhr, status, errorThrown) {

			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
};

function addMarker(json, braderies){

	var mapDiv = document.getElementById('map');
	var braderiePosition = {lat: json.results[0].geometry.location.lat, lng: json.results[0].geometry.location.lng};
	var marker = new google.maps.Marker({
	    map: map,
	    position: braderiePosition,
	    title: 'Afficher les informations'
  	});

  	marker.addListener('click', function () {
  		var origin = new google.maps.LatLng(current_position.lat, current_position.lng);
    	var destination = new google.maps.LatLng(braderiePosition.lat, braderiePosition.lng);

    	directionsService.route({origin:origin, destination:destination, travelMode:google.maps.TravelMode.DRIVING},function(result, status){
    		if(status == google.maps.DirectionsStatus.OK){
    			addInformation(braderies,result.routes[0].legs[0].distance.value / 1000,origin,destination);
    		}
    	});
  	});
};

function addInformation(braderie, distance, origin, destination){

	$('#tableMap').show();
	$('#tableMap #name').text(braderie.name);
    $('#tableMap #distance').text(distance + " km");
    $('#tableMap #heureDeb').text(braderie.heureDeb);
    $('#tableMap #heureFin').text(braderie.heureFin);

	$('#tableMap #itineraire').click( function () {
		var request = {
        	origin      : origin,
        	destination : destination,
        	travelMode  : google.maps.DirectionsTravelMode.DRIVING
    	}

    	directionsService.route(request, function(response, status){
        	if(status == google.maps.DirectionsStatus.OK){
            	directionDisplay.setDirections(response);
        	}
    	});
	});
};

// INITIALISE LA MAP
function initializeMap() {
	var mapDiv = document.getElementById('map');
	map = new google.maps.Map(mapDiv, {
	  center: {lat: 44.540, lng: -78.546},
	  zoom: 12
	});

	directionsService = new google.maps.DirectionsService();
	directionDisplay = new google.maps.DirectionsRenderer({
		    map   : map 
	});

	var infoWindow = new google.maps.InfoWindow({map: map});
  // Try HTML5 geolocation.
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	     var pos = {
	        lat: position.coords.latitude,
	        lng: position.coords.longitude
	     };
	    current_position=pos;
	    infoWindow.setPosition(pos);
	    infoWindow.setContent('Vous êtes ici.');
	    map.setCenter(pos);
	    }, function() {
	      handleLocationError(true, infoWindow, map.getCenter());
	    });
	} else {
	    // Browser doesn't support Geolocation
	    handleLocationError(false, infoWindow, map.getCenter());
	}
};

// EXCEPTION DE L'INITALISATION DE LA MAP
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	infoWindow.setPosition(pos);
	infoWindow.setContent(browserHasGeolocation ?'Error: The Geolocation service failed.' :'Error: Your browser doesn\'t support geolocation.');
};