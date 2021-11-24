package daosInterfaces;


import java.util.List;

import classes.Cliente;

public interface ClienteDAO { 
	boolean insertCliente(Cliente o);
	List<Cliente> getAllCliente();
	List<Cliente> getListaClientesSortFacturacion();
}

