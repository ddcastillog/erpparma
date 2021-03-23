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
public class BeanParmaTipoProducto implements Serializable {
	@EJB
	private ManagerInventario mInventario;
	private List<ParmaTipoProducto> listaTipoProductos;
	private ParmaTipoProducto newtTipoProducto;
	private ParmaTipoProducto editTipoProducto;

	public BeanParmaTipoProducto() {

	}

	@PostConstruct
	public void inicializacion() {		
		newtTipoProducto=new ParmaTipoProducto();
		editTipoProducto=new ParmaTipoProducto();
	}
	public String actionCargarMenuTipoProducto() {
		listaTipoProductos=mInventario.findAllParmaTipoProducto();
		return "tipoProductos?faces-redirect=true";
	}
	public void actionInsertarTipoProducto() throws Exception {	
		try {
		mInventario.insertarParmaTipoProducto(newtTipoProducto);
		listaTipoProductos=mInventario.findAllParmaTipoProducto();
		newtTipoProducto=new ParmaTipoProducto();	
		JSFUtil.crearMensajeINFO("Tipo Producto Creado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}

	public void actionActualizarTipoProductos() throws Exception {	
		try {
		mInventario.actualizarParmaTipoProducto(editTipoProducto);
		listaTipoProductos=mInventario.findAllParmaTipoProducto();
		editTipoProducto=new ParmaTipoProducto();		
		JSFUtil.crearMensajeINFO("Actualizar Tipo Productos");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}		
	}
	public void actionlisternerEliminarTipoProducto(Integer id) {
		try {
			mInventario.eliminarParmaTipoProducto(id);
			listaTipoProductos=mInventario.findAllParmaTipoProducto();
			JSFUtil.crearMensajeINFO("Tipo Producto Eliminado");			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	public void seteditTipoProducto(ParmaTipoProducto tipoProducto) {
		this.editTipoProducto=tipoProducto;
	}

	public List<ParmaTipoProducto> getListaTipoProductos() {
		return listaTipoProductos;
	}

	public ParmaTipoProducto getNewtTipoProducto() {
		return newtTipoProducto;
	}

	public ParmaTipoProducto getEditTipoProducto() {
		return editTipoProducto;
	}

	public void setListaTipoProductos(List<ParmaTipoProducto> listaTipoProductos) {
		this.listaTipoProductos = listaTipoProductos;
	}

	public void setNewtTipoProducto(ParmaTipoProducto newtTipoProducto) {
		this.newtTipoProducto = newtTipoProducto;
	}

	public void setEditTipoProducto(ParmaTipoProducto editTipoProducto) {
		this.editTipoProducto = editTipoProducto;
	}
	
	
}
