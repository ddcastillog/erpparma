package erpparma.controller.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import erpparma.controller.JSFUtil;
import erpparma.controller.seguridades.BeanSegLogin;
import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.managers.ManagerFacturacion;

@Named
@SessionScoped
public class BeanFacturar implements Serializable {

	@EJB
	private ManagerFacturacion mf;

	private List<ParmaProducto> productos;

	private String txtLike;

	private List<ParmaFacturacionDetalle> items;

	@Inject
	private BeanFacturacion beanFacturacion;

	@Inject
	private BeanSegLogin login;

	private ParmaFactura factura;

	private Integer cantidad;

	private String codeOrEmail;

	public BeanFacturar() {

	}

	public void init() {
		this.items = new ArrayList<ParmaFacturacionDetalle>();
		this.factura = new ParmaFactura();
		this.mf.initFacturaValues(factura);
	}

	@PostConstruct
	public void inicializar() {
		this.init();
	}

	public String actionCrearFactura() {

		String ruta = "";

		if (this.beanFacturacion.getClienteSelected() == null) {
			JSFUtil.crearMensajeERROR("No se ha seleccionado un usuario");
			return "";
		}

		if (this.items.size() == 0) {
			JSFUtil.crearMensajeERROR("No se ha seleccionado productos!");
			return "";
		}

		try {
			this.mf.crearFactura(this.factura, this.beanFacturacion.getClienteSelected(), this.login.getIdSegUsuario());
			System.out.println("Creado correctamente");
			this.mf.findFacturas(this.beanFacturacion.getClienteSelected());
			this.init();
			JSFUtil.crearMensajeINFO("Factura creada");
			ruta = "detalle?faces-redirect=true";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruta;
	}

	public void actionAgregarCarro() {
		try {
			ParmaProducto p = this.mf.findProductoVentaByName(this.txtLike);
			if (p == null) {
				JSFUtil.crearMensajeERROR("Seleccione un producto válido");
				return;
			}

			if (!isProduct(p)) {
				this.items.add(this.mf.initItemFacturaValuesNew(p));
				this.factura.setParmaFacturacionDetalles(this.items);
				this.mf.calculateFacturaValues(factura);
				JSFUtil.crearMensajeINFO("Producto agregado");
			} else {
				JSFUtil.crearMensajeWARN("El producto ya este agreado!");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error: !" + e.getMessage());
		}
	}

//	public void calcularSubTotalGlobal() {
//		this.factura.setSubtotal(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//		this.factura.setDescuento(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//		this.factura.setIva(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//		this.factura.setTotal(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//		for (ParmaFacturacionDetalle item : items) {
//			Double sp = this.factura.getSubtotal().doubleValue() + item.getSubtotal().doubleValue();
//			BigDecimal d = new BigDecimal(sp).setScale(2, RoundingMode.HALF_UP);
//			this.factura.setSubtotal(d);
//			this.factura.setTotal(d);
//		}
//
//		Double subtotal = this.getFactura().getSubtotal().doubleValue();
//		if (subtotal >= 10) {
//			Double desc = subtotal * 0.10;
//			Double iva = (subtotal - desc) * 0.14;
//			this.factura.setDescuento(new BigDecimal(desc).setScale(2, RoundingMode.HALF_UP));
//			this.factura.setIva(new BigDecimal(iva).setScale(2, RoundingMode.HALF_UP));
//			BigDecimal df = new BigDecimal(subtotal - desc + iva).setScale(2, RoundingMode.HALF_UP);
//			this.factura.setTotal(df);
//		} else {
//			this.factura.setIva(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//			this.factura.setDescuento(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
//		}
//
//	}

	public void changeCliente() {
		DTOClientes c = this.mf.findClienteByIdOrCorreo(this.codeOrEmail.toString());
		if (c != null) {
			this.beanFacturacion.setClienteSelected(c);
			JSFUtil.crearMensajeINFO("Cliente encontrado!!");
			return;
		}

		JSFUtil.crearMensajeERROR("No existe ese cliente");
	}

	public void updateCantidad(ValueChangeEvent event) {
		cantidad = (Integer) event.getNewValue();
	}

	public void refreshCantidad(Integer pos) {
		this.mf.updteSubtotalItemFactura(this.items.get(pos), this.cantidad);
		this.mf.calculateFacturaValues(factura);
	}

	public void deleteProducto(Integer pos) {
		this.items.remove(pos.intValue());
		this.mf.calculateFacturaValues(factura);
	}

	public List<ParmaProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<ParmaProducto> productos) {
		this.productos = productos;
	}

	public boolean isProduct(ParmaProducto p) {
		for (ParmaFacturacionDetalle item : items) {
			if (item.getParmaProducto().getNombreProducto().equals(p.getNombreProducto())) {
				return true;
			}
		}
		return false;
	}

	public List<String> completeText(String query) {
		String queryLowerCase = query.toLowerCase();
		List<String> productList = new ArrayList<>();
		List<ParmaProducto> products = this.mf.findAllProductosVenta();
		for (ParmaProducto producto : products) {
			productList.add(producto.getNombreProducto());
		}
		return productList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public String getTxtLike() {
		return txtLike;
	}

	public void setTxtLike(String txtLike) {
		this.txtLike = txtLike;
	}

	public List<ParmaFacturacionDetalle> getItems() {
		return items;
	}

	public void setItems(List<ParmaFacturacionDetalle> items) {
		this.items = items;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public ParmaFactura getFactura() {
		return factura;
	}

	public void setFactura(ParmaFactura factura) {
		this.factura = factura;
	}

	public String getCodeOrEmail() {
		return codeOrEmail;
	}

	public void setCodeOrEmail(String codeOrEmail) {
		this.codeOrEmail = codeOrEmail;
	}

	public BeanFacturacion getBeanFacturacion() {
		return beanFacturacion;
	}

	public void setBeanFacturacion(BeanFacturacion beanFacturacion) {
		this.beanFacturacion = beanFacturacion;
	}

	public BeanSegLogin getLogin() {
		return login;
	}

	public void setLogin(BeanSegLogin login) {
		this.login = login;
	}

	private static final long serialVersionUID = 1L;
}
