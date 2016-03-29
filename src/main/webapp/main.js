//VARIABLE LOCAL
var current_user;
var current_passwd;

var uri = "/v1/brocante";
var map;

/**
	On load show user interface corresponding to his rank
**/
$(document).ready( function () {
	checkUser();
	$("#braderie-list-button").trigger("click");
});

/**
	Add Authorization header to ajax request to access secure resource
**/
function setSecureHeader(req){
	if(current_user && current_passwd)
 		req.setRequestHeader("Authorization", "Basic " + btoa(current_user.name + ":" + current_passwd));
}

/**
Retourne l'utilisateur après tentative de connexion au serveur
**/
function getActualUser () {
	$.ajax({
		url: "/v1/login",
		type: "GET",
		dataType: "json",
		beforeSend : function(req) {
			req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
			current_passwd = $("#passwdlogin").val();
		},
		success: function(json) {
			if(json != null){
				current_user = json;
				checkUser();
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

/**
Affiche les options correspondant au role de l'utilisateur défini dans la variable current_user
**/
function checkUser () {
	if(current_user != null){
		$('.anonymous-button').hide();
		$('.user-button').show();

		if(current_user.rank > 0)
			$('.admin-button').show();

	} else{
		$('.user-button').hide();
		$('.admin-button').hide();
		$('.anonymous-button').show();
	}
	changeView("#braderie-list");
};



/**
Cache toutes les vues et affiche celle d'id view passée en paramètre
**/
function changeView(view){
	$(".view").hide();
	$(view).show();
}


// LOGIN PART

/**
Change la vue pour afficher le formulaire de connexion
**/
$('#login-button').click(function () {
	changeView("#login-form");	
});

/**
Log l'utilisateur quand il rempli le formulaire
**/
$('#login-form-button').click(function(){
	current_user = getActualUser();
	checkUser();
});

// BRADERIE PART

/**
Bouton lister braderies
Liste les braderies et ajoute des options en fonction du role de l'utilisateur
**/
$('#braderie-list-button').click( function () {
	var view = "#braderie-list";
	changeView(view);
	var isAdmin = (current_user != null && current_user.rank > 0);
	$.ajax({
		url: isAdmin ? uri + "/all" : uri,
		type: "GET",
		dataType: "JSON",
		beforeSend : function(req) {
			setSecureHeader(req);
		},
		success: function(json) {
			$(view).html(getBraderieListHtml(json, isAdmin));
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
});

/**
Affiche la liste des braderies passées en paramètre (json), et si le booleen isAdmin est true
On affiche des options de suppresion, modification et validation
**/
function getBraderieListHtml(braderies, isAdmin){
	var res = "";
	if(braderies[0] == null) {
		res = "Aucune brocante disponible.";
	} else {
		res = "<div><table class=\"table table-striped table-bordered\" style=\"text-align:center\"><tr>";
		res += "<td>Libelle</td><td>Organisateur</td><td>Adresse</td><td>Horaires</td><td>Salle</td><td>Prix emplacement</td><td>Accès Handicapé</td>";
		if(isAdmin)
			res+="<td>Action</td>";
		res += "</tr><tr>";
		var b;
		for(i in braderies) {
				b = braderies[i];
				res+="<td>"+b.libelle+"</td>";
				res+="<td>"+"nom : " + b.nomOrganisateur+"<br> tel : "+b.telOrganisateur+"<br> mail : "+b.emailOrganisateur+"</td>";
				res+="<td>"+ b.rue + " " + b.codePostal + " à " + b.ville + " - " + b.pays +"</td>";
				res+="<td>"+b.date+" <br>de "+ b.heure_debut + " à " + b.heure_fin + "</td>";
				res+="<td>"+ b.salle + "</td>";
				res+="<td>" + b.prixEmplacement + "€/m</td>";
				res+="<td>";
				if(b.handicape)
					res+= "oui";
				else
					res+="non";
				res+="</td>";

				if(isAdmin){
					res+="<td>";
					if(!b.valide)
						res+="<button onclick='validateOrDeleteBraderie("+b.id+", true)'>Valider</button>";
					res+="<button onclick='validateOrDeleteBraderie("+b.id+", false)'>Supprimer</button>";
					res+="<button onclick='modifyBraderie("+JSON.stringify(b)+")'>Modifier</button>";
					res+="</td>";
				}
				res+="</tr><tr>"
		}
		res+="</tr></table></div>";
		$("#outputList").html(res);
	}
	return res;
}

/**
	Valide ou supprime une braderie d'id idBraderie
	Si validate vaut true alors on valide la braderie, sinon on la supprime
**/
function validateOrDeleteBraderie(idBraderie, validate){
	var resUrl = uri;
	if(validate)
		resUrl += "/valider";
	resUrl += "/" + idBraderie;
	$.ajax({
		url: resUrl,
		type: validate ? "GET" : "DELETE",
		beforeSend : function(req) {
		 setSecureHeader(req);
		},
		success: function(json) {
			$("#braderie-list-button").trigger("click");
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
}

function modifyBraderie(brad) {
	var braderie = brad;
	changeView("#braderie-form");

	var id = braderie.id;

	$("#inputLibelle").val(braderie.libelle);

	$("#inputNomBroc").val(braderie.nomOrganisateur);
	$("#inputMailBroc").val(braderie.emailOrganisateur);
	$("#inputTelBroc").val(braderie.telOrganisateur);

	$("#inputVille").val(braderie.ville);
	$("#inputPays").val(braderie.pays);
	$("#inputDepart").val(braderie.departement);
	$("#inputCPostal").val(braderie.codePostal);
	$("#inputAddr1").val(braderie.rue);

	$("#inputDate").val(braderie.date);
	$("#inputHDeb").val(braderie.heure_debut);
	$("#inputHFin").val(braderie.heure_fin);

	$("#inputSalle").val(braderie.salle);
	$("#inputLibelle").val(braderie.libelle);
	
	if(braderie.handicape) {
		$("#handicY").checked = true;
		$("#handicN").checked = false;
	} else { 
		$("#handicN").checked = true;
		$("#handicY").checked = false;
	}

	$("#inputPrix").val(braderie.prixEmplacement);

	$('#submit').hide();
	$('#modif').show();

	$('#modif').click(function (){

		var handic = function() {
			if($("#handicY").attr('checked', true)) {
				return true;
			} else {
				return false;
			}
		}

		var dat = {
			libelle : $("#inputLibelle").val(),

			nomOrganisateur: $("#inputNomBroc").val(),
			emailOrganisateur: $("#inputMailBroc").val(),
			telOrganisateur: $("#inputTelBroc").val(),

			pays: $("#inputPays").val(),
			departement: $("#inputDepart").val(),
			codePostal: $("#inputCPostal").val(),
			ville: $("#inputVille").val(),
			rue : $('#inputAddr1').val()+" "+$('#inputAddr2').val()+" "+$('#inputAddr3').val()+" "+$('#inputAddr4').val(),

			date: $("#inputDate").val(),
			heure_debut : $("#inputHDeb").val(),
			heure_fin : $("#inputHFin").val(),

			salle: $("#inputSalle").val(),
			handicape: handic,
			prixEmplacement : $("#inputPrix").val()
		};

		$.ajax({
			url: uri+"/"+braderie.id,
			type: "PUT",
			dataType: "json",
			processData: false,
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(dat),
			beforeSend : function(req) {
				setSecureHeader(req);
			},
			success: function(json) {
				$("#output").html("Brocante modifiée !");
			},
			error: function(xhr, status, errorThrown) {
				alert("Something went wrong");
				console.log("xhr: ", xhr);
				console.log("status: ", status);
				console.log("errorThrown: ", errorThrown);
			}
		});
	})
}

/**
	Bouton ajouter/modifier une braderie
	Affiche le formulaire d'une braderie
**/
$("#braderie-modify-button").click(function() {
	changeView("#braderie-form");
});

function checker(handicY, handicN) {
	if($('#handicY').checked)
		$('#handicN').checked = false;
	else
		$('#handicN').checked = true;
}

// AJOUTER UNE BRADERIE
$("#add #submit").click(function() {
	var handic = function() {
		if($("#handicY").attr('checked', true)) {
			return true;
		} else {
			return false;
		}
	}

	var dat = {
		libelle : $("#inputLibelle").val(),

		nomOrganisateur: $("#inputNomBroc").val(),
		emailOrganisateur: $("#inputMailBroc").val(),
		telOrganisateur: $("#inputTelBroc").val(),

		pays: $("#inputPays").val(),
		departement: $("#inputDepart").val(),
		codePostal: $("#inputCPostal").val(),
		ville: $("#inputVille").val(),
		rue : $('#inputAddr1').val()+" "+$('#inputAddr2').val()+" "+$('#inputAddr3').val()+" "+$('#inputAddr4').val(),

		date: $("#inputDate").val(),
		heure_debut : $("#inputHDeb").val(),
		heure_fin : $("#inputHFin").val(),

		salle: $("#inputSalle").val(),
		handicape : handic,
		prixEmplacement : $("#inputPrix").val(),
		valide: false
	};
	console.log(dat);

	$.ajax({
		url: uri,
		type: "POST",
		dataType: "json",
		processData: false,
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(dat),
		success: function(json) {
			$("#output").html("Brocante ajoutée !");
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
	$('#braderie-modify-button').trigger("click");
});

// MAP PART

// BOUTON AFFICHER LA CARTE
$('#map-button').click( function () {
	initializeMap();
	getInformationBraderie();
	changeView("#map");
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
					var info = [json[i].codePostal, json[i].ville, json[i].rue, json[i].date, json[i].heure_debut, json[i].heure_fin];
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
}

// RECUPERER LA LATITUDE ET LA LONGITUDE D'UNE BRADERIE
function getGeolocalisations(braderies){

	for(var i in braderies){
		$.ajax({
			url: trimSplitJoin("https://maps.googleapis.com/maps/api/geocode/json?address="+ braderies[i][2] +",+" + braderies[i][0] + "+"+braderies[i][1]+",+"+"France"),
			type: "GET",
			dataType: "json",
			success: function(json) {
				addMarker(json);
			},
			error: function(xhr, status, errorThrown) {

				alert("Something went wrong");
				console.log("xhr: ", xhr);
				console.log("status: ", status);
				console.log("errorThrown: ", errorThrown);
			}
		});
	}
};

function addMarker(json){
	var mapDiv = document.getElementById('map');
	var myLatLng = {lat: json.results[0].geometry.location.lat, lng: json.results[0].geometry.location.lng};
	var marker = new google.maps.Marker({
	    map: map,
	    position: myLatLng,
	    title: 'Hello World!'
	});
};

// INITIALISE LA MAP
function initializeMap() {
	var mapDiv = document.getElementById('map');
	map = new google.maps.Map(mapDiv, {
	  center: {lat: 44.540, lng: -78.546},
	  zoom: 15
});

    var infoWindow = new google.maps.InfoWindow({map: map});
  // Try HTML5 geolocation.
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	     	var pos = {
	        lat: position.coords.latitude,
	        lng: position.coords.longitude
	    };
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
}
