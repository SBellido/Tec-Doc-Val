package daosInterfaces;

import java.util.List;

import classes.Factura_Producto;

public interface Factura_ProductoDAO  {
	boolean insertFactura_Producto(Factura_Producto o);
	List<Factura_Producto> getAllFactura_Producto();
}
