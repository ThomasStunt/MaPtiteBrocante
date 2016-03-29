package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {
	private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);


    public UserDBResource() {
		try {
			logger.debug("\n\nCreating table !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
			dao.createUserTable();
			System.out.println("Creating table");
			User user = new User(0,"admin", "le meilleur admin");
			user.setPassword("admin");
			user.setRank(1);
			dao.insert(user);
			
			user = new User(0,"user", "le meilleur user");
			user.setPassword("user");
			dao.insert(user);
			
		} catch (Exception e) {
			logger.debug("\n\nCreating table !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
		}
	}
    
    @GET
	@Path("/login")
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
	
	@POST
	public User createUser(User user){
        user.resetPasswordHash();
        int id = dao.insert(user);
        user.setId(id);
		return user;
	}

	@GET
	@Path("/{name}")
	public User getUser(@PathParam("name") String name, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser) || currentUser.getRank() <= 0) {
			logger.debug("UNAUTHORIZED");
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Ma ptite brocante\"")
					.entity("Ressouce requires login.").build());
		}
		User user = dao.findByName(name);
		if (user == null) {
			throw new WebApplicationException(404);
		}
		return user;
	}

	@GET
	public List<User> getAllUsers(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser) || currentUser.getRank() <= 0) {
			logger.debug("UNAUTHORIZED");
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Ma ptite brocante\"")
					.entity("Ressouce requires login.").build());
		}
		return dao.all();
	}

}
