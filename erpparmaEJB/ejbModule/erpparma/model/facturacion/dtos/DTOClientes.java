package erpparma.model.facturacion.dtos;

import java.util.ArrayList;
import java.util.List;

public class DTOClientes {

	private Integer id;
	private String codigo;
	private String apellidos;
	private String nombres;
	private String correo;
	private Boolean activo;
	List<DTOFactura> facturas;

	public DTOClientes(Integer id, String codigo, String apellidos, String nombres, String correo, Boolean activo) {
		this.id = id;
		this.codigo = codigo;
		this.apellidos = apellidos;
		this.nombres = nombres;
		this.correo = correo;
		this.activo = activo;
		this.facturas = new ArrayList<DTOFactura>();
	}

	public List<DTOFactura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<DTOFactura> facturas) {
		this.facturas = facturas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return apellidos + " " + nombres;
	}

}
