package service.rest.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries(value = {
//		Consigna 2:
//		f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
		@NamedQuery(name = Carrera.BUSCAR_CON_INSCRIPTOS, query = ""
				+ "SELECT DISTINCT ec.carrera FROM EstudianteCarrera ec,  Estudiante e, Carrera c WHERE c.id = ec.carrera.id AND e.num_libreta = ec.estudiante.num_libreta GROUP BY ec.carrera.id_carrera ORDER BY COUNT(ec.estudiante)"),
//		Consigna 3: 
		// Se seleccionan todas las carreras ordenadas por su nombre
		@NamedQuery(name = Carrera.BUSCAR_TODAS, query = "SELECT c FROM Carrera c ORDER BY c.nombre"),
		// Se seleccionan las entidades EstudianteCarrera,  Estudiante, Carrera, y se las une por
        // su id en el WHERE. Luego, se las filtra los estudiantes a través del id de la carrera y una fecha de inscripción
		@NamedQuery(name = Carrera.BUSCAR_INSCRIPTOS_DE_CARRARA_POR_FECHA, query = "SELECT ec.estudiante FROM EstudianteCarrera ec,  Estudiante e, Carrera c WHERE c.id_carrera = ec.carrera.id_carrera AND e.num_libreta = ec.estudiante.num_libreta AND c.id_carrera = :carreraId AND YEAR (ec.fecha_inscripcion) = : fecha"),
		// Se seleccionan las entidades EstudianteCarrera,  Estudiante, Carrera, y se las une por
        // su id en el WHERE. Luego, se las filtra los estudiantes a través del id de la carrera y una fecha en la que egresan
		@NamedQuery(name = Carrera.BUSCAR_EGRESADOS_DE_CARRARA_POR_FECHA, query = "SELECT ec.estudiante FROM EstudianteCarrera ec,  Estudiante e, Carrera c WHERE c.id_carrera = ec.carrera.id_carrera AND e.num_libreta = ec.estudiante.num_libreta AND c.id_carrera = :carreraId AND YEAR (ec.fecha_finalizacion) = : fecha"),
		// Se seleccionan todos los años en los que ingresron estudiantes
		@NamedQuery(name = Carrera.BUSCAR_FECHAS_INGRESO, query = "SELECT YEAR(ec.fecha_inscripcion) FROM EstudianteCarrera ec GROUP BY YEAR (ec.fecha_inscripcion) ORDER BY YEAR (ec.fecha_inscripcion)"),
		// Se seleccionan todos los años en los que ingresron egresaron
		@NamedQuery(name = Carrera.BUSCAR_FECHAS_EGRESO, query = "SELECT YEAR(fecha_finalizacion) FROM EstudianteCarrera ec WHERE ec.fecha_finalizacion IS NOT NULL GROUP BY YEAR(ec.fecha_finalizacion) ORDER BY YEAR (ec.fecha_finalizacion)"),
//		g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
		// Se seleccionan las entidades EstudianteCarrera,  Estudiante, Carrera, y se las une por
        // su id en el WHERE. Luego, se las filtra los estudiantes a través del id de un número de libreta, y el nombre de una ciudad, ambos pasados por parametro
		@NamedQuery(name = Carrera.BUSCAR_ESTUDIANTES_POR_CIUDAD_RESIDENCIA, query = "SELECT ec.estudiante FROM EstudianteCarrera ec,  Estudiante e, Carrera c WHERE c.id_carrera = ec.carrera.id_carrera AND e.num_libreta = ec.estudiante.num_libreta AND c.id_carrera = :carreraId AND ec.estudiante.ciudad_residencia =: ciudadPass")})
public class Carrera {

	public static final String BUSCAR_CON_INSCRIPTOS = "Carrera.buscarConInscriptos";
	public static final String BUSCAR_INSCRIPTOS = "Carrera.buscarInscriptos";
	public static final String BUSCAR_TODAS = "Carrera.buscarTodas";
	public static final String BUSCAR_INSCRIPTOS_DE_CARRARA_POR_FECHA = "Carrera.buscarInscriptosDeCarreraPorFecha";
	public static final String BUSCAR_EGRESADOS_DE_CARRARA_POR_FECHA = "Carrera.buscarEgresadosDeCarreraPorFecha";
	public static final String BUSCAR_FECHAS_INGRESO = "Carrera.buscarFechasIngreso";
	public static final String BUSCAR_FECHAS_EGRESO = "Carrera.buscarFechasEgreso";
	public static final String BUSCAR_ESTUDIANTES_POR_CIUDAD_RESIDENCIA = "Carrera.buscarPorCarreraFiltradoPorResidencia";

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_carrera;

	@Column
	private String nombre;

	@OneToMany(mappedBy = "carrera")
	private List<EstudianteCarrera> estudiantes;
	public Carrera() {}
	
	public Carrera(String nombre) {
		this.nombre = nombre;
		this.estudiantes = new ArrayList<EstudianteCarrera>();
	}

	public Carrera(int id, String nombre) {
		this.id_carrera = id;
		this.nombre = nombre;
		this.estudiantes = new ArrayList<EstudianteCarrera>();
	}

	public int getId() {
		return id_carrera;
	}

	public void setId(int id) {
		this.id_carrera = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Carrera [id=" + id_carrera + ", nombre=" + nombre + "]";
	}

}
