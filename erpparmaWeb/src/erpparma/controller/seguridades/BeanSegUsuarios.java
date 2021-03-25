package erpparma.controller.seguridades;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.seguridades.managers.ManagerSeguridades;
import erpparma.controller.seguridades.BeanSegLogin;

@Named
@SessionScoped
public class BeanSegUsuarios implements Serializable {
	@EJB
	private ManagerSeguridades managerSeguridades;
	
	private List<SegUsuario> listaUsuarios;
	private SegUsuario nuevoUsuario;
	private SegUsuario edicionUsuario;
	@Inject
	private BeanSegLogin beanSegLogin;
	
	public BeanSegUsuarios() {
		
	}
	
	public String actionMenuUsuarios() {
		listaUsuarios=managerSeguridades.findAllUsuarios();
		return "usuarios";
	}
	
	public String actionNuevoUsuarios() {
		
		return "nuevocliente";
	}
	
	public void actionListenerActivarDesactivarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.activarDesactivarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionMenuNuevoUsuario() {
		nuevoUsuario=new SegUsuario();
		nuevoUsuario.setActivo(true);
		return "usuarios_nuevo";
	}
	
	public String actionMenuNuevoUsuario1() {
		nuevoUsuario=new SegUsuario();
		nuevoUsuario.setActivo(true);
		return "nuevo_cliente";
	}
	
	

	public void actionListenerInsertarNuevoUsuarioCliente() {
		try {
			managerSeguridades.insertarUsuarioNuevo(nuevoUsuario);
			//listaUsuarios=managerSeguridades.findAllUsuarios();
			nuevoUsuario=new SegUsuario();
			nuevoUsuario.setActivo(true);
			JSFUtil.crearMensajeINFO("Usuario insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void actionListenerInsertarNuevoUsuario() {
		try {
			managerSeguridades.insertarUsuario(nuevoUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			nuevoUsuario=new SegUsuario();
			nuevoUsuario.setActivo(true);
			JSFUtil.crearMensajeINFO("Usuario insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionSeleccionarEdicionUsuario(SegUsuario usuario) {
		edicionUsuario=usuario;
		return "usuarios_edicion";
	}
	
	public void actionListenerActualizarEdicionUsuario() {
		try {
			managerSeguridades.actualizarUsuario(beanSegLogin.getLoginDTO(),edicionUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.eliminarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public SegUsuario getNuevoUsuario() {
		return nuevoUsuario;
	}

	public void setNuevoUsuario(SegUsuario nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public SegUsuario getEdicionUsuario() {
		return edicionUsuario;
	}

	public void setEdicionUsuario(SegUsuario edicionUsuario) {
		this.edicionUsuario = edicionUsuario;
	}
	
	

}
