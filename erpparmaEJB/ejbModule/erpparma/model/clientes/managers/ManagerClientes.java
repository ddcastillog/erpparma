package erpparma.model.clientes.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import erpparma.model.clientes.dto.DTOProducto;

/**
 * Session Bean implementation class ManagerClientes
 */
@Stateless
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
    	producto.add(new DTOProducto(2, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(3, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(4, "Pan Cortado", 0.15));
    	producto.add(new DTOProducto(5, "Pan Cortado", 0.15));
    	
    	
    	return producto;
    	
    }
    
    
    public List<DTOProducto> agregardatosCarrito( List <DTOProducto> carrito , DTOProducto producto )  {

    	if (carrito == null) 
    		
    		carrito = new ArrayList<DTOProducto>();
    		
    		carrito.add(producto);
    		
    		
    	return carrito;	
    	
			
		}
    
    public List<DTOProducto> eliminardatosCarrito( List <DTOProducto> carrito , int idproducto )  {

    	if (carrito == null) 
    		
    		return null;
    	
    	int i = 0;
    	
    	for (DTOProducto producto : carrito) {
    		
    		if (producto.getCodigo() == idproducto) {
    			
    			carrito.remove(i);
    			break;
    			
    		}
    		
    		i++;
 	
			
		}
 	
    	return carrito;	
  	
		}
    
    
    public double CalcularTotal (List <DTOProducto> carrito ) {	
    	
    	double suma = 0;
    	
    	for (DTOProducto producto : carrito) 
    		
    		suma += producto.getPrecio();
			
			
		return suma;
    	
    }
		
    	
}
