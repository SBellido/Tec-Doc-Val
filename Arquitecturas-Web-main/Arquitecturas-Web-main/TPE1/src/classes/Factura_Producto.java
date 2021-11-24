package classes;

public class Factura_Producto {
	
	private String idProducto;
	private String idFactura;
	private String cantidad;
	
	public Factura_Producto(String idFactura, String idProducto, String cantidad) {
		super();
		this.idProducto = idProducto;
		this.idFactura = idFactura;
		this.cantidad = cantidad;
	}
	
	public String getIdProducto() {
		return idProducto;
	}
	
	public String getIdFactura() {
		return idFactura;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "Factura_Producto [idProducto=" + idProducto + ", idFactura=" + idFactura + ", cantidad=" + cantidad
				+ "]";
	}

}
