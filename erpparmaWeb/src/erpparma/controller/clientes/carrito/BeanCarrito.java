package erpparma.controller.clientes.carrito;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.clientes.managers.ManagerClientes;
import erpparma.model.core.entities.ParmaProducto;

@Named
@SessionScoped
public class BeanCarrito implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerClientes mClientes;

	private List<ParmaProducto> listaproductos;
	private List<ParmaProducto> listaproductos1;
	private List<ParmaProducto> carrito;
	private double totalcarrito;

	@PostConstruct

	public void inicializar() {

		listaproductos = mClientes.generardatosProductos();

	}

	public BeanCarrito() {
		// TODO Auto-generated constructor stub
	}

	
	public void actionListenerAgregarCarrito(ParmaProducto producto) {

		carrito = mClientes.agregardatosCarrito(carrito, producto);

		totalcarrito = mClientes.CalcularTotal(getCarrito());

	}
	
	public void actionListenereliminarCarrito(ParmaProducto producto) {

		carrito = mClientes.eliminardatosCarrito(carrito, producto.getIdParmaProducto());

		totalcarrito = mClientes.CalcularTotal(carrito);

	}
	
	public String actionComprar1() {
		listaproductos = mClientes.generardatosProductos();
		return "carrito";
	}

	public List<ParmaProducto> getListaproductos1() {
		return listaproductos1;
	}

	public void setListaproductos1(List<ParmaProducto> listaproductos1) {
		this.listaproductos1 = listaproductos1;
	}


	public String actionComprar() {

		return "usuarios";
	}



	public double getTotalcarrito() {
		return totalcarrito;
	}

	public void setTotalcarrito(double totalcarrito) {
		this.totalcarrito = totalcarrito;
	}

	public List<ParmaProducto> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<ParmaProducto> carrito) {
		this.carrito = carrito;
	}

	public ManagerClientes getmClientes() {
		return mClientes;
	}

	public void setmClientes(ManagerClientes mClientes) {
		this.mClientes = mClientes;
	}

	public List<ParmaProducto> getListaproductos() {
		return listaproductos;
	}

	public void setListaproductos(List<ParmaProducto> listaproductos) {
		this.listaproductos = listaproductos;
	}


}
