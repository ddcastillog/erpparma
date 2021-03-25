package erpparma.controller.seguridades;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.core.entities.SegAsignacion;
import erpparma.model.core.entities.SegModulo;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanSegAsignaciones implements Serializable {
	@EJB
	private ManagerSeguridades managerSeguridades;
	
	private List<SegUsuario> listaUsuarios;
	private List<SegModulo> listaModulos;
	private List<SegModulo> listaModulos1;
	private int idSegUsuarioSeleccionado;
	private List<SegAsignacion> listaAsignaciones;
	
	
	public BeanSegAsignaciones() {
		
	}
	
	public String actionMenuAsignaciones() {
		listaUsuarios=managerSeguridades.findAllUsuarios();
		listaModulos=managerSeguridades.findAllModulos();
	
		return "asignaciones";
	}
	
	public String actionMenuAsignacionesClientes() {
		listaUsuarios=managerSeguridades.findAllUsuarios();
		listaModulos=managerSeguridades.findAllModulos1();
		return "asignaciones";
	}
	
	
	
	
	public void setListaModulos1(List<SegModulo> listaModulos1) {
		this.listaModulos1 = listaModulos1;
	}

	public void actionListenerSeleccionarUsuario() {
		listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
	}
	
	public void actionListenerAsignarModulo(int idSegModulo) {
		try {
			managerSeguridades.asignarModulo(idSegUsuarioSeleccionado, idSegModulo);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Módulo asignado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void actionListenerAsignarModuloCliente(int idSegModulo) {
		try {
			managerSeguridades.asignarModulo(idSegUsuarioSeleccionado, idSegModulo);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Módulo asignado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	public void actionListenerAsignarModuloClienteNuevo(int idSegModulo) {
		try {
			managerSeguridades.asignarModuloNuevoCliente(idSegUsuarioSeleccionado, idSegModulo);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Módulo asignado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void actionListenerEliminarAsignacionModulo(int idSegAsignacion) {
		try {
			managerSeguridades.eliminarAsignacion(idSegAsignacion);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Asignación eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarAsignacionModuloClientes(int idSegAsignacion) {
		try {
			managerSeguridades.eliminarAsignacion(idSegAsignacion);
			listaAsignaciones=managerSeguridades.findAsignacionByUsuario(idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Asignación eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("NO tiene acceso de eliminar esa asignacion");
			e.printStackTrace();
		}
	}
	

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<SegModulo> getListaModulos() {
		return listaModulos;
	}
	
	public List<SegModulo> getListaModulos1() {
		
		SegModulo asig = new SegModulo();
		
		if (asig.getNombreModulo()=="Cliente") 
			
			return listaModulos;
		
		return listaModulos;
	}


	public void setListaModulos(List<SegModulo> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}

	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
	}

	public List<SegAsignacion> getListaAsignaciones() {
		return listaAsignaciones;
	}

	public void setListaAsignaciones(List<SegAsignacion> listaAsignaciones) {
		this.listaAsignaciones = listaAsignaciones;
	}
	
	

}
