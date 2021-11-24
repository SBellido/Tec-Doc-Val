package data.stored;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import service.rest.pojo.Carrera;

public class CarreraData {
	
	public static ArrayList<Carrera> getCarreras() throws FileNotFoundException, IOException{
		
			ArrayList<Carrera> carreras = new ArrayList<Carrera>();
		
//			@SuppressWarnings("deprecation")
//			CSVParser carrera = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/carreras.csv"));
//			for (CSVRecord row : carrera) {
//				carreras.add(new Carrera(Integer.parseInt(row.get("id_carrera")), row.get("nombre")));
//			}
			@SuppressWarnings("deprecation")
			CSVParser carrera = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/carreras.csv"));
			for (CSVRecord row : carrera) {
				carreras.add(new Carrera(row.get("nombre")));
			}
			return carreras;

	}
}