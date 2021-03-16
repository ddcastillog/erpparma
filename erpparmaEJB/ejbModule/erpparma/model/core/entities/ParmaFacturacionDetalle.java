package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parma_facturacion_detalle database table.
 * 
 */
@Entity
@Table(name="parma_facturacion_detalle")
@NamedQuery(name="ParmaFacturacionDetalle.findAll", query="SELECT p FROM ParmaFacturacionDetalle p")
public class ParmaFacturacionDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_factura", unique=true, nullable=false)
	private Integer idDetalleFactura;

	@Column(nullable=false)
	private Integer cantidad;

	//bi-directional many-to-one association to ParmaFactura
	@ManyToOne
	@JoinColumn(name="id_factura")
	private ParmaFactura parmaFactura;

	//bi-directional many-to-one association to ParmaProducto
	@ManyToOne
	@JoinColumn(name="id_parma_producto")
	private ParmaProducto parmaProducto;

	public ParmaFacturacionDetalle() {
	}

	public Integer getIdDetalleFactura() {
		return this.idDetalleFactura;
	}

	public void setIdDetalleFactura(Integer idDetalleFactura) {
		this.idDetalleFactura = idDetalleFactura;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public ParmaFactura getParmaFactura() {
		return this.parmaFactura;
	}

	public void setParmaFactura(ParmaFactura parmaFactura) {
		this.parmaFactura = parmaFactura;
	}

	public ParmaProducto getParmaProducto() {
		return this.parmaProducto;
	}

	public void setParmaProducto(ParmaProducto parmaProducto) {
		this.parmaProducto = parmaProducto;
	}

}