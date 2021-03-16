package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the parma_pedidos database table.
 * 
 */
@Entity
@Table(name="parma_pedidos")
@NamedQuery(name="ParmaPedido.findAll", query="SELECT p FROM ParmaPedido p")
public class ParmaPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido", unique=true, nullable=false)
	private Integer idPedido;

	@Column(name="estado_pedido", nullable=false)
	private Boolean estadoPedido;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido", nullable=false)
	private Date fechaPedido;

	//bi-directional many-to-one association to ParmaDetallePedido
	@OneToMany(mappedBy="parmaPedido")
	private List<ParmaDetallePedido> parmaDetallePedidos;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario")
	private SegUsuario segUsuario;

	public ParmaPedido() {
	}

	public Integer getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public Boolean getEstadoPedido() {
		return this.estadoPedido;
	}

	public void setEstadoPedido(Boolean estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public Date getFechaPedido() {
		return this.fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public List<ParmaDetallePedido> getParmaDetallePedidos() {
		return this.parmaDetallePedidos;
	}

	public void setParmaDetallePedidos(List<ParmaDetallePedido> parmaDetallePedidos) {
		this.parmaDetallePedidos = parmaDetallePedidos;
	}

	public ParmaDetallePedido addParmaDetallePedido(ParmaDetallePedido parmaDetallePedido) {
		getParmaDetallePedidos().add(parmaDetallePedido);
		parmaDetallePedido.setParmaPedido(this);

		return parmaDetallePedido;
	}

	public ParmaDetallePedido removeParmaDetallePedido(ParmaDetallePedido parmaDetallePedido) {
		getParmaDetallePedidos().remove(parmaDetallePedido);
		parmaDetallePedido.setParmaPedido(null);

		return parmaDetallePedido;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}