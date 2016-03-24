package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/brocante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BrocanteResource {
	
	private static Map<Integer, Brocante> brocantes = new HashMap<>();
	private static BrocanteDao dao = BDDFactory.getDbi().open(BrocanteDao.class);
	
    
   Logger logger = LoggerFactory.getLogger(BrocanteResource.class);

   protected Brocante find(int id) {
       return brocantes.get(id);
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
	public Brocante createBrocante(Brocante brocante) {
       int id = dao.insert(brocante);
       brocantes.put(id, brocante);
       brocante.setId(id);
       return brocante;
	}
	

	@GET
	public List<Brocante> getAllBrocantes() {
		return dao.all();
	}

	@DELETE
	@Path("/{id}")
	public void deleteBrocante(@PathParam("id") int id) {
		if (brocantes.containsKey(id)){
			brocantes.remove(id);
			dao.deleteBrocante(id);
			Response.accepted().status(Status.ACCEPTED).build();
		} else {
			Response.accepted().status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
    @Path("{id}")
    public Response updateBrocante(@PathParam("id") int id,
                               Brocante brocante) {
        Brocante oldBrocante = find(id);
        logger.info("Should update Brocante with id: " + id + " (" + oldBrocante + ") to " + brocante);
        if (brocante == null) {
            throw new WebApplicationException(404);
        }
        oldBrocante.setLibelle(brocante.getLibelle());
        oldBrocante.setDate(brocante.getDate());
        oldBrocante.setCodePostal(brocante.getCodePostal());
        oldBrocante.setDepartement(brocante.getDepartement());
        oldBrocante.setEmailOrganisateur(brocante.getEmailOrganisateur());
        oldBrocante.setHandicape(brocante.isHandicape());
        oldBrocante.setHeure_debut(brocante.getHeure_debut());
        oldBrocante.setHeure_fin(brocante.getHeure_fin());
        oldBrocante.setNomOrganisateur(brocante.getNomOrganisateur());
        oldBrocante.setRue(brocante.getRue());
        oldBrocante.setPays(brocante.getPays());
        oldBrocante.setPrixEmplacement(brocante.getPrixEmplacement());
        oldBrocante.setTelOrganisateur(brocante.getTelOrganisateur());
        oldBrocante.setSalle(brocante.getSalle());
        return Response.status(200).entity(oldBrocante).build();
    }
	
	/*@GET
	@Path("/{name}")
	public Brocante getBrocante(@PathParam("name") String name) {
		Brocante brocante = dao.findByName(name);
		if (brocante == null) {
			throw new WebApplicationException(404);
		}
		return brocante;
	}*/

   
	/*
	 * Old resource
	 * 
	 */
	
	//private static Map<Integer, Brocante> brocantes = new HashMap<>();
	
   /* @POST
    public Brocante createBrocante(Brocante brocante) {
        int id = brocantes.size();
        brocante.setId(id + 1);
        brocantes.put(brocante.getId(), brocante);
        return brocante;
    }

    @DELETE
    @Path("{id}")
    public Response deleteBrocante(@PathParam("id") Integer id) {
        if (brocantes.containsKey(id)) {
            return Response.accepted().status(Status.ACCEPTED).build();
        }
        return Response.accepted().status(Status.NOT_FOUND).build();
    }

    protected Brocante find(String libelle) {
        for (Brocante Brocante : brocantes.values()) {
            if (Brocante.getLibelle().equals(libelle)) {
                return Brocante;
            }
        }
        return null;
    }

    protected Brocante find(int id) {
        return brocantes.get(id);
    }
	*/
	
    
    /*
    @GET
    @Path("/{name}")
    public Brocante getBrocante(@PathParam("name") String name) {
        Brocante out = find(name);
        if (out == null) {
            throw new WebApplicationException(404);
        }
        return out;
    }

    @GET
    public List<Brocante> getBrocantes(@DefaultValue("10") @QueryParam("limit") int limit) {
    	logger.info("ok "+brocantes.values());
        return new ArrayList<>(brocantes.values());
    }
*/
}
