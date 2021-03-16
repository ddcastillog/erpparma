package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parma_detalle_pedido database table.
 * 
 */
@Entity
@Table(name="parma_detalle_pedido")
@NamedQuery(name="ParmaDetallePedido.findAll", query="SELECT p FROM ParmaDetallePedido p")
public class ParmaDetallePedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_pedido", unique=true, nullable=false)
	private Integer idDetallePedido;

	@Column(nullable=false)
	private Integer cantidad;

	//bi-directional many-to-one association to ParmaPedido
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private ParmaPedido parmaPedido;

	//bi-directional many-to-one association to ParmaProducto
	@ManyToOne
	@JoinColumn(name="id_parma_producto")
	private ParmaProducto parmaProducto;

	public ParmaDetallePedido() {
	}

	public Integer getIdDetallePedido() {
		return this.idDetallePedido;
	}

	public void setIdDetallePedido(Integer idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public ParmaPedido getParmaPedido() {
		return this.parmaPedido;
	}

	public void setParmaPedido(ParmaPedido parmaPedido) {
		this.parmaPedido = parmaPedido;
	}

	public ParmaProducto getParmaProducto() {
		return this.parmaProducto;
	}

	public void setParmaProducto(ParmaProducto parmaProducto) {
		this.parmaProducto = parmaProducto;
	}

}