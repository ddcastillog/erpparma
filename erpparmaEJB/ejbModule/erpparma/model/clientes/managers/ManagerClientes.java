package erpparma.model.clientes.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import erpparma.model.clientes.dto.DTOProducto;

/**
 * Session Bean implementation class ManagerClientes
 */
@Singleton
@LocalBean
public class ManagerClientes {

    /**
     * Default constructor. 
     */
    public ManagerClientes() {
        // TODO Auto-generated constructor stub
    }
    
    public List<DTOProducto> generardatosProductos( )  {
    	
    	List<DTOProducto> producto = new ArrayList<DTOProducto>();
    	
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(1, "Pan Cortado", 0.15));
    	
    	return producto;
    	
    }

}
