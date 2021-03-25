package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the parma_producto database table.
 * 
 */
@Entity
@Table(name="parma_producto")
@NamedQuery(name="ParmaProducto.findAll", query="SELECT p FROM ParmaProducto p")
public class ParmaProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parma_producto", unique=true, nullable=false)
	private Integer idParmaProducto;

	@Column(name="iva_producto", nullable=false)
	private Boolean ivaProducto;

	@Column(name="nombre_producto", nullable=false, length=30)
	private String nombreProducto;

	@Column(name="precio_producto", nullable=false, precision=7, scale=2)
	private BigDecimal precioProducto;
	
	

	//bi-directional many-to-one association to ParmaAjuste
	@OneToMany(mappedBy="parmaProducto")
	private List<ParmaAjuste> parmaAjustes;

	//bi-directional many-to-one association to ParmaDetallePedido
	@OneToMany(mappedBy="parmaProducto")
	private List<ParmaDetallePedido> parmaDetallePedidos;

	//bi-directional many-to-one association to ParmaFacturacionDetalle
	@OneToMany(mappedBy="parmaProducto")
	private List<ParmaFacturacionDetalle> parmaFacturacionDetalles;

	//bi-directional many-to-one association to ParmaInventario
	@OneToMany(mappedBy="parmaProducto", fetch = FetchType.EAGER)
	private List<ParmaInventario> parmaInventarios;

	//bi-directional many-to-one association to ParmaTipoProducto
	@ManyToOne
	@JoinColumn(name="id_parma_tipo_producto")
	private ParmaTipoProducto parmaTipoProducto;

	public ParmaProducto() {
	}
	

	



	public Integer getIdParmaProducto() {
		return this.idParmaProducto;
	}

	public void setIdParmaProducto(Integer idParmaProducto) {
		this.idParmaProducto = idParmaProducto;
	}

	public Boolean getIvaProducto() {
		return this.ivaProducto;
	}

	public void setIvaProducto(Boolean ivaProducto) {
		this.ivaProducto = ivaProducto;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioProducto() {
		return this.precioProducto;
	}

	public void setPrecioProducto(BigDecimal precioProducto) {
		this.precioProducto = precioProducto;
	}

	public List<ParmaAjuste> getParmaAjustes() {
		return this.parmaAjustes;
	}

	public void setParmaAjustes(List<ParmaAjuste> parmaAjustes) {
		this.parmaAjustes = parmaAjustes;
	}

	public ParmaAjuste addParmaAjuste(ParmaAjuste parmaAjuste) {
		getParmaAjustes().add(parmaAjuste);
		parmaAjuste.setParmaProducto(this);

		return parmaAjuste;
	}

	public ParmaAjuste removeParmaAjuste(ParmaAjuste parmaAjuste) {
		getParmaAjustes().remove(parmaAjuste);
		parmaAjuste.setParmaProducto(null);

		return parmaAjuste;
	}

	public List<ParmaDetallePedido> getParmaDetallePedidos() {
		return this.parmaDetallePedidos;
	}

	public void setParmaDetallePedidos(List<ParmaDetallePedido> parmaDetallePedidos) {
		this.parmaDetallePedidos = parmaDetallePedidos;
	}

	public ParmaDetallePedido addParmaDetallePedido(ParmaDetallePedido parmaDetallePedido) {
		getParmaDetallePedidos().add(parmaDetallePedido);
		parmaDetallePedido.setParmaProducto(this);

		return parmaDetallePedido;
	}

	public ParmaDetallePedido removeParmaDetallePedido(ParmaDetallePedido parmaDetallePedido) {
		getParmaDetallePedidos().remove(parmaDetallePedido);
		parmaDetallePedido.setParmaProducto(null);

		return parmaDetallePedido;
	}

	public List<ParmaFacturacionDetalle> getParmaFacturacionDetalles() {
		return this.parmaFacturacionDetalles;
	}

	public void setParmaFacturacionDetalles(List<ParmaFacturacionDetalle> parmaFacturacionDetalles) {
		this.parmaFacturacionDetalles = parmaFacturacionDetalles;
	}

	public ParmaFacturacionDetalle addParmaFacturacionDetalle(ParmaFacturacionDetalle parmaFacturacionDetalle) {
		getParmaFacturacionDetalles().add(parmaFacturacionDetalle);
		parmaFacturacionDetalle.setParmaProducto(this);

		return parmaFacturacionDetalle;
	}

	public ParmaFacturacionDetalle removeParmaFacturacionDetalle(ParmaFacturacionDetalle parmaFacturacionDetalle) {
		getParmaFacturacionDetalles().remove(parmaFacturacionDetalle);
		parmaFacturacionDetalle.setParmaProducto(null);

		return parmaFacturacionDetalle;
	}

	public List<ParmaInventario> getParmaInventarios() {
		return this.parmaInventarios;
	}

	public void setParmaInventarios(List<ParmaInventario> parmaInventarios) {
		this.parmaInventarios = parmaInventarios;
	}

	public ParmaInventario addParmaInventario(ParmaInventario parmaInventario) {
		getParmaInventarios().add(parmaInventario);
		parmaInventario.setParmaProducto(this);

		return parmaInventario;
	}

	public ParmaInventario removeParmaInventario(ParmaInventario parmaInventario) {
		getParmaInventarios().remove(parmaInventario);
		parmaInventario.setParmaProducto(null);

		return parmaInventario;
	}

	public ParmaTipoProducto getParmaTipoProducto() {
		return this.parmaTipoProducto;
	}

	public void setParmaTipoProducto(ParmaTipoProducto parmaTipoProducto) {
		this.parmaTipoProducto = parmaTipoProducto;
	}

}