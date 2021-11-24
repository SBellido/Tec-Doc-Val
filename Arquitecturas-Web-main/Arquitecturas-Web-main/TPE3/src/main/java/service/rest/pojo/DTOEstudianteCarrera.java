package service.rest.pojo;

import java.util.HashMap;
import java.util.List;

public class DTOEstudianteCarrera {

	private Carrera carrera;
	private HashMap<Integer, List<Estudiante>> inscriptos;
	private HashMap<Integer, List<Estudiante>> egresados;

	public DTOEstudianteCarrera(Carrera carrera, HashMap<Integer, List<Estudiante>> inscriptos,
			HashMap<Integer, List<Estudiante>> egresados) {
		super();
		this.carrera = carrera;
		this.inscriptos = inscriptos;
		this.egresados = egresados;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public HashMap<Integer, List<Estudiante>> getInscriptos() {
		return inscriptos;
	}

	public HashMap<Integer, List<Estudiante>> getEgresados() {
		return egresados;
	}

	@Override
	public String toString() {
		String data = "";
		for (Integer key : this.inscriptos.keySet()) {
			data += "Año = " + key + " \r\n";
			data += "Inscriptos = " + this.inscriptos.get(key).toString() + " \r\n";
		}
		for (Integer key : this.egresados.keySet()) {
			data += "Año = " + key + " \r\n";
			data += "Egresados = " + this.egresados.get(key).toString() + " \r\n";
		}

		return "Carrera = " + carrera.getNombre() + "\r\n" + data;
	}

}
