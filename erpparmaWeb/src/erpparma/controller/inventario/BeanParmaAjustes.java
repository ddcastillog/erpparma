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
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
import erpparma.model.core.utils.ModelUtil;
import erpparma.model.inventario.managers.ManagerInventario;

@Named
@SessionScoped
public class BeanParmaAjustes implements Serializable {
	@EJB
	private ManagerInventario mInventario;
	private List<ParmaAjuste> listaAjustes;
	private List<ParmaProducto> listaProductos;
	private ParmaAjuste newtAjuste;
	private String nombreProducto;

	public BeanParmaAjustes() {

	}

	@PostConstruct
	public void inicializacion() {
		newtAjuste = new ParmaAjuste();
	}

	public String actionCargarMenuAjustes() {
		listaAjustes = mInventario.findAllParmaAjuste();		
		return "ajustes?faces-redirect=true";
	}

	public void actionInsertarAjustes() throws Exception {
		try {				
			newtAjuste.setParmaProducto(mInventario.findProductoByName(nombreProducto));
			mInventario.insertarParmaAjuste(newtAjuste);
			listaAjustes = mInventario.findAllParmaAjuste();
			newtAjuste = new ParmaAjuste();
			JSFUtil.crearMensajeINFO("Ajuste Creado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<String> completeText(String query) {
		String queryLowerCase = query.toLowerCase();
		List<String> productList = new ArrayList<>();
		listaProductos = mInventario.findAllParmaProducto();
		for (ParmaProducto producto : listaProductos) {
			productList.add(producto.getNombreProducto());
		}
		return productList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public List<ParmaAjuste> getListaAjustes() {
		return listaAjustes;
	}

	public ParmaAjuste getNewtAjuste() {
		return newtAjuste;
	}

	public void setListaAjustes(List<ParmaAjuste> listaAjustes) {
		this.listaAjustes = listaAjustes;
	}

	public void setNewtAjuste(ParmaAjuste newtAjuste) {
		this.newtAjuste = newtAjuste;
	}

	public List<ParmaProducto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<ParmaProducto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


}
