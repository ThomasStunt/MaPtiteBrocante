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
}


// ARTICLE PART
$('#blog-button').click(function() {
	changeView("#article");
	var isAdmin = current_user && current_user.rank > 0;
	$.ajax({
		url: "/v1/articles",
		type: "GET",
		dataType: "json",
		success: function(json) {
			var articles = [];
			for(i in json) {
				var info = {
					'id' : json[i].id,
					'titre' : json[i].titre,
					'theme' : json[i].theme,
					'texte' : json[i].texte
				};
				articles.push(info);
			}
			getArticleListHtml(articles, isAdmin);
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});
	
	if(isAdmin){
		$("#article-form").show();
		$("#article-textarea").markdown({autofocus:false,savable:false});
	} else {
		$("#article-form").hide();
	}
});

$('#article-form-button').click(function() {
	$.ajax({
		url: "/v1/articles",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		beforeSend : function(req) {
			setSecureHeader(req);
		},
		data : JSON.stringify({
			titre: $("#article-title").val(),
			texte: $("#article-textarea").val(),
			theme : "none"
		}),
		success: function(json) {
			$("#blog-button").trigger("click");
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
		}
	});

});

function getArticleListHtml(articles, isAdmin){
	var res = "";
	if(articles[0] == null) {
		res = "Aucun article disponible.";
	} else {
		var article;
		for(i in articles) {
			article = articles[i];
			res += "<div>";
			res += "<h1>"+article.titre+"</h1><div>";
			res +=  markdown.toHTML(article.texte)+"</div>";
			if(isAdmin)
				res += "<button onclick='supprimerArticle("+article.id+")'>Supprimer</button>"
			res += "</div><hr><br>";
		}	
	}
	$("#article-list").html(res);
}

function supprimerArticle(id){
	$.ajax({
		url: "/v1/articles/"+id,
		type: "DELETE",
		beforeSend : function(req) {
			setSecureHeader(req);
		},
		success: function(json) {
			$("#blog-button").trigger("click");
		},
		error: function(xhr, status, errorThrown) {
			alert("Something went wrong");
			console.log("xhr: ", xhr);
			console.log("status: ", status);
			console.log("errorThrown: ", errorThrown);
			
		}
	});
}

