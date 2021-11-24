package data.stored;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import service.rest.pojo.EstudianteCarrera;
import service.rest.repository.CarreraRepository;
import service.rest.repository.EstudianteRepository;

import java.sql.Timestamp;
public class Estudiante_CarreraData {
	
	@SuppressWarnings("deprecation")
	public static ArrayList<EstudianteCarrera> getEstudiante_Carrera(EntityManager em) throws FileNotFoundException, IOException{
		
			ArrayList<EstudianteCarrera> estudiantes = new ArrayList<EstudianteCarrera>();
		
			EstudianteRepository er = new EstudianteRepository(em);
			CarreraRepository cr = new CarreraRepository(em);
			CSVParser estudiante = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/estudiante_carrera.csv"));
			for (CSVRecord row : estudiante) {
				String fecha_inscripcion = row.get("fecha_finalizacion");
				Timestamp times = null;
				if(fecha_inscripcion != "") {
					times = new Timestamp(Timestamp.parse(fecha_inscripcion));
				}
				estudiantes.add(
					new EstudianteCarrera(
						er.getById((int) Integer.parseInt(row.get("num_libreta"))), 
						cr.getById((int) Integer.parseInt(row.get("id_carrera"))), 
						new Timestamp(Timestamp.parse(row.get("fecha_inscipcion"))), 
						times 
					)
				);
			}
			
			return estudiantes;

	}
}

