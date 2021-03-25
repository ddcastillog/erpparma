package erpparma.controller.inventario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.auditoria.managers.ManagerAuditoria;
import erpparma.model.core.entities.AudBitacora;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
import erpparma.model.core.utils.ModelUtil;
import erpparma.model.inventario.managers.ManagerInventario;

@Named
@SessionScoped
public class BeanParmaProducto implements Serializable {
	@EJB
	private ManagerInventario mInventario;
	private List<ParmaProducto> listaProductosMateriaPrima;
	private List<ParmaProducto> listaProductosActivos;
	private List<ParmaProducto> listaProductosVentas;
	private List<ParmaTipoProducto> listaTipoProductos;
	private ParmaProducto newProducto;
	private ParmaProducto editProducto;	

	public BeanParmaProducto() {

	}

	@PostConstruct
	public void inicializacion() {		
		newProducto=new ParmaProducto();
		editProducto=new ParmaProducto();
	}
	public String actionCargarMenuProductos() {
		listaTipoProductos=mInventario.findAllParmaTipoProducto();
		listaProductosVentas= mInventario.findAllParmaProducto(listaTipoProductos.get(0).getIdParmaTipoProducto());
		listaProductosMateriaPrima= mInventario.findAllParmaProducto(listaTipoProductos.get(1).getIdParmaTipoProducto());	
		listaProductosActivos= mInventario.findAllParmaProducto(listaTipoProductos.get(2).getIdParmaTipoProducto());		
		return "productos?faces-redirect=true";
	}
	public void actionInsertarProducto(Integer idParmaTipoProducto) throws Exception {	
		try {
		mInventario.insertarParmaProducto(newProducto,listaTipoProductos.get(idParmaTipoProducto).getIdParmaTipoProducto());
		listaProductosVentas= mInventario.findAllParmaProducto(listaTipoProductos.get(0).getIdParmaTipoProducto());
		listaProductosMateriaPrima= mInventario.findAllParmaProducto(listaTipoProductos.get(1).getIdParmaTipoProducto());	
		listaProductosActivos= mInventario.findAllParmaProducto(listaTipoProductos.get(2).getIdParmaTipoProducto());
		newProducto=new ParmaProducto();		
		JSFUtil.crearMensajeINFO("Producto Creado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}

	public void actionActualizarProductos() throws Exception {	
		try {
		mInventario.actualizarParmaProducto(editProducto,editProducto.getParmaTipoProducto().getIdParmaTipoProducto());
		listaProductosVentas= mInventario.findAllParmaProducto(listaTipoProductos.get(0).getIdParmaTipoProducto());
		listaProductosMateriaPrima= mInventario.findAllParmaProducto(listaTipoProductos.get(1).getIdParmaTipoProducto());	
		listaProductosActivos= mInventario.findAllParmaProducto(listaTipoProductos.get(2).getIdParmaTipoProducto());
		editProducto=new ParmaProducto();		
		JSFUtil.crearMensajeINFO("Actualizar Productos");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void seteditProducto(ParmaProducto producto) {
		this.editProducto=producto;		
	}

	

	public ParmaProducto getNewProducto() {
		return newProducto;
	}

	public ParmaProducto getEditProducto() {
		return editProducto;
	}	

	public void setNewProducto(ParmaProducto newProducto) {
		this.newProducto = newProducto;
	}

	public void setEditProducto(ParmaProducto editProducto) {
		this.editProducto = editProducto;
	}	

	public List<ParmaTipoProducto> getListaTipoProductos() {
		return listaTipoProductos;
	}

	public void setListaTipoProductos(List<ParmaTipoProducto> listaTipoProductos) {
		this.listaTipoProductos = listaTipoProductos;
	}

	public List<ParmaProducto> getListaProductosMateriaPrima() {
		return listaProductosMateriaPrima;
	}

	public List<ParmaProducto> getListaProductosActivos() {
		return listaProductosActivos;
	}

	public List<ParmaProducto> getListaProductosVentas() {
		return listaProductosVentas;
	}

	public void setListaProductosMateriaPrima(List<ParmaProducto> listaProductosMateriaPrima) {
		this.listaProductosMateriaPrima = listaProductosMateriaPrima;
	}

	public void setListaProductosActivos(List<ParmaProducto> listaProductosActivos) {
		this.listaProductosActivos = listaProductosActivos;
	}

	public void setListaProductosVentas(List<ParmaProducto> listaProductosVentas) {
		this.listaProductosVentas = listaProductosVentas;
	}
	
	
}
