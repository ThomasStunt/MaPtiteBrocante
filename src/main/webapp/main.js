$(function () {

	$('#test').click( function () {
		 $.ajax({
	      url: "/v1/brocante",
	      type: "GET",
	      dataType : "json",
	      success: function(json) {
	        alert(json[0].libelle);
	      },
	      error: function( xhr, status, errorThrown ) {
	          alert( "Sorry, there was a problem!" );
	          console.log( "Error: " + errorThrown );
	          console.log( "Status: " + status );
	          console.dir( xhr );
	      },
	      complete: function( xhr, status ) {
	          //alert( "The request is complete!" );
	      }
	    });

	});
});  
