//VARIABLES GLOBAL
var current_user;
var current_token;
var current_position;

var uri = "/v1/brocante";
var map;
var directionDisplay;
var directionsService;

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
	if(current_token)
 		req.setRequestHeader("Authorization", "Basic " + current_token);
}

/**
Retourne l'utilisateur après tentative de connexion au serveur
**/
function getActualUser () {
	current_token = btoa($("#userlogin").val() + ":" + $("#passwdlogin").val());
	$.ajax({
		url: "/v1/login",
		type: "GET",
		dataType: "json",
		beforeSend : function(req) {
			setSecureHeader(req);
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
};
