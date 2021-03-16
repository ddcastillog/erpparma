package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parma_inventario database table.
 * 
 */
@Entity
@Table(name="parma_inventario")
@NamedQuery(name="ParmaInventario.findAll", query="SELECT p FROM ParmaInventario p")
public class ParmaInventario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_inventario", unique=true, nullable=false)
	private Integer idInventario;

	@Column(nullable=false)
	private Integer cantidad;

	//bi-directional many-to-one association to ParmaProducto
	@ManyToOne
	@JoinColumn(name="id_parma_producto")
	private ParmaProducto parmaProducto;

	public ParmaInventario() {
	}

	public Integer getIdInventario() {
		return this.idInventario;
	}

	public void setIdInventario(Integer idInventario) {
		this.idInventario = idInventario;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public ParmaProducto getParmaProducto() {
		return this.parmaProducto;
	}

	public void setParmaProducto(ParmaProducto parmaProducto) {
		this.parmaProducto = parmaProducto;
	}

}