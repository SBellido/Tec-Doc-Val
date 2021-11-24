package factoryPattern;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.MySqlClienteDAO;
import dao.MySqlFacturaDAO;
import dao.MySqlFactura_ProductoDAO;
import dao.MySqlProductoDAO;

public class MySqlDAOFactory  extends DAOFactory {

	// Se utilizan constantes para evitar repetición de código en la conexión
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String uri = "jdbc:mysql://localhost:3306/facturacion";
	private static final String user = "root";
	private static final String password = "";
	
	
	@Override
	public Connection createConnection() {
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(uri, user, password);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// Se obtienen las instancias del DAO de cada entidad
	@Override
	public MySqlClienteDAO getClienteDao(Connection conn) {
		return new MySqlClienteDAO(conn);
	}
	
	@Override
	public MySqlProductoDAO getProductoDao(Connection conn) {
		return new MySqlProductoDAO(conn);
	}
	
	@Override
	public MySqlFactura_ProductoDAO getFatura_ProductoDao(Connection conn) {
		return new MySqlFactura_ProductoDAO(conn);
	}
	
	@Override
	public MySqlFacturaDAO getFacturaDao(Connection conn) {
		return new MySqlFacturaDAO(conn);
	}
}

