package erpparma.model.clientes.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerPedidos
 */
@Stateless
@LocalBean
public class ManagerPedidos {
	
	@EJB
	private ManagerDAO mDAO;
	
	@PersistenceContext
	private EntityManager em;


    /**
     * Default constructor. 
     */
    public ManagerPedidos() {
        // TODO Auto-generated constructor stub
    }
    
    // PEDIDOS

	public List<ParmaDetallePedido> findAllPedidos() {
		return mDAO.findAll(ParmaDetallePedido.class);
	}
	
	// PRODUCTOS
		public List<ParmaDetallePedido> findAllParmadetalle() {
			return mDAO.findAll(ParmaDetallePedido.class);
		}
		
		
		public void insertarPedido (int cantidad , int id_pedido , int id_producto) {
			
			DTOProducto dtoproducto = new DTOProducto();
			ParmaDetallePedido pedido1 = new ParmaDetallePedido();
			ParmaPedido pedidosparma= em.find(ParmaPedido.class, id_pedido);
			ParmaProducto producto = em.find(ParmaProducto.class, id_producto);
			
			
			pedido1.setCantidad(dtoproducto.getCantidad());
			pedido1.setParmaPedido(pedidosparma);
			pedido1.setParmaProducto(producto);
			
			try {
				mDAO.insertar(pedido1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
		
		
	
	
	

}
