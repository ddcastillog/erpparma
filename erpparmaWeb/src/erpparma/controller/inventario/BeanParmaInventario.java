package erpparma.controller.inventario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.auditoria.managers.ManagerAuditoria;
import erpparma.model.core.entities.AudBitacora;
import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
import erpparma.model.core.utils.ModelUtil;
import erpparma.model.inventario.managers.ManagerInventario;

@Named
@SessionScoped
public class BeanParmaInventario implements Serializable {
	@EJB
	private ManagerInventario mInventario;	
	private List<ParmaInventario> listaInventarios;
	

	public BeanParmaInventario() {

	}

	@PostConstruct
	public void inicializacion() {
		
	}
	public void activoDesactivoProducto(int  idInventario) {
		try {
			mInventario.activodesactivoProducto(idInventario);
			listaInventarios=mInventario.findAllInventario();
			JSFUtil.crearMensajeINFO("Prodcuto actualizado");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String actionCargarMenuInventario() {
		listaInventarios=mInventario.findAllInventario();	
		return "inventario?faces-redirect=true";
	}

	public List<ParmaInventario> getListaInventarios() {
		return listaInventarios;
	}

	public void setListaInventarios(List<ParmaInventario> listaInventarios) {
		this.listaInventarios = listaInventarios;
	}

	


}
