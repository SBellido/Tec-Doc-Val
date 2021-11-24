package Repository;
import javax.persistence.EntityManager;

import Entities.EstudianteCarrera;


public class EstudianteCarreraRepository extends Repository<EstudianteCarrera> {

	public EstudianteCarreraRepository(EntityManager em) {
		super(em);
	}
	
	@Override
	public EstudianteCarrera getById(int id) {
		return this.em.find(EstudianteCarrera.class, id);
	}

}

