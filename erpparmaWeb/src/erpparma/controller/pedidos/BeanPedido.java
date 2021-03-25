package erpparma.controller.pedidos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.controller.facturacion.BeanFacturar;
import erpparma.controller.seguridades.BeanSegLogin;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.managers.ManagerFacturacion;
import erpparma.model.pedido.managers.ManagerPedido;

@Named
@SessionScoped
public class BeanPedido implements Serializable {
	@EJB
	private ManagerPedido mPedido;
	@EJB
	private ManagerFacturacion mFacturacion;

	@Inject
	private BeanSegLogin login;
	@Inject
	private BeanFacturar bFacturar;

	private List<ParmaPedido> listaPedidosP;
	private ParmaFactura factura;
	private List<ParmaFacturacionDetalle> items;

	private ParmaPedido editEstadoPedido;

	public BeanPedido() {

	}

	public void init() {
		this.items = new ArrayList<ParmaFacturacionDetalle>();
		this.factura = new ParmaFactura();
		this.mFacturacion.initFacturaValues(factura);
	}

	@PostConstruct
	public void inicializar() {
		this.init();
	}

	public String actionMenuPedidos() {
		listaPedidosP = mPedido.findPedidosPendientes();
		return "pedidos";
	}

	public void actionListenerProcesarPedido(int idPedido) {
		try {
			mPedido.procesarPedido(idPedido);
			listaPedidosP = mPedido.findPedidosPendientes();

			ParmaPedido pedido = mPedido.findPedidoById(idPedido);
			// init detFactura
			List<ParmaDetallePedido> listAllDetPedidosByIdPed = mPedido.findAllDetPedidosByIdPed(idPedido);

			SegUsuario user = pedido.getSegUsuario();

			DTOClientes client = new DTOClientes(user.getIdSegUsuario(), user.getCodigo(), user.getApellidos(),
					user.getNombres(), user.getCorreo(), user.getActivo());
			this.init();

			
			for (int i = 0; i < pedido.getParmaDetallePedidos().size(); i++) {
				ParmaFacturacionDetalle facturaDet = new ParmaFacturacionDetalle();
				facturaDet.setCantidad(pedido.getParmaDetallePedidos().get(i).getCantidad());
				facturaDet.setParmaProducto(pedido.getParmaDetallePedidos().get(i).getParmaProducto());
				mFacturacion.updteSubtotalItemFactura(facturaDet, pedido.getParmaDetallePedidos().get(i).getCantidad());
				items.add(facturaDet);
			}

			factura.setParmaFacturacionDetalles(items);
			mFacturacion.calculateFacturaValues(factura);

			mFacturacion.crearFactura(this.factura, client, this.login.getIdSegUsuario());

			System.out.println("Factura creada correctamente");
			JSFUtil.crearMensajeINFO("Pedido procesado");

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			
		}
	}

	public List<ParmaPedido> getListaPedidosP() {
		return listaPedidosP;
	}

	public void setListaPedidosP(List<ParmaPedido> listaPedidosP) {
		this.listaPedidosP = listaPedidosP;
	}

	public ParmaPedido getEditEstadoPedido() {
		return editEstadoPedido;
	}

	public void setEditEstadoPedido(ParmaPedido editEstadoPedido) {
		this.editEstadoPedido = editEstadoPedido;
	}

}
