package erpparma.controller.facturacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import erpparma.controller.JSFUtil;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.dtos.DTOFactura;
import erpparma.model.facturacion.managers.ManagerFacturacion;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BeanFacturacion implements Serializable {

	@EJB
	private ManagerFacturacion mf;

	private List<DTOClientes> clientes;

	private DTOClientes clienteSelected;

	List<ParmaFacturacionDetalle> items;

	private Integer facturaPos;

	private SegUsuario segUsuario;

	public BeanFacturacion() {
		this.facturaPos = 0;
	}

	@PostConstruct
	public void inicializar() {
		this.clientes = this.mf.findAllClientes();
	}

	public void openNewCliente() {
		this.segUsuario = new SegUsuario();
	}

	public void crearCliente() {
		try {
			this.mf.crearCliente(this.segUsuario);
			JSFUtil.crearMensajeINFO("Cliente creado correctamente");
			this.clientes = this.mf.findAllClientes();
			PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("Error: " + e.getMessage());
		}
	}

	public void loadDetallesFactura() {
		this.items = this.clienteSelected.getFacturas().get(this.facturaPos).getItems();
	}

	public void loadItems(DTOFactura factura, Integer index) {

		this.mf.findDetallesFactura(factura);
		this.facturaPos = index;
		this.loadDetallesFactura();

	}

	public List<DTOClientes> getClientes() {
		return clientes;
	}

	public String actionChangeCliente(DTOClientes cliente, String value) {
		this.clienteSelected = cliente;
		if (value.equals("detalle")) {
			this.mf.findFacturas(this.clienteSelected);
		}

		return value + "?faces-redirect=true";
	}

	public void setClientes(List<DTOClientes> clientes) {
		this.clientes = clientes;
	}

	public DTOClientes getClienteSelected() {
		return clienteSelected;
	}

	public void setClienteSelected(DTOClientes clienteSelected) {
		this.clienteSelected = clienteSelected;
	}

	public Integer getFacturaPos() {
		return facturaPos;
	}

	public void setFacturaPos(Integer facturaPos) {
		this.facturaPos = facturaPos;
	}

	public SegUsuario getSegUsuario() {
		return segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

	public List<ParmaFacturacionDetalle> getItems() {
		return items;
	}

	public void setItems(List<ParmaFacturacionDetalle> items) {
		this.items = items;
	}

	private static final long serialVersionUID = 1L;
}
