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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HttpHeaders;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ArticleResource {

	private static ArticleDao dao = BDDFactory.getDbi().open(ArticleDao.class);

	Logger logger = LoggerFactory.getLogger(BrocanteResource.class);

	protected Article find(int id) {
		return dao.find(id);
	}

	public ArticleResource() {
		try {
			System.out.println("Creating table");
			dao.createArticleTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}

	@GET
	public List<Article> getAllArticle() {
		return dao.all();
	}
	
	@POST
	@RolesAllowed({ "admin" })
	public Article createArticle(Article article, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User  :" + currentUser.toString());
		if (currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}
		logger.debug("Current User :" + currentUser.toString());
		dao.insert(article);
		return article;
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public void deleteArticle(@PathParam("id") int id, @Context SecurityContext context) {
		User currentUser = (User) context.getUserPrincipal();
		logger.debug("Current User  :" + currentUser.toString());
		if (currentUser.getRank() <= 0) {
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
					.header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
					.entity("Ressouce requires login.").build());
		}

		try {
			dao.deleteArticle(id);
			Response.accepted().status(Status.ACCEPTED).build();
		} catch (Exception e) {
			Response.accepted().status(Status.NOT_FOUND).build();
		}
	}
	
	

}
