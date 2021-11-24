package Entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "Estudiante_Carrera")
@NamedQueries(value = {
		@NamedQuery(name = EstudianteCarrera.BUSCAR_FECHAS_INSCRIPCION, query = "SELECT ec.fecha_inscripcion FROM EstudianteCarrera ec ORDER BY ec.fecha_inscripcion ")})
public class EstudianteCarrera implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BUSCAR_FECHAS_INSCRIPCION = "EstudianteCarrera.buscarFechasInscripcion";

	@Id
	@ManyToOne
	@JoinColumn(name = "num_libreta", referencedColumnName = "num_libreta")
	private Estudiante estudiante;

	@ManyToOne
	@JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera")
	private Carrera carrera;

	@Column
	private Timestamp fecha_inscripcion;

	@Column(nullable = true)
	private Timestamp fecha_finalizacion;

	public EstudianteCarrera() {
	}

	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, Timestamp fecha_inscipcion,
			Timestamp fecha_finalizacion) {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(carrera, estudiante, fecha_finalizacion, fecha_inscripcion);
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

}