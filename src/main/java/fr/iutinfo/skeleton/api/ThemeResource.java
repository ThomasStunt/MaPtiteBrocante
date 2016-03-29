package fr.iutinfo.skeleton.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/themes")

public class ThemeResource {

	private static ThemeDao dao = BDDFactory.getDbi().open(ThemeDao.class);

	Logger logger = LoggerFactory.getLogger(BrocanteResource.class);

	protected Theme find(int id) {
		return dao.find(id);
	}

	public ThemeResource() {
		try {
			System.out.println("Creating table");
			dao.createThemeTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@GET
	public List<Theme> getAllTheme() {
		return dao.all();
	}

	@POST
	@RolesAllowed({ "admin" })
	public Theme createTheme(Theme theme, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		dao.insert(theme);
		return theme;
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public void deleteTheme(@PathParam("id") int id, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User  :" + currentUser.toString());
		if (currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}

		try {
			dao.deleteTheme(id);
			Response.accepted().status(Status.ACCEPTED).build();
		} catch (Exception e) {
			Response.accepted().status(Status.NOT_FOUND).build();
		}
	}

}
