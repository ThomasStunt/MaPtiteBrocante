package fr.iutinfo.skeleton.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/login")
public class LoginResource {
    final static Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @GET
    public User login(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser)) {
			logger.debug("UNAUTHORIZED");
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Ma ptite brocante\"")
					.entity("Ressouce requires login.").build());
		}
		return currentUser;
    }

}
