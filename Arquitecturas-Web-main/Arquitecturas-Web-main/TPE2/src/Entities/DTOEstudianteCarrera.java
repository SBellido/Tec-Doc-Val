package Entities;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

	public List<Estudiante> getAniosIngreso(int anio) {
		return this.inscriptos.get(anio);
	}
	
	public List<Estudiante> getAniosEgreso(int anio) {
		return this.egresados.get(anio);
	}
	
	public Set<Integer> getAniosEgreso() {
		return this.egresados.keySet();
	}
	
	public Set<Integer> getAniosIngreso() {
		return this.egresados.keySet();
	}
	
	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
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
