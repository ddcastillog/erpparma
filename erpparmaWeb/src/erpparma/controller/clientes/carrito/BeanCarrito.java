package erpparma.controller.clientes.carrito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import erpparma.controller.seguridades.BeanSegLogin;
import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.clientes.managers.ManagerClientes;
import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.dtos.DTOFactura;
import erpparma.model.facturacion.managers.ManagerFacturacion;
import erpparma.model.inventario.managers.ManagerInventario;
import erpparma.model.seguridades.dtos.LoginDTO;
import erpparma.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanCarrito implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerClientes mClientes;
	@EJB
	private ManagerFacturacion mf;	
	private List<ParmaProducto> listaproductos;
	private List<ParmaProducto> listaproductos1;
	private List<ParmaDetallePedido> carrito;
	private List<ParmaAjuste> cantidad;
	private List<ParmaAjuste> LISTAcantidad;
	private List<DTOProducto> dtoproducto;
	private DTOClientes clienteSelected;
	private DTOProducto dtoproducto1;
	private Integer facturaPos;
	List<ParmaFacturacionDetalle> items;

	private int cantidadcarrito;

	private double subtotalcarrito;

	private List<ParmaInventario> inventario;

	private double totalcarrito;

	@Inject

	private BeanDetallePedido detallepedidos;

	@Inject

	private BeanSegLogin usuario;

	@EJB

	private ManagerInventario minventario;

	@PostConstruct
	public void inicializar() {

		listaproductos = minventario.stockVenta();
		// dtoproducto1 = mClientes.generardatosProductos();

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

		carrito = mClientes.agregardatosCarritoCantidad(carrito, producto);

		// subtotalcarrito = mClientes.CalcularTotalparcial(producto, cantidadcarrito);

		// totalcarrito = mClientes.CalcularTotal(carrito);

	}

	public void insertarPedido() {

		ParmaPedido pedido = new ParmaPedido();

		pedido.setFechaPedido(new Date());
		pedido.setEstadoPedido(true);

		try {
			mClientes.insertarPedido(carrito, pedido, usuario.getIdSegUsuario());
			carrito = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BeanDetallePedido getDetallepedidos() {
		return detallepedidos;
	}

	public void setDetallepedidos(BeanDetallePedido detallepedidos) {
		this.detallepedidos = detallepedidos;
	}

	public void actionListenerAgregarCarrito1(ParmaAjuste producto) {

		cantidad = mClientes.agregardatosCarrito1(cantidad, producto);

		// totalcarrito = mClientes.CalcularTotal(getCarrito());

	}

	public void actionListenereliminarCarrito(ParmaProducto producto) {

		carrito = mClientes.eliminardatosCarrito(carrito, producto.getIdParmaProducto());

		// totalcarrito = mClientes.CalcularTotal(carrito);

	}

	public void actionListenereliminarCarrito1(ParmaAjuste producto) {

		cantidad = mClientes.eliminardatosCarrito1(cantidad, producto.getParmaProducto().getIdParmaProducto());

		// totalcarrito = mClientes.CalcularTotal(carrito);

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

	public double getSubtotalcarrito() {
		return subtotalcarrito;
	}

	public void setSubtotalcarrito(double subtotalcarrito) {
		this.subtotalcarrito = subtotalcarrito;
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

	public List<ParmaDetallePedido> getCarrito() {
		return carrito;
	}

	public void setCarrito(List<ParmaDetallePedido> carrito) {
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

	public String verfactura() {	
		List<DTOClientes> clientes=mf.findAllClientes();
		this.clienteSelected =null;
		for (int i = 0; i < clientes.size(); i++) {
			if(clientes.get(i).getId()==usuario.getIdSegUsuario()) {
				this.clienteSelected = clientes.get(i);
			}
		}		
		this.mf.findFacturas(this.clienteSelected);
		return "detalle" + "?faces-redirect=true";
	}

	public DTOClientes getClienteSelected() {
		return clienteSelected;
	}

	public void setClienteSelected(DTOClientes clienteSelected) {
		this.clienteSelected = clienteSelected;
	}
	public void loadItems(DTOFactura factura, Integer index) {

		this.mf.findDetallesFactura(factura);
		this.facturaPos = index;
		this.loadDetallesFactura();

	}
	public void loadDetallesFactura() {
		this.items = this.clienteSelected.getFacturas().get(this.facturaPos).getItems();
	}

	public Integer getFacturaPos() {
		return facturaPos;
	}

	public List<ParmaFacturacionDetalle> getItems() {
		return items;
	}

	public void setFacturaPos(Integer facturaPos) {
		this.facturaPos = facturaPos;
	}

	public void setItems(List<ParmaFacturacionDetalle> items) {
		this.items = items;
	}
	

}
