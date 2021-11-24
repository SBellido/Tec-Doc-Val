package Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import Entities.Estudiante;

public class EstudianteData {
	
	public static ArrayList<Estudiante> getEstudiantes() throws FileNotFoundException, IOException{
			ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
		
			@SuppressWarnings("deprecation")
			CSVParser estudiante = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/estudiantes.csv"));
			for (CSVRecord row : estudiante) {
				estudiantes.add(new Estudiante(Integer.parseInt(row.get("num_libreta")), Integer.parseInt(row.get("num_doc")), row.get("nombre"), row.get("apellido"),
						Integer.parseInt(row.get("edad")), row.get("ciudad"), row.get("genero")));
			}
			
			return estudiantes;
	}
}
