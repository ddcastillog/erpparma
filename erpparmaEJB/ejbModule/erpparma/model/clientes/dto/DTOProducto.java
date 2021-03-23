package erpparma.model.clientes.dto;

import java.util.ArrayList;
import java.util.List;

import erpparma.model.core.entities.ParmaProducto;

public class DTOProducto {
	
	private int codigo;
	private String nombre;
	private double precio;
	private ArrayList<ParmaProducto> productosparma;
	
	private ParmaProducto producto;
	
	
	
	public ArrayList<ParmaProducto> listaDTOProductos(){
		
		
		productosparma = getProductosparma();
		
		
		return productosparma;
		
	}
	
	public DTOProducto() {
		
	}
	public DTOProducto(int codigo, String nombre, double precio, ArrayList<ParmaProducto> productosparma) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.productosparma = productosparma;
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
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public ArrayList<ParmaProducto> getProductosparma() {
		return productosparma;
	}
	public void setProductosparma(ArrayList<ParmaProducto> productosparma) {
		this.productosparma = productosparma;
	}
	
	
	
	

}
