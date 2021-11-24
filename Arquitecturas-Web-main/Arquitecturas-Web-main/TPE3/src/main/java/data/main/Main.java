package data.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import javax.persistence.*;
import service.rest.repository.*;
import service.rest.pojo.*;
import data.stored.*;
/*
 * Consignas:
 * Consigna 1:
 *         Los diagramas se encuentran la carpeta diagramas del mismo proyecto, las entidades se
 *      encuentran en el paquete Entities
 *  Consigna 2 y 3:
 *       Todas las consultas están resultas con Named Queries, y se encuentran repartidas entre  
 *       sus respectivas clases. La ejecución de las mismas se lleva a cabo en sus
 *       respectivos repositorios
 * 
 * 
 * 
 * Descripción de paquetes:
 * Entities:
 *      Contiene las clases de sql mapeadas a java
 *  Data:
 *       Contiene los datos con los que se cargará la BBDD
 *  Ropository:
 *       Contiene las clases del patrón repository
 *  Main:
 *         Contiene el código con el que se instancian las clases y se prueba el mismo
 *
 *
 *
 *  Usuario y contraseña para la conexión a lña BBDD:
 *      Usuario: "root"
 *      Contraseña: ""
 * */

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String dataBaseName = "tudai_aweb_tp3_entregable";
		createDataBase(dataBaseName);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
		EntityManager em = emf.createEntityManager();

		EstudianteRepository er = new EstudianteRepository(em);
		CarreraRepository cr = new CarreraRepository(em);
		Repository<EstudianteCarrera> ecr = new Repository<EstudianteCarrera>(em, EstudianteCarrera.class);

		er.addAll(EstudianteData.getEstudiantes());
		cr.addAll(CarreraData.getCarreras());
		ecr.addAll(Estudiante_CarreraData.getEstudiante_Carrera(em));


		em.close();
		emf.close();
	}

	
	// Esta función crea una la BBDD: si existe, la borra, y luego la crea
	public static void createDataBase(String dataBaseName) {
		String existsDatabaseQuery = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = '"
				+ dataBaseName + "'";
		String dropQuery = "DROP DATABASE " + dataBaseName;
		String createQuery = "CREATE DATABASE " + dataBaseName;
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		String uri = "jdbc:mysql://localhost:3306/";

		try {
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			PreparedStatement ps0 = conn.prepareStatement(existsDatabaseQuery);
			ResultSet rs = ps0.executeQuery();
			if (rs.next()) {
				PreparedStatement ps1 = conn.prepareStatement(dropQuery);
				ps1.executeUpdate();
				conn.commit();
			}
			PreparedStatement ps2 = conn.prepareStatement(createQuery);
			ps2.executeUpdate();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
