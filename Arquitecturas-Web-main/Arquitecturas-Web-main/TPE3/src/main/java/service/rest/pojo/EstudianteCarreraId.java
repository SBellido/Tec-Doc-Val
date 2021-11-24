package service.rest.pojo;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class EstudianteCarreraId implements Serializable {

	private static final long serialVersionUID = -4885315620128432357L;

	@Column(name = "id_carrera")
	private Integer idCarrera;

	@Column(name = "num_libreta")
	private Integer numLibreta;

	public EstudianteCarreraId(Integer idCarrera, Integer numLibreta) {
		super();
		this.idCarrera = idCarrera;
		this.numLibreta = numLibreta;
	}

	public Integer getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}

	public Integer getNumLibreta() {
		return numLibreta;
	}

	public void setNumLibreta(Integer numLibreta) {
		this.numLibreta = numLibreta;
	}

}
