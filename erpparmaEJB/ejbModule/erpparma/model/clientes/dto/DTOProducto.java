package erpparma.model.clientes.dto;

import java.util.ArrayList;
import java.util.List;

import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;

public class DTOProducto {
	
	private int codigo;
	private int cantidad;
	private String nombre;
	private String tipo;
	private double precio;
	private ArrayList<ParmaInventario> productosinventario;	
	private ArrayList<ParmaDetallePedido> detallepedidos;	
	

	public DTOProducto() {
		
	}


	


	public DTOProducto(int codigo, int cantidad, String nombre, String tipo, double precio,
			ArrayList<ParmaInventario> productosinventario) {
		super();
		this.codigo = codigo;
		this.cantidad = cantidad;
		this.nombre = nombre;
		this.tipo = tipo;
		this.precio = precio;
		this.productosinventario = productosinventario;
	}





	public int getCantidad() {
		return cantidad;
	}





	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}





	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public ArrayList<ParmaInventario> getProductosinventario() {
		return productosinventario;
	}


	public void setProductosinventario(ArrayList<ParmaInventario> productosinventario) {
		this.productosinventario = productosinventario;
	}
	
	
	
	
	

}
