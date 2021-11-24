package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Factura_Producto;
import daosInterfaces.Factura_ProductoDAO;

public class MySqlFactura_ProductoDAO  implements Factura_ProductoDAO {
	
	private Connection conn;

	public MySqlFactura_ProductoDAO(Connection conn) {
		this.conn = conn;
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean insertFactura_Producto(Factura_Producto p) {
		try {
			String insert = "INSERT INTO Factura_Producto(idFactura, idProducto, cantidad) VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, p.getIdFactura());
			ps.setString(2, p.getIdProducto());
			ps.setString(3, p.getCantidad());
			ps.executeUpdate();
			ps.close();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Se obtienen todas las filas de la tabla Factura_Producto
	@Override 
	public List<Factura_Producto> getAllFactura_Producto() {
		List<Factura_Producto> retorno = new ArrayList<Factura_Producto>();
		try {
			String selectFP = "SELECT idProducto, idFactura, cantidad FROM Factura_Producto";
			PreparedStatement psfp = conn.prepareStatement(selectFP);
			ResultSet rsfp = psfp.executeQuery();
			while (rsfp.next())
				retorno.add(new Factura_Producto(rsfp.getString(1), rsfp.getString(2), rsfp.getString(3)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
