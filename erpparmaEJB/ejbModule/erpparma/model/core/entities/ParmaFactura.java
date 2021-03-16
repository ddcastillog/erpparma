package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the parma_factura database table.
 * 
 */
@Entity
@Table(name="parma_factura")
@NamedQuery(name="ParmaFactura.findAll", query="SELECT p FROM ParmaFactura p")
public class ParmaFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_factura", unique=true, nullable=false)
	private Integer idFactura;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_factura", nullable=false)
	private Date fechaFactura;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal subtotal;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal total;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario_cliente")
	private SegUsuario segUsuario;

	//bi-directional many-to-one association to ThmEmpleado
	@ManyToOne
	@JoinColumn(name="id_thm_empleado")
	private ThmEmpleado thmEmpleado;

	//bi-directional many-to-one association to ParmaFacturacionDetalle
	@OneToMany(mappedBy="parmaFactura")
	private List<ParmaFacturacionDetalle> parmaFacturacionDetalles;

	public ParmaFactura() {
	}

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Date getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

	public ThmEmpleado getThmEmpleado() {
		return this.thmEmpleado;
	}

	public void setThmEmpleado(ThmEmpleado thmEmpleado) {
		this.thmEmpleado = thmEmpleado;
	}

	public List<ParmaFacturacionDetalle> getParmaFacturacionDetalles() {
		return this.parmaFacturacionDetalles;
	}

	public void setParmaFacturacionDetalles(List<ParmaFacturacionDetalle> parmaFacturacionDetalles) {
		this.parmaFacturacionDetalles = parmaFacturacionDetalles;
	}

	public ParmaFacturacionDetalle addParmaFacturacionDetalle(ParmaFacturacionDetalle parmaFacturacionDetalle) {
		getParmaFacturacionDetalles().add(parmaFacturacionDetalle);
		parmaFacturacionDetalle.setParmaFactura(this);

		return parmaFacturacionDetalle;
	}

	public ParmaFacturacionDetalle removeParmaFacturacionDetalle(ParmaFacturacionDetalle parmaFacturacionDetalle) {
		getParmaFacturacionDetalles().remove(parmaFacturacionDetalle);
		parmaFacturacionDetalle.setParmaFactura(null);

		return parmaFacturacionDetalle;
	}

}