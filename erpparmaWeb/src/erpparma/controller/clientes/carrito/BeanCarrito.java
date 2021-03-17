package erpparma.controller.clientes.carrito;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.clientes.managers.ManagerClientes;

@Named
@SessionScoped
public class BeanCarrito implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerClientes mClientes;
	
	private List<DTOProducto> listaproductos;
	
	@PostConstruct
	
	public void inicializar ()  {
		
		mClientes.generardatosProductos();
	}
	
	
	
	
	

	public ManagerClientes getmClientes() {
		return mClientes;
	}






	public void setmClientes(ManagerClientes mClientes) {
		this.mClientes = mClientes;
	}






	public List<DTOProducto> getListaproductos() {
		return listaproductos;
	}






	public void setListaproductos(List<DTOProducto> listaproductos) {
		this.listaproductos = listaproductos;
	}






	public BeanCarrito() {
		// TODO Auto-generated constructor stub
	}

}
