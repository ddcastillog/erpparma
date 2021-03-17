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
	
	private List<DTOProducto> carrito;
	
	private double totalcarrito;
	
	@PostConstruct
	
	public void inicializar ()  {
		
		listaproductos = mClientes.generardatosProductos();
	}
	
	
	public void actionListenerAgregarCarrito (DTOProducto producto) {
		
		carrito = mClientes.agregardatosCarrito(carrito, producto);
		
		totalcarrito = mClientes.CalcularTotal(carrito);
		
	}
	
	
public void actionListenereliminarCarrito (DTOProducto producto) {
		
		carrito = mClientes.eliminardatosCarrito(carrito, producto.getCodigo());
		

		totalcarrito = mClientes.CalcularTotal(carrito);
		
	}



	
	
	
	
	
	
	
	
	
	

	public double getTotalcarrito() {
	return totalcarrito;
}


public void setTotalcarrito(double totalcarrito) {
	this.totalcarrito = totalcarrito;
}


	public List<DTOProducto> getCarrito() {
		return carrito;
	}








	public void setCarrito(List<DTOProducto> carrito) {
		this.carrito = carrito;
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
	
	
	






	public static long getSerialversionuid() {
		return serialVersionUID;
	}






	public BeanCarrito() {
		// TODO Auto-generated constructor stub
	}

}
