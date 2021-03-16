package erpparma.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the parma_tipo_producto database table.
 * 
 */
@Entity
@Table(name="parma_tipo_producto")
@NamedQuery(name="ParmaTipoProducto.findAll", query="SELECT p FROM ParmaTipoProducto p")
public class ParmaTipoProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parma_tipo_producto", unique=true, nullable=false)
	private Integer idParmaTipoProducto;

	@Column(name="nombre_tipo_producto", nullable=false, length=30)
	private String nombreTipoProducto;

	//bi-directional many-to-one association to ParmaProducto
	@OneToMany(mappedBy="parmaTipoProducto")
	private List<ParmaProducto> parmaProductos;

	public ParmaTipoProducto() {
	}

	public Integer getIdParmaTipoProducto() {
		return this.idParmaTipoProducto;
	}

	public void setIdParmaTipoProducto(Integer idParmaTipoProducto) {
		this.idParmaTipoProducto = idParmaTipoProducto;
	}

	public String getNombreTipoProducto() {
		return this.nombreTipoProducto;
	}

	public void setNombreTipoProducto(String nombreTipoProducto) {
		this.nombreTipoProducto = nombreTipoProducto;
	}

	public List<ParmaProducto> getParmaProductos() {
		return this.parmaProductos;
	}

	public void setParmaProductos(List<ParmaProducto> parmaProductos) {
		this.parmaProductos = parmaProductos;
	}

	public ParmaProducto addParmaProducto(ParmaProducto parmaProducto) {
		getParmaProductos().add(parmaProducto);
		parmaProducto.setParmaTipoProducto(this);

		return parmaProducto;
	}

	public ParmaProducto removeParmaProducto(ParmaProducto parmaProducto) {
		getParmaProductos().remove(parmaProducto);
		parmaProducto.setParmaTipoProducto(null);

		return parmaProducto;
	}

}