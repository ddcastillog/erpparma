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
	private List<ParmaProducto> listaProductos;
	private List<ParmaTipoProducto> listaTipoProductos;
	private ParmaProducto newProducto;
	private ParmaProducto editProducto;
	private Integer idParmaTipoProducto;

	public BeanParmaProducto() {

	}

	@PostConstruct
	public void inicializacion() {		
		newProducto=new ParmaProducto();
		editProducto=new ParmaProducto();
	}
	public String actionCargarMenuProductos() {
		listaProductos= mInventario.findAllParmaProducto();
		listaTipoProductos=mInventario.findAllParmaTipoProducto();
		return "productos?faces-redirect=true";
	}
	public void actionInsertarProducto() throws Exception {	
		try {
		mInventario.insertarParmaProducto(newProducto, idParmaTipoProducto);
		listaProductos= mInventario.findAllParmaProducto();
		newProducto=new ParmaProducto();		
		JSFUtil.crearMensajeINFO("Producto Creado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}

	public void actionActualizarProductos() throws Exception {	
		try {
		mInventario.actualizarParmaProducto(editProducto, idParmaTipoProducto);
		listaProductos= mInventario.findAllParmaProducto();
		editProducto=new ParmaProducto();		
		JSFUtil.crearMensajeINFO("Actualizar Productos");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}
	public void actionlisternerEliminarProducto(Integer id) {
		try {
			mInventario.eliminarParmaProducto(id);
			listaProductos= mInventario.findAllParmaProducto();
			JSFUtil.crearMensajeINFO("Producto Eliminado");			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	public void seteditProducto(ParmaProducto producto) {
		this.editProducto=producto;		
	}

	public List<ParmaProducto> getListaProductos() {
		return listaProductos;
	}

	public ParmaProducto getNewProducto() {
		return newProducto;
	}

	public ParmaProducto getEditProducto() {
		return editProducto;
	}

	public Integer getIdParmaTipoProducto() {
		return idParmaTipoProducto;
	}

	public void setListaProductos(List<ParmaProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public void setNewProducto(ParmaProducto newProducto) {
		this.newProducto = newProducto;
	}

	public void setEditProducto(ParmaProducto editProducto) {
		this.editProducto = editProducto;
	}

	public void setIdParmaTipoProducto(Integer idParmaTipoProducto) {
		this.idParmaTipoProducto = idParmaTipoProducto;
	}

	public List<ParmaTipoProducto> getListaTipoProductos() {
		return listaTipoProductos;
	}

	public void setListaTipoProductos(List<ParmaTipoProducto> listaTipoProductos) {
		this.listaTipoProductos = listaTipoProductos;
	}
	
	
}
