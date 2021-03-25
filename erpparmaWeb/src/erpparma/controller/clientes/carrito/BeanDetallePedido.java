package erpparma.controller.clientes.carrito;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.clientes.managers.ManagerClientes;
import erpparma.model.clientes.managers.ManagerPedidos;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.pedido.managers.ManagerPedido;


@Named
@SessionScoped
public class BeanDetallePedido implements Serializable {
	
	
	private int cantidad;
	
	private int id_parma_pedido;
	
	private int id_parma_producto;
	
	private List<ParmaDetallePedido> listadetallepedido;
	private List<ParmaProducto> listaproductos;
	
	private ParmaDetallePedido edit;
	
	
	 @EJB
	 private ManagerPedidos mPedido;
	 @EJB
	 private ManagerClientes mclientes;
	 
	

	 @PostConstruct
	 
	 public void inicializar() {
		 
		// listadetallepedido= mPedido.findAllPedidos();
		 listaproductos = mclientes.generardatosProductos();
	 }
	
	 
	 
	 public void actionListenerCreaDestino() {
			try {
				
				mPedido.insertarPedido(cantidad, id_parma_pedido, id_parma_producto);
				
				listadetallepedido = mPedido.findAllParmadetalle();

				JSFUtil.crearMensajeINFO("Detalle pedido creado");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}

		}
	 
	 
	 
	 public void actionListenerSelecionarDetalle(ParmaDetallePedido  Detalle) {
			edit=  Detalle;
			System.out.println("Destino Selecionado: " + edit.getParmaProducto().getIdParmaProducto());

		}
	

	public ParmaDetallePedido getEdit() {
		return edit;
	}



	public void setEdit(ParmaDetallePedido edit) {
		this.edit = edit;
	}



	public ManagerPedidos getmPedido() {
		return mPedido;
	}



	public void setmPedido(ManagerPedidos mPedido) {
		this.mPedido = mPedido;
	}



	public ManagerClientes getMclientes() {
		return mclientes;
	}



	public void setMclientes(ManagerClientes mclientes) {
		this.mclientes = mclientes;
	}



	public BeanDetallePedido() {
		// TODO Auto-generated constructor stub
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public int getId_parma_pedido() {
		return id_parma_pedido;
	}


	public void setId_parma_pedido(int id_parma_pedido) {
		this.id_parma_pedido = id_parma_pedido;
	}


	public int getId_parma_producto() {
		return id_parma_producto;
	}


	public void setId_parma_producto(int id_parma_producto) {
		this.id_parma_producto = id_parma_producto;
	}


	public List<ParmaDetallePedido> getListadetallepedido() {
		return listadetallepedido;
	}


	public void setListadetallepedido(List<ParmaDetallePedido> listadetallepedido) {
		this.listadetallepedido = listadetallepedido;
	}


	public List<ParmaProducto> getListaproductos() {
		return listaproductos;
	}


	public void setListaproductos(List<ParmaProducto> listaproductos) {
		this.listaproductos = listaproductos;
	}
	
	
	

}
