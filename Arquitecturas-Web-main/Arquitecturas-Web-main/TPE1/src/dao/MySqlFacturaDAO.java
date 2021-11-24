package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Factura;
import daosInterfaces.FacturaDAO;

public class MySqlFacturaDAO  implements FacturaDAO{
	private Connection conn;

	public MySqlFacturaDAO(Connection conn) {
		this.conn = conn;
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean insertFactura(Factura o) {
		String insert = "INSERT INTO Factura(idFactura, idCliente) VALUES(?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, o.getIdFactura());
			ps.setString(2, o.getIdCliente());
			ps.executeUpdate();
			ps.close();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Se obtienen todas las filas de la tabla factura
	@Override
	public List<Factura> getAllFactura() {
		List<Factura> retorno = new ArrayList<Factura>();
		try {
			String selectFacturas = "SELECT idCliente, idFactura FROM Factura";
			PreparedStatement psf = conn.prepareStatement(selectFacturas);
			ResultSet rsf = psf.executeQuery();
			while (rsf.next())
				retorno.add(new Factura(rsf.getString(1), rsf.getString(2)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
