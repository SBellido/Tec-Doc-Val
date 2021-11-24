package daosInterfaces;

import java.util.List;

import classes.Producto;

public interface ProductoDAO {
	boolean insertProducto(Producto o);
	List<Producto> getAllProducto();
	Producto getProductoMayorRecaudacion();
}