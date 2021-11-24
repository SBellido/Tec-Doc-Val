package classes;

public class Factura {
	private String idCliente;
	private String idFactura;

	public Factura(String idFactura, String idCliente) {
		super();
		this.idCliente = idCliente;
		this.idFactura = idFactura;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	@Override
	public String toString() {
		return "Invoice [idCliente=" + idCliente + ", idFactura=" + idFactura + "]";
	}
}
