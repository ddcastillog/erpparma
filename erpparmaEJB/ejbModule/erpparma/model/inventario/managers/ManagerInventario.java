package erpparma.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
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
    public List<ParmaProducto> findAllParmaProducto(){
    	return mDAO.findAll(ParmaProducto.class);
    }  
  //Insertar productos
    public void insertarParmaProducto(ParmaProducto producto,Integer idParmaTipoProducto) throws Exception{
    	ParmaTipoProducto tipoProducto= (ParmaTipoProducto)mDAO.findById(ParmaTipoProducto.class, idParmaTipoProducto);
    	producto.setParmaTipoProducto(tipoProducto);
    	mDAO.insertar(producto);
    }
  //Actualizar producto
    public void actualizarParmaProducto(ParmaProducto producto,Integer idParmaTipoProducto) throws Exception{
    	ParmaTipoProducto tipoProducto= (ParmaTipoProducto)mDAO.findById(ParmaTipoProducto.class, idParmaTipoProducto);
    	producto.setParmaTipoProducto(tipoProducto);
    	mDAO.actualizar(producto);
    }
  //Elimar producto
    public void eliminarParmaProducto(Integer idParmaProducto) throws Exception{
    	mDAO.eliminar(ParmaProducto.class, idParmaProducto);
    }
    
  //Tipo de producto
    public List<ParmaTipoProducto> findAllParmaTipoProducto(){
    	return mDAO.findAll(ParmaTipoProducto.class);
    }
  //Insertar Tipo de producto
    public void insertarParmaTipoProducto(ParmaTipoProducto tipoProducto) throws Exception{    	
    	mDAO.insertar(tipoProducto);
    }
  //Actualizar Tipo de producto
    public void actualizarParmaTipoProducto(ParmaTipoProducto tipoProducto) throws Exception{
    	mDAO.actualizar(tipoProducto);
    }
  //Elimar Tipo de producto
    public void eliminarParmaTipoProducto(Integer idParmaTipoProducto) throws Exception{
    	mDAO.eliminar(ParmaTipoProducto.class, idParmaTipoProducto);
    }
  //Tipo de producto
    public List<ParmaAjuste> findAllParmaAjuste(){
    	return mDAO.findAll(ParmaAjuste.class);
    }
  //Tipo de producto
    public void insertarParmaAjuste(ParmaAjuste ajuste) throws Exception{
    List<ParmaInventario> inventario= mDAO.findWhere(ParmaAjuste.class,
    		"o.parmaProducto='"+ajuste.getParmaProducto().getIdParmaProducto()+"'", null);
    try {
		ParmaInventario inve=inventario.get(0);
		if(ajuste.getTipoAjuste()) {
			int cantidad= inve.getCantidad().intValue()+ajuste.getCantidadAjuste().intValue();
			inve.setCantidad(cantidad);
		}else {
			int cantidad= inve.getCantidad().intValue()-ajuste.getCantidadAjuste().intValue();
			inve.setCantidad(cantidad);
		}
		mDAO.actualizar(inve);
		mDAO.insertar(ajuste);
		
	} catch (Exception e) {
		ParmaInventario newinve= new ParmaInventario();
	    newinve.setParmaProducto(ajuste.getParmaProducto());
	    newinve.setCantidad(ajuste.getCantidadAjuste().intValue());
	    mDAO.insertar(newinve);
	    mDAO.insertar(ajuste);
	}
    }
    
    

}
