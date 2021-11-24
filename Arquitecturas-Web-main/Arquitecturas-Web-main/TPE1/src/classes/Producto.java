package classes;

public class Producto {
	private String idProducto;
	private String valor;
	private String nombre;

	public Producto(String idProducto, String nombre, String valor) {
		super();
		this.idProducto = idProducto;
		this.valor = valor;
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdProducto() {
		return idProducto;
	}

	@Override
	public String toString() {
		return "Product [idProducto=" + idProducto + ", valor=" + valor + ", nombre=" + nombre + "]";
	}
}
