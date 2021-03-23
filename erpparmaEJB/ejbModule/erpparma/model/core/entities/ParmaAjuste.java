package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the parma_ajuste database table.
 * 
 */
@Entity
@Table(name="parma_ajuste")
@NamedQuery(name="ParmaAjuste.findAll", query="SELECT p FROM ParmaAjuste p")
public class ParmaAjuste implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parma_ajuste", unique=true, nullable=false)
	private Integer idParmaAjuste;

	@Column(name="cantidad_ajuste", nullable=false, precision=7, scale=2)
	private BigDecimal cantidadAjuste;

	@Column(length=2147483647)
	private String descripcion;

	@Column(name="fecha_ajuste", nullable=false)
	private Timestamp fechaAjuste;

	@Column(name="tipo_ajuste", nullable=false)
	private Boolean tipoAjuste;

	//bi-directional many-to-one association to ParmaProducto
	@ManyToOne
	@JoinColumn(name="id_parma_producto")
	private ParmaProducto parmaProducto;

	public ParmaAjuste() {
	}

	public Integer getIdParmaAjuste() {
		return this.idParmaAjuste;
	}

	public void setIdParmaAjuste(Integer idParmaAjuste) {
		this.idParmaAjuste = idParmaAjuste;
	}

	public BigDecimal getCantidadAjuste() {
		return this.cantidadAjuste;
	}

	public void setCantidadAjuste(BigDecimal cantidadAjuste) {
		this.cantidadAjuste = cantidadAjuste;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaAjuste() {
		return this.fechaAjuste;
	}

	public void setFechaAjuste(Timestamp fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
	}

	public Boolean getTipoAjuste() {
		return this.tipoAjuste;
	}

	public void setTipoAjuste(Boolean tipoAjuste) {
		this.tipoAjuste = tipoAjuste;
	}

	public ParmaProducto getParmaProducto() {
		return this.parmaProducto;
	}

	public void setParmaProducto(ParmaProducto parmaProducto) {
		this.parmaProducto = parmaProducto;
	}

}