package main;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import classes.*;
import creates.DataBase;
import daosInterfaces.*;
import factoryPattern.DAOFactory;

public class Main {
	public static void main(String[] args) {
		
//		Si la base está creada se borra para evitar conflictos
		try {
			DataBase.dropDatabase();
		} catch (SQLException e) {
		}
		
		try {
			// Se crea la base de datos, se obtinene una instancia del Factory de Daos de MySql
			// y se le pide la conexión a la base de datos
			DataBase.createDatabase();			
			DAOFactory daof = DAOFactory.getDaoFactory();
			Connection conn = daof.createConnection();
			
			// Se obtienen las clases DAO para cada entidad
			ClienteDAO clienteDao= daof.getClienteDao(conn);	
			FacturaDAO facturaDao= daof.getFacturaDao(conn);	
			ProductoDAO productoDao= daof.getProductoDao(conn);	
			Factura_ProductoDAO facturaProductoDao = daof.getFatura_ProductoDao(conn);	
			
			// Se insertan los datos correspondientes a cada entidad
			loadData(conn, clienteDao, facturaDao, productoDao, facturaProductoDao);	
			
			// Se imprimen por consola los resultados de los ejercicios 3 y 4
			System.out.println("El producto que más recaudo: "+productoDao.getProductoMayorRecaudacion());
			System.out.println("Clientes a los que más se les facturó: "+clienteDao.getListaClientesSortFacturacion());

		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// Se crean las instancias para cada entidad y se insertan los datos+
	@SuppressWarnings("deprecation")
	public static void loadData(Connection conn, ClienteDAO clienteDao, FacturaDAO facturaDao, ProductoDAO productoDao, Factura_ProductoDAO facturaProductoDao) {
		try {
			
			CSVParser clientes;
			clientes = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/clientes.csv"));
			for (CSVRecord row : clientes)
				clienteDao.insertCliente(new Cliente( row.get("idCliente"), row.get("nombre"), row.get("email")));

			CSVParser facturas = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/facturas.csv"));
			for (CSVRecord row : facturas)
				facturaDao.insertFactura(new Factura(row.get("idFactura"), row.get("idCliente")));
			
			CSVParser f_p = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/facturas-productos.csv"));
			for (CSVRecord row : f_p)
				facturaProductoDao.insertFactura_Producto(new Factura_Producto(row.get("idFactura"), row.get("idProducto"), row.get("cantidad")));
			
			CSVParser productos = CSVFormat.DEFAULT.withHeader().parse(new FileReader("resources/productos.csv"));
			for (CSVRecord row : productos)
				productoDao.insertProducto(new Producto(row.get("idProducto"), row.get("nombre"), row.get("valor")));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
}
