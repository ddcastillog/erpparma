package erpparma.model.pedido.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.managers.ManagerDAO;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.managers.ManagerFacturacion;

/**
 * Session Bean implementation class ManagerPedido
 */
@Stateless
@LocalBean
public class ManagerPedido {
	@EJB
	private ManagerDAO mDAO;	

	public ManagerPedido() {
		// TODO Auto-generated constructor stub
	}

	public List<ParmaPedido> findAllPedidos() {
		return mDAO.findAll(ParmaPedido.class);
	}	

	public List<ParmaPedido> findPedidosPendientes() {
		List<ParmaPedido> listPedidosP = new ArrayList<ParmaPedido>();
		for (ParmaPedido p : findAllPedidos()) {
			if (p.getEstadoPedido())
				listPedidosP.add(p);
		}
		return listPedidosP;
	}
	
	public ParmaPedido findPedidoById(int idPedido) throws Exception {
		ParmaPedido p=new ParmaPedido();
		p=(ParmaPedido) mDAO.findById(ParmaPedido.class, idPedido);
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public List<ParmaDetallePedido> findAllDetPedidosByIdPed(int idPedido){
		
		String clausula="id_pedido = '"+String.valueOf(idPedido)+"'";
		List<ParmaDetallePedido> listAllDetPedidos=mDAO.findWhere(ParmaDetallePedido.class, clausula, null);
		return listAllDetPedidos;
	}
		

	public void procesarPedido(int idPedido) throws Exception {
		ParmaPedido p = (ParmaPedido) mDAO.findById(ParmaPedido.class, idPedido);
		p.setEstadoPedido(!p.getEstadoPedido());
		System.out.println("procesar pedido... ");
		mDAO.actualizar(p);
	}

}
