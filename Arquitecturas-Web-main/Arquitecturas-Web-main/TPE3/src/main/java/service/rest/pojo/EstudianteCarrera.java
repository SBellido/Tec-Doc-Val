package service.rest.pojo;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "Estudiante_Carrera")
@NamedQueries(value = {
		@NamedQuery(name = EstudianteCarrera.BUSCAR_FECHAS_INSCRIPCION, query = "SELECT ec.fecha_inscripcion FROM EstudianteCarrera ec ORDER BY ec.fecha_inscripcion ")})
public class EstudianteCarrera { 

	public static final String BUSCAR_FECHAS_INSCRIPCION = "EstudianteCarrera.buscarFechasInscripcion";

    @EmbeddedId
    private EstudianteCarreraId id;
    
    @ManyToOne
    @MapsId("num_libreta")
    @JoinColumn(name = "num_libreta")
	private Estudiante estudiante;

    @ManyToOne
    @MapsId("id_carrera")
    @JoinColumn(name = "id_carrera")
	private Carrera carrera;
    
	@Column
	private Timestamp fecha_inscripcion;

	@Column(nullable = true)
	private Timestamp fecha_finalizacion;

	public EstudianteCarrera() { }

	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, Timestamp fecha_inscipcion,
			Timestamp fecha_finalizacion) {
		super();
		this.id =  new EstudianteCarreraId(carrera.getId(), estudiante.getNum_libreta());
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.fecha_inscripcion = fecha_inscipcion;
		this.fecha_finalizacion = fecha_finalizacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Timestamp getFecha_inscripcion() {
		return fecha_inscripcion;
	}

	public void setFecha_inscripcion(Timestamp fecha_inscipcion) {
		this.fecha_inscripcion = fecha_inscipcion;
	}

	public Timestamp getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public void setFecha_finalizacion(Timestamp fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}

	@Override
	public String toString() {
		return "\r\n Estudiante_Carrera \r\n estudiante = " + estudiante + ", \r\n carrera = " + carrera
				+ ", fecha_inscripcion=" + fecha_inscripcion + ", fecha_finalizacion=" + fecha_finalizacion + "]";
	}
}