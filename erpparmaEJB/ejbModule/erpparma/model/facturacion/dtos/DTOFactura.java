package erpparma.model.facturacion.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import erpparma.model.core.entities.ParmaFacturacionDetalle;

public class DTOFactura {
	private Integer idFactura;
	private Date fechaFactura;
	private BigDecimal subtotal;
	private BigDecimal total;
	private BigDecimal iva;
	private BigDecimal descuento;
	private List<ParmaFacturacionDetalle> items;

	public DTOFactura() {

	}

	public DTOFactura(Integer idFactura, Date fechaFactura, BigDecimal subtotal, BigDecimal total, BigDecimal iva,
			BigDecimal descuento) {
		this.idFactura = idFactura;
		this.fechaFactura = fechaFactura;
		this.subtotal = subtotal;
		this.total = total;
		this.iva = iva;
		this.descuento = descuento;
		this.items = new ArrayList<ParmaFacturacionDetalle>();
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public List<ParmaFacturacionDetalle> getItems() {
		return items;
	}

	public void setItems(List<ParmaFacturacionDetalle> items) {
		this.items = items;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

}
