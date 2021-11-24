package service.rest.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import service.rest.LifecycleReader;
import service.rest.pojo.*;
import service.rest.repository.*;

@Path("/estudiante")
public class EstudianteRest {

	// c) recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		EntityManager em = LifecycleReader.createEntityManager();
		List<Estudiante> e = this.getRepository(em).getAll();
		em.close();
		return this.getResponse(Status.OK, e);
	}
	
	// e) recuperar todos los estudiantes, en base a su género.
	@GET
	@Path("/genero/{genero}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllByGender(@PathParam("genero") String genero) {
		EntityManager em = LifecycleReader.createEntityManager();
		List<Estudiante> e = this.getRepository(em).getPorGenero(genero);
		em.close();
		return this.getResponse(Status.OK, e);
	}
	
	// d) recuperar un estudiante, en base a su número de libreta universitaria.
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		EntityManager em = LifecycleReader.createEntityManager();
		Estudiante e = this.getRepository(em).getById(id);
		em.close();
		if(e == null) {
			return this.getResponse(Status.NOT_FOUND);
		}
		return this.getResponse(Status.OK, e);
	}
	
	// a) dar de alta un estudiante
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDog(Estudiante e) {
		EntityManager em = LifecycleReader.createEntityManager();
		this.getRepository(em).add(e);
		em.close();
		return this.getResponse(Status.CREATED, e);
	}

	private EstudianteRepository getRepository(EntityManager em) {
		return new EstudianteRepository(em);
	}
	
	private Response getResponse(Status status) {
		return getResponse(status, null);
	}

	private Response getResponse(Status status, Object o) {
		if(o != null) {			
			return Response.status(status.getStatusCode(), status.toString()).entity(o).build();
		} else {
			return Response.status(status.getStatusCode(), status.toString()).build();
		}
	}
}
 