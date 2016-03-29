/**
Change la vue pour afficher le formulaire de connexion
**/
$('#login-button').click(function () {
	$('#tableMap').hide();
	changeView("#login-form");
});

$('#inscription-button').click(function() {
	$('#tableMap').hide();
	changeView("#inscription-form");
});

/**
Log l'utilisateur quand il rempli le formulaire
**/
$('#login-form-button').click(function(){
	current_user = getActualUser();
	checkUser();
});

$('#inscription-form-button').click(function() {
	$.ajax({
		url: "/v1/userdb",
		type: "POST",
		contentType: "application/json",
		processData: false,
		data: JSON.stringify({
			name: $('#userinscription').val(),
			password: $('#passwdinscription').val()
		}),
		success: function(json) {
			$('#inscription-form').html();
			$('#inscription-form').text("Vous êtes inscrit.");
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
Délog de l'utilisateur
**/
$('#disconnect-button').click(function() {
	document.location.href="index.html";
});