package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Producto;
import daosInterfaces.ProductoDAO;

public class MySqlProductoDAO  implements ProductoDAO{
	private Connection conn;

	public MySqlProductoDAO(Connection conn) {
		this.conn = conn;
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean insertProducto(Producto p) {
		String insert = "INSERT INTO Producto VALUES(?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, p.getIdProducto());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getValor());
			ps.executeUpdate();
			ps.close();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	// Se obtienen todas las filas de la tabla producto
	@Override
	public List<Producto> getAllProducto() {
		List<Producto> retorno = new ArrayList<Producto>();
		try {
			String selectProducto = "SELECT idProducto, valor, nombre FROM Producto";
			PreparedStatement psp = conn.prepareStatement(selectProducto);
			ResultSet rsp = psp.executeQuery();
			while (rsp.next())
				retorno.add(new Producto(rsp.getString(1), rsp.getString(2),rsp.getString(3)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	// Se obtiene el producto de mayor recaudación
	@Override
	public Producto getProductoMayorRecaudacion() {
		Producto retorno = null;
		try {
			
			String query = "SELECT p.*, (COUNT(p.idProducto)*p.valor) AS total "
					+ "		 FROM Producto p "
					+ "		 JOIN Factura_Producto fp ON (p.idProducto = fp.idProducto) "
					+ "		 JOIN Factura f ON (fp.idFactura = f.idFactura) "
					+ "		 GROUP BY p.idProducto "
					+ "		 ORDER BY total DESC"
					+ "		 LIMIT 1";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				retorno = new Producto(rs.getString(1), rs.getString(2),rs.getString(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
