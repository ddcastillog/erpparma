package erpparma.controller.facturacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.managers.ManagerFacturacion;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BeanFacturacion implements Serializable {

	@EJB
	private ManagerFacturacion mf;

	private List<DTOClientes> clientes;

	private DTOClientes clienteSelected;

	public BeanFacturacion() {

	}
	
	

	@PostConstruct
	public void inicializar() {
		this.clientes = this.mf.findAllClientes();
	}

	public List<DTOClientes> getClientes() {
		return clientes;
	}

	public String actionChangeCliente(DTOClientes cliente) {
		this.clienteSelected = cliente;
		return "facturar";
	}

	public void setClientes(List<DTOClientes> clientes) {
		this.clientes = clientes;
	}

	public DTOClientes getClienteSelected() {
		return clienteSelected;
	}

	public void setClienteSelected(DTOClientes clienteSelected) {
		this.clienteSelected = clienteSelected;
	}

	private static final long serialVersionUID = 1L;
}
