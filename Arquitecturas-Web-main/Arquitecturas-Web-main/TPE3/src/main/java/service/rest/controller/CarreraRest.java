package service.rest.controller;

import java.sql.Timestamp;
import java.util.Date;
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
@Path("/carrera")
public class CarreraRest {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		EntityManager em = LifecycleReader.createEntityManager();
		List<Carrera> c = this.getRepository(em).getAll();
		em.close();
		return this.getResponse(Status.OK, c);
	}
	
	// f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
	@GET
	@Path("con-inscriptos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllConInscriptos() {
		EntityManager em = LifecycleReader.createEntityManager();
		List<Carrera> c = this.getRepository(em).getCarrerasConInscriptos();
		em.close();
		return this.getResponse(Status.OK, c);
	}
	
	// g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
	@GET
	@Path("/{id}/estudiante/ciudad/{ciudad}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllByGender(@PathParam("ciudad") String ciudad, @PathParam("id") int id) {
		EntityManager em = LifecycleReader.createEntityManager();
		List<Estudiante> e = this.getRepository(em).getEtudiantesDeCarreraUnaCarreraPorSuCiudad(id, ciudad);
		em.close();
		return this.getResponse(Status.OK, e);
	}
	
	
//	h) generar un reporte de las carreras, que para cada carrera incluya información de los
//	inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
//	presentar los años de manera cronológica.
	@GET
	@Path("/reporte")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReporteCarreras() {
		EntityManager em = LifecycleReader.createEntityManager();
		List<DTOEstudianteCarrera> d = this.getRepository(em).getReporte();
		em.close();
		if(d == null) {
			return this.getResponse(Status.NOT_FOUND);
		}
		return Response.status(Status.OK.getStatusCode(), Status.OK.toString()).entity(d).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idCarrera}/matricular-estudiante/{idEstudiante}")
	public Response createDog(@PathParam("idCarrera")int idCarrera,@PathParam("idEstudiante") int idEstudiante) {
		EntityManager em = LifecycleReader.createEntityManager();
		Estudiante e = new EstudianteRepository(em).getById(idEstudiante);
		Carrera    c = this.getRepository(em).getById(idCarrera);
		Timestamp  t = new Timestamp(new Date().getTime()); 
		this.getRepository(em).matricularEstudiante(c, e, t);
		em.close();
		return this.getResponse(Status.CREATED);
	}

	private CarreraRepository getRepository(EntityManager em) {
		return new CarreraRepository(em);
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
