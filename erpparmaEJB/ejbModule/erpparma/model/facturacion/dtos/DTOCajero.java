package erpparma.model.facturacion.dtos;

public class DTOCajero {
	private String codigo;
	private String apellidos;
	private String nombres;
	private String correo;
	
	
	public DTOCajero() {
		
	}
	

	public DTOCajero(String codigo, String apellidos, String nombres, String correo) {
	
		this.codigo = codigo;
		this.apellidos = apellidos;
		this.nombres = nombres;
		this.correo = correo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCodigo() {

		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

}
