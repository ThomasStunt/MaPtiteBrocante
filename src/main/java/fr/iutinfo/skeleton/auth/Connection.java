package fr.iutinfo.skeleton.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.SecureResource;
import fr.iutinfo.skeleton.api.User;

@Path("/connection")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Connection {
	final static Logger logger = LoggerFactory.getLogger(SecureResource.class);

	@GET
	public User secureForLoggedUsers(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}
		return currentUser;
	}
	
}
