package erpparma.controller.facturacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.facturacion.managers.ManagerFacturacion;

@Named
@SessionScoped
public class BeanFacturar implements Serializable {

	@EJB
	private ManagerFacturacion mf;

	private List<ParmaProducto> productos;

	private String txtLike;

	private List<ParmaFacturacionDetalle> items;

	private ParmaFactura factura;

	private Integer cantidad;

	public BeanFacturar() {

	}

	public void cargarProductos() {
		this.productos = this.mf.findAllProductosVenta();
	}

	public void actionAgregarCarro() {
		if (this.items == null) {
			this.items = new ArrayList<ParmaFacturacionDetalle>();
			this.factura = new ParmaFactura();
			this.factura.setSubtotal(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
		}

		try {
			ParmaProducto p = this.mf.findProductoVentaByName(this.txtLike);
			ParmaFacturacionDetalle det = new ParmaFacturacionDetalle();
			det.setParmaProducto(p);
			det.setCantidad(1);
			det.setSubtotal(p.getPrecioProducto());
			this.items.add(det);
			calcularSubTotalGlobal();
			System.out.println("Subtotal: " + this.factura.getSubtotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calcularSubTotalGlobal() {
		this.factura.setSubtotal(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
		for (ParmaFacturacionDetalle item : items) {
			Double sp = this.factura.getSubtotal().doubleValue() + item.getSubtotal().doubleValue();
			BigDecimal d = new BigDecimal(sp).setScale(2, RoundingMode.HALF_UP);
			this.factura.setSubtotal(d);
		}
	}

	public void updateCantidad(ValueChangeEvent event) {
		cantidad = (Integer) event.getNewValue();

	}

	public void refreshCantidad(Integer pos) {
		ParmaFacturacionDetalle p = this.items.get(pos);
		Double s = p.getParmaProducto().getPrecioProducto().doubleValue() * this.cantidad;
		BigDecimal d = new BigDecimal(s).setScale(2, RoundingMode.HALF_UP);
		p.setSubtotal(d);
		calcularSubTotalGlobal();
	}

	public void deleteProducto(Integer pos) {
		this.items.remove(pos.intValue());
		calcularSubTotalGlobal();
	}

	public List<ParmaProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<ParmaProducto> productos) {
		this.productos = productos;
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

	private static final long serialVersionUID = 1L;
}
