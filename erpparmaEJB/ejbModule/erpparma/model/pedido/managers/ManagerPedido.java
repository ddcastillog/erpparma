package erpparma.model.pedido.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.managers.ManagerDAO;

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
			if (p.getEstadoPedido() == true)
				listPedidosP.add(p);
		}
		return listPedidosP;
	}
	
	public void procesarPedido(int idPedido) throws Exception {
		ParmaPedido p=(ParmaPedido) mDAO.findById(ParmaPedido.class, idPedido);
		p.setEstadoPedido(!p.getEstadoPedido());
		System.out.println("procesar pedido... "+p.getEstadoPedido());
		mDAO.actualizar(p);
	}
	
	

}
