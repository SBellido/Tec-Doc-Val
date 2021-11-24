package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Cliente;
import daosInterfaces.ClienteDAO;

public class MySqlClienteDAO implements ClienteDAO{
	private Connection conn;

	public MySqlClienteDAO(Connection conn) {
		this.conn = conn;
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public boolean insertCliente(Cliente o) {
		String insert = "INSERT INTO Cliente(idCliente, nombre, email) VALUES(?,?,?)";
		PreparedStatement ps;
		try {
			ps = this.conn.prepareStatement(insert);
			ps.setString(1, o.getIdCliente());
			ps.setString(2, o.getNombre());
			ps.setString(3, o.getEmail());
			ps.executeUpdate();
			ps.close();
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public List<Cliente> getAllCliente() {
		String selectClientes = "SELECT idCliente, nombre, email FROM Cliente";
		List<Cliente> retorno = new ArrayList<Cliente>();
		try {
			PreparedStatement psc;
			psc = this.conn.prepareStatement(selectClientes);
			ResultSet rsc = psc.executeQuery();
			while (rsc.next())
				retorno.add(new Cliente(rsc.getString(1), rsc.getString(2), rsc.getString(3)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	// Se obtiene la lista de clientes a los que se les facturo más
	// ordenados de mayor a menor
	public List<Cliente> getListaClientesSortFacturacion() {
		List<Cliente> retorno = new ArrayList<Cliente>();
		try {
			String query = "SELECT c.* "
					+ "		 FROM Cliente c JOIN Factura f ON (c.idCliente = f.idCliente) "
					+ "		 GROUP BY c.idCliente "
					+ "		 ORDER BY COUNT(c.idCliente) DESC";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				retorno.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
