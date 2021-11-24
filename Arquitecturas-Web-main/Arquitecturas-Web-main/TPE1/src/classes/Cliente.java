package classes;

public class Cliente {
	
	private String idCliente;
	private String nombre;
	private String email;
	
	public Cliente(String idCliente, String nombre, String email) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.email = email;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIdCliente() {
		return idCliente;
	}
	
	@Override
	public String toString() {
		return "	\nClient [idCliente=" + idCliente + ", nombre=" + nombre + ", email=" + email + "]";
	}

}
