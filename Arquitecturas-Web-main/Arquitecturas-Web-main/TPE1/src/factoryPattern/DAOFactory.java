package factoryPattern;

import java.sql.Connection;

import daosInterfaces.*;

// Se implementó el patrón abstract Factory, dejando un sistema más robusto
// para una mejor escalabilidad
public abstract class DAOFactory {
	
	public abstract Connection createConnection();
	public abstract ClienteDAO getClienteDao(Connection conn);
	public abstract ProductoDAO getProductoDao(Connection conn);
	public abstract Factura_ProductoDAO getFatura_ProductoDao(Connection conn);
	public abstract FacturaDAO getFacturaDao(Connection conn);
	
	public static DAOFactory getDaoFactory() {
		return new MySqlDAOFactory();
	}
}
