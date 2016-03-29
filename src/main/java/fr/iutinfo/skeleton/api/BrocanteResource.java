package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/brocante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BrocanteResource {

	private static BrocanteDao dao = BDDFactory.getDbi().open(BrocanteDao.class);

	Logger logger = LoggerFactory.getLogger(BrocanteResource.class);

	protected Brocante find(int id) {
		return dao.find(id);
	}
	

	public BrocanteResource() {
		try {
			System.out.println("Creating table");
			dao.createBrocanteTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@POST
	@RolesAllowed({"user","admin"})
	public Brocante createBrocante(Brocante brocante, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (currentUser.getRank() > 0)
			brocante.setValide(true);
		else
			brocante.setValide(false);
		
		brocante.sanitize();
		dao.insert(brocante);
		return brocante;
	}

	@GET
	@Path("/all")
	@RolesAllowed({"user","admin"})
	public List<Brocante> getAllBrocantes(@Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser) || currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}
		return dao.all();
	}
	
	@GET
	@Path("/valider/{id}")
	@RolesAllowed({"user","admin"})
	public void valideBrocante(@PathParam("id") int id, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser) || currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}
		dao.valideBrocante(id);
	}

	@GET
	public List<Brocante> getAllBrocantesUser() {
		return dao.allBrocanteUser();
	}

	@DELETE
	@Path("/{id}")
	//@RolesAllowed({"user"})
	public void deleteBrocante(@PathParam("id") int id, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User  :" + currentUser.toString());
		if (currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}

		try {
			dao.deleteBrocante(id);
			Response.accepted().status(Status.ACCEPTED).build();
		} catch (Exception e) {
			Response.accepted().status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{id}")
	@RolesAllowed({"user"})
	public Response updateBrocante(@PathParam("id") int id, Brocante brocante, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User :" + currentUser.toString());
		if (User.isAnonymous(currentUser) || currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}
		brocante.sanitize();
		dao.updateBrocante(id, brocante);
		return Response.status(200).entity(brocante).build();
	}
}
