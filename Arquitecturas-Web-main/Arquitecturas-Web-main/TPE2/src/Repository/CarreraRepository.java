package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import Entities.Carrera;
import Entities.DTOEstudianteCarrera;
import Entities.Estudiante;

public class CarreraRepository extends Repository<Carrera>{

	public CarreraRepository(EntityManager em) {
		super(em);
	}

	@Override
	public Carrera getById(int id) {
		return this.em.find(Carrera.class, id);
	}
	
	public List<Carrera> getCarrerasConInscriptos() {
		TypedQuery<Carrera> tp = this.em.createNamedQuery(
				Carrera.BUSCAR_CON_INSCRIPTOS, Carrera.class);
		return tp.getResultList();
	}
	
	public List<Carrera> getTodas() {
		TypedQuery<Carrera> tp = this.em.createNamedQuery(
				Carrera.BUSCAR_TODAS, Carrera.class);
		return tp.getResultList();
	}
	
	public List<DTOEstudianteCarrera> getReporteCarreras() {
		// Se buscan todas las fechas de ingreso
		TypedQuery<Integer> tdq1 = em.createNamedQuery(Carrera.BUSCAR_FECHAS_INGRESO, Integer.class);
		TypedQuery<Integer> tdq2 = em.createNamedQuery(Carrera.BUSCAR_FECHAS_EGRESO, Integer.class);
		
		List<Integer> fechasIngreso = tdq1.getResultList();
		List<Integer> fechasEgreso = tdq2.getResultList();
		
		// Se buscan todas las carreras
		List<Carrera> carreras = this.getTodas();
	
		ArrayList<DTOEstudianteCarrera> retorno = new ArrayList<DTOEstudianteCarrera>();
		for (Carrera c : carreras){						
			HashMap<Integer, List<Estudiante>> inscriptos = new HashMap<Integer, List<Estudiante>>();
			HashMap<Integer, List<Estudiante>> egresados = new HashMap<Integer, List<Estudiante>>();
            
			// Se buscan todos los inscriptos de una carrera en un año dado, y se los guarda en
            // un HashMap utilizando con clave el año, y como valor una Lista de estudiantes       
			for (Integer fi : fechasIngreso) {
				TypedQuery<Estudiante> tq1 = 
						em.createNamedQuery(
								Carrera.BUSCAR_INSCRIPTOS_DE_CARRARA_POR_FECHA, Estudiante.class)
						.setParameter("carreraId", c.getId()).setParameter("fecha", fi);
				List<Estudiante> estudianesInscriptos = tq1.getResultList();
				if(!estudianesInscriptos.isEmpty()) {	
					inscriptos.put(fi, estudianesInscriptos);
				}
			}
			
			// Se buscan todos los egresados de una carrera en un año dado, y se los guarda en
            // un HashMap utilizando con clave el año, y como valor una Lista de estudiantes       
			for(Integer fe : fechasEgreso){
				TypedQuery<Estudiante> tq2 = 
						em.createNamedQuery(
								Carrera.BUSCAR_EGRESADOS_DE_CARRARA_POR_FECHA, Estudiante.class)
						.setParameter("carreraId", c.getId()).setParameter("fecha", fe);
				List<Estudiante> estudianesEgresados = tq2.getResultList();
				if(!estudianesEgresados.isEmpty()) {	
					egresados.put(fe, estudianesEgresados);
				}
			}
			
			// Se instancia un DTOEstudianteCarrera, el cual guarda, por cada carrera, todos los inscriptos y egresados por cada año
			retorno.add(new DTOEstudianteCarrera(c, inscriptos, egresados));
		}
		
		return retorno;
	}
	
	public DTOEstudianteCarrera getReporte() {
		return null;
	}
	
}
