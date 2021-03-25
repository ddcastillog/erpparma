package erpparma.controller.clientes.carrito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.clientes.managers.ManagerClientes;
import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.inventario.managers.ManagerInventario;

@Named
@SessionScoped
public class BeanCarrito implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerClientes mClientes;

	private List<ParmaProducto> listaproductos;
	private List<ParmaProducto> listaproductos1;
	private List<ParmaProducto> carrito;
	private List<ParmaAjuste> cantidad;
	private List<ParmaAjuste> LISTAcantidad;
	private List<DTOProducto> dtoproducto;
	
	private DTOProducto dtoproducto1;
	
	private int cantidadcarrito;
	
	private double totalparcial;
	
	private List<ParmaInventario> inventario;
	
	private double totalcarrito;
	
	
	@Inject
	
	
	
	private BeanDetallePedido detallepedidos;
	
	
	@EJB
	
	private ManagerInventario minventario;

	@PostConstruct
	public void inicializar() {

	   listaproductos = minventario.stockVenta();
	 //  dtoproducto1 = mClientes.generardatosProductos();
		
	}

	public BeanCarrito() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	public DTOProducto getDtoproducto1() {
		return dtoproducto1;
	}

	public void setDtoproducto1(DTOProducto dtoproducto1) {
		this.dtoproducto1 = dtoproducto1;
	}

	public List<ParmaInventario> getInventario() {
		return inventario;
	}

	public void setInventario(List<ParmaInventario> inventario) {
		this.inventario = inventario;
	}

	public List<ParmaAjuste> getLISTAcantidad() {
		return LISTAcantidad;
	}

	public void setLISTAcantidad(List<ParmaAjuste> lISTAcantidad) {
		LISTAcantidad = lISTAcantidad;
	}

	public List<ParmaAjuste> getCantidad() {
		return cantidad;
	}

	public void setCantidad(List<ParmaAjuste> cantidad) {
		this.cantidad = cantidad;
	}

	public void actionListenerAgregarCarrito(ParmaProducto producto) {

	//	carrito = mClientes.agregardatosCarrito(carrito, producto);
		//int cantidad = detallepedidos.getCantidad();
		
		carrito = mClientes.agregardatosCarritoCantidad(carrito, producto,cantidadcarrito);
		
		totalparcial = mClientes.CalcularTotalparcial(producto, cantidadcarrito);

		totalcarrito = mClientes.CalcularTotal(carrito);

	}
	
	
	
	
	
	
	
	public BeanDetallePedido getDetallepedidos() {
		return detallepedidos;
	}

	public void setDetallepedidos(BeanDetallePedido detallepedidos) {
		this.detallepedidos = detallepedidos;
	}

	public void actionListenerAgregarCarrito1(ParmaAjuste producto) {

		cantidad = mClientes.agregardatosCarrito1(cantidad, producto);

		totalcarrito = mClientes.CalcularTotal(getCarrito());

	}
	
	
	

	public void actionListenereliminarCarrito(ParmaProducto producto) {

		carrito = mClientes.eliminardatosCarrito(carrito, producto.getIdParmaProducto());

		totalcarrito = mClientes.CalcularTotal(carrito);

	}
	
	public void actionListenereliminarCarrito1(ParmaAjuste producto) {

		cantidad = mClientes.eliminardatosCarrito1(cantidad, producto.getParmaProducto().getIdParmaProducto());

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
	
	
	



	public List<DTOProducto> getDtoproducto() {
		return dtoproducto;
	}

	public void setDtoproducto(List<DTOProducto> dtoproducto) {
		this.dtoproducto = dtoproducto;
	}

	public double getTotalcarrito() {
		return totalcarrito;
	}
	
	
	

	public double getTotalparcial() {
		return totalparcial;
	}

	public void setTotalparcial(double totalparcial) {
		this.totalparcial = totalparcial;
	}

	public void setTotalcarrito(double totalcarrito) {
		this.totalcarrito = totalcarrito;
	}
	
	

	public int getCantidadcarrito() {
		return cantidadcarrito;
	}

	public void setCantidadcarrito(int cantidadcarrito) {
		this.cantidadcarrito = cantidadcarrito;
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
