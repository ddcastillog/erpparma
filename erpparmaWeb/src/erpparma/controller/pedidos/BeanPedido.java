package erpparma.controller.pedidos;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.pedido.managers.ManagerPedido;

@Named
@SessionScoped
public class BeanPedido implements Serializable {
	@EJB
	private ManagerPedido mPedido;

	private List<ParmaPedido> listaPedidos;
	private ParmaPedido editEstadoPedido;

	public BeanPedido() {
		
	}

	public String actionMenuPedidos() {
		listaPedidos = mPedido.findPedidosPendientes();
		return "pedidos";
	}

	public void actionListenerProcesarPedido(int idPedido) {
		try {
			mPedido.procesarPedido(idPedido);
			listaPedidos = mPedido.findPedidosPendientes();
			JSFUtil.crearMensajeINFO("Pedido en proceso");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	
	

	public List<ParmaPedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<ParmaPedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public ParmaPedido getEditEstadoPedido() {
		return editEstadoPedido;
	}

	public void setEditEstadoPedido(ParmaPedido editEstadoPedido) {
		this.editEstadoPedido = editEstadoPedido;
	}
	
	
	

}
