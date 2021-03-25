package erpparma.controller.facturacion;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import erpparma.controller.JSFUtil;
import erpparma.controller.seguridades.BeanSegLogin;
import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.managers.ManagerFacturacion;
import erpparma.model.inventario.managers.ManagerInventario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Named
@SessionScoped
public class BeanFacturar implements Serializable {

	@EJB
	private ManagerFacturacion mf;

	@EJB
	private ManagerInventario mInventario;

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

	private Double payValue;

	private String changeValue;

	public BeanFacturar() {

	}

	public void init() {
		this.items = new ArrayList<ParmaFacturacionDetalle>();
		this.factura = new ParmaFactura();
		this.mf.initFacturaValues(factura);
		this.payValue = 0.00;
		this.changeValue = "$ 0.00";
		System.out.println("**** TOP CLIENTES *****");
		this.mf.getTopClientes();
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
	
			Boolean stock = this.mInventario.ajusteFactura(this.items, this.login.getLoginDTO());
			System.out.println(stock);
			if (!stock) {
				JSFUtil.crearMensajeERROR("Algunos productos no tienen stock disponible");
				return "";
			}
			
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
				this.payValue = 0.00;
				this.changeValue = "$ 0.00";
				JSFUtil.crearMensajeINFO("Producto agregado");
			} else {
				JSFUtil.crearMensajeWARN("El producto ya este agreado!");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error: !" + e.getMessage());
		}
	}

	public void calculateChange() {
		Double total = this.factura.getTotal().doubleValue();
		if (total > this.payValue) {
			JSFUtil.crearMensajeWARN("Error, el pago es menor que el total a pagar!");
			this.payValue = 0.00;
			this.changeValue = "$ 0.00";
			return;
		}
		this.changeValue = "$ " + this.mf.calculateChange(total, this.payValue).toString();
	}

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
		this.payValue = 0.00;
		this.changeValue = "$ 0.00";
	}

	public void deleteProducto(Integer pos) {
		this.items.remove(pos.intValue());
		this.mf.calculateFacturaValues(factura);
		this.payValue = 0.00;
		this.changeValue = "$ 0.00";
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
		List<ParmaProducto> products = this.mInventario.stockVenta();
		for (ParmaProducto producto : products) {
			productList.add(producto.getNombreProducto());
		}
		return productList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public void actionFacturaPDF() {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("factura", 27);
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("facturacion/facturaparma.jasper");
		System.out.println("RUTA: " + ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=factura.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://143.255.250.17:5432/ddcastillog", "ddcastillog",
					"admin1004291447");
			JasperReport jr = null;
			jr = (JasperReport) JRLoader.loadObjectFromFile(
					"/home/kevincatu/git/erpparma/erpparmaWeb/WebContent/facturacion/facturaparma.jasper");
			JasperPrint impresion = JasperFillManager.fillReport(jr, parameterMap, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	
	}

	
	public void exportarPDF() {
		Map<String,Object> parametros= new HashMap<String,Object>();
		parametros.put("factura", 35);
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://143.255.250.17:5432/ddcastillog", "ddcastillog",
					"admin1004291447");
			File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("factura.jasper"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, connection);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (ClassNotFoundException | SQLException | JRException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public Double getPayValue() {
		return payValue;
	}

	public void setPayValue(Double payValue) {
		this.payValue = payValue;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}

	private static final long serialVersionUID = 1L;
}
