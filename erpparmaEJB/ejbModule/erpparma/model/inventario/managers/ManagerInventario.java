package erpparma.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ThmEmpleado;
import erpparma.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerInventario
 */
@Stateless
@LocalBean
public class ManagerInventario {
	@EJB
	private ManagerDAO mDAO;
	
    public ManagerInventario() {
        // TODO Auto-generated constructor stub
    }
    //PRODUCTOS
    public List<ParmaProducto> findAllThmEmpleado(){
    	return mDAO.findAll(ParmaProducto.class);
    }

}
