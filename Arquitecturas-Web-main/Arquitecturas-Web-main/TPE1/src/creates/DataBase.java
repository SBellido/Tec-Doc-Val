package creates;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esta clase es el ABM del esquema
public class DataBase {
	
	public static void dropDatabase() throws SQLException {
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		String host = "jdbc:mysql://localhost:3306";
		String uri = host + "/facturacion";

		try {
			Connection conn_host = DriverManager.getConnection(uri, "root", "");
			conn_host.setAutoCommit(false);
			
			String create = "DROP DATABASE facturacion";
			conn_host.prepareStatement(create).execute();	
			conn_host.commit();
			
			
			conn_host.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createDatabase() throws SQLException {
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		String host = "jdbc:mysql://localhost:3306";
		String uri = host + "/facturacion";

		try {
			Connection conn_host = DriverManager.getConnection(host, "root", "");
			conn_host.setAutoCommit(false);
			
			String create = "CREATE DATABASE facturacion";
			String use = "USE facturacion";
			conn_host.prepareStatement(create).execute();
			conn_host.prepareStatement(use).execute();			
			conn_host.commit();

			initSchema(conn_host);
		
			conn_host.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initSchema(Connection conn) throws SQLException {

		String Cliente = "CREATE TABLE Cliente (" + "    idCliente int NOT NULL,"
				+ "    nombre varchar(500) NOT NULL," + "    email varchar(150) NOT NULL,"
				+ "    CONSTRAINT PK_CLIENTE PRIMARY KEY (idCliente)" + ")";

		String Factura = "CREATE TABLE Factura (" + "    idFactura int NOT NULL,"
				+ "    idCliente int NOT NULL," + "    CONSTRAINT PK_FACTURA PRIMARY KEY (idFactura)" + ")";

		String Factura_Producto = "CREATE TABLE Factura_Producto (" + "    idFactura int NOT NULL,"
				+ "    idProducto int NOT NULL," + "    cantidad int NOT NULL,"
				+ "    CONSTRAINT Factura_Producto_pk PRIMARY KEY (idFactura,idProducto)" + ")";

		String Producto = "CREATE TABLE Producto (" + "    idProducto int NOT NULL,"
				+ "    nombre varchar(45) NOT NULL," + "    valor float NOT NULL,"
				+ "    CONSTRAINT Producto_pk PRIMARY KEY (idProducto)" + ")";

		conn.prepareStatement(Cliente).execute();
		conn.prepareStatement(Factura).execute();
		conn.prepareStatement(Factura_Producto).execute();
		conn.prepareStatement(Producto).execute();

		conn.commit();
	}

	public static void clearSchema(Connection conn) throws SQLException {

		String deleteFactura_Producto = "DELETE FROM Factura_Producto";
		String deleteFactura = "DELETE FROM Factura";
		String deleteProducto = "DELETE FROM Producto";
		String deleteCliente = "DELETE FROM Cliente";

		String dropFactura_Producto = "DROP TABLE Factura_Producto";
		String dropFactura = "DROP TABLE Factura";
		String dropProducto = "DROP TABLE Producto";
		String dropCliente = "DROP TABLE Cliente";

		conn.prepareStatement(deleteFactura_Producto).execute();
		conn.prepareStatement(deleteFactura).execute();
		conn.prepareStatement(deleteProducto).execute();
		conn.prepareStatement(deleteCliente).execute();

		conn.prepareStatement(dropFactura_Producto).execute();
		conn.prepareStatement(dropFactura).execute();
		conn.prepareStatement(dropProducto).execute();
		conn.prepareStatement(dropCliente).execute();

		conn.commit();

	}
}
