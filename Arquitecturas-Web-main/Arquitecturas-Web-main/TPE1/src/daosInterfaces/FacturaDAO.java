package daosInterfaces;

import java.util.List;

import classes.Factura;

public interface FacturaDAO {
	boolean insertFactura(Factura o);
	List<Factura> getAllFactura();
}
