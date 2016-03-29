//Variables globales
var current_user;
var uri = "/v1/brocante";

/**
Bouton lister braderies
Liste les braderies et ajoute des options en fonction du role de l'utilisateur
**/
$('#braderie-list-button').click( function () {
	$('#tableMap').hide();
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
	$('#tableMap').hide();
	changeView("#braderie-form");
	$('#submit').show();
	$('#modif').hide();
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