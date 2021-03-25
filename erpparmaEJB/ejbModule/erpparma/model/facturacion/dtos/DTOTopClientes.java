package erpparma.model.facturacion.dtos;

public class DTOTopClientes {

	private Double cantidad;
	private String nombreCliente;

	public DTOTopClientes(Double cantidad, String nombreCliente) {
		this.cantidad = cantidad;
		this.nombreCliente = nombreCliente;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

}
