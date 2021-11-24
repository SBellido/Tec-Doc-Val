package service.rest.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import service.rest.pojo.Estudiante;

public class EstudianteRepository extends Repository<Estudiante> {

	public EstudianteRepository(EntityManager em) {
		super(em, Estudiante.class);
	}
	
	public List<Estudiante> getAll() {
		TypedQuery<Estudiante> tp = this.em.createNamedQuery(
				Estudiante.BUSCAR_TODOS, Estudiante.class);
		return tp.getResultList();
	}
	
//	@Override 
//	public Estudiante getById(int id) {
//		TypedQuery<Estudiante> tp = this.em.createNamedQuery(
//				Estudiante.BUSCAR_POR_NUM_LIBRETA, Estudiante.class).setParameter("num_libretaPass", id);
//		return tp.getSingleResult();
//	}

	public List<Estudiante> getPorGenero(String genero) {
		TypedQuery<Estudiante> tp = this.em.createNamedQuery(
				Estudiante.BUSCAR_POR_GENERO, Estudiante.class).setParameter("generoPass", genero);
		return tp.getResultList();
	}
}