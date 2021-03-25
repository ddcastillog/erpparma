package erpparma.model.clientes.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import erpparma.model.clientes.dto.DTOProducto;
import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaDetallePedido;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaPedido;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.core.managers.ManagerDAO;
import erpparma.model.inventario.managers.ManagerInventario;

/**
 * Session Bean implementation class ManagerClientes
 */
@Stateless
@LocalBean
public class ManagerClientes {

	@EJB
	private ManagerInventario minventario;
	@EJB
	private ManagerDAO mDAO;

	/**
	 * Default constructor.
	 */
	public ManagerClientes() {
		// TODO Auto-generated constructor stub
	}

	public List<ParmaProducto> generardatosProductos3() {

		String field = "parmaTipoProducto.nombreTipoProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat("ventas").concat("'");
		List<ParmaProducto> productos = mDAO.findWhere(ParmaProducto.class, where, "idParmaProducto");
		return productos;

	}

	public List<ParmaProducto> generardatosProductos() {

		List<ParmaProducto> producto = new ArrayList<ParmaProducto>();

		producto = (minventario.findAllParmaProducto());

		return producto;

	}

	public List<ParmaAjuste> generardatosProductos1() {

		List<ParmaAjuste> producto = new ArrayList<ParmaAjuste>();

		producto = (minventario.findAllParmaAjuste());

		return producto;

	}

	public List<ParmaInventario> generardatosProductos2() {

		List<ParmaInventario> producto = new ArrayList<ParmaInventario>();

		producto = (minventario.findAllInventario());

		return producto;

	}

	// METODO PARA INGRESAR AL CARRITO

	// Tipo de producto
	public void insertarParmaAjuste(ParmaAjuste ajuste) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		ajuste.setFechaAjuste(new Timestamp(millis));
		List<ParmaInventario> inventario = mDAO.findWhere(ParmaInventario.class,
				"o.parmaProducto='" + ajuste.getParmaProducto().getIdParmaProducto() + "'", null);
		try {
			ParmaInventario inve = inventario.get(0);
			if (ajuste.getTipoAjuste()) {
				int cantidad = inve.getCantidad().intValue() + ajuste.getCantidadAjuste().intValue();
				inve.setCantidad(cantidad);
			} else {
				int cantidad = inve.getCantidad().intValue() - ajuste.getCantidadAjuste().intValue();
				if (cantidad >= 0) {
					inve.setCantidad(cantidad);
					mDAO.actualizar(inve);
					mDAO.insertar(ajuste);
				} else {
					throw new Exception("No hay tantos productos para el egreso");
				}

			}

		} catch (Exception e) {
			if (ajuste.getTipoAjuste()) {
				ParmaInventario newinve = new ParmaInventario();
				newinve.setParmaProducto(ajuste.getParmaProducto());
				newinve.setCantidad(ajuste.getCantidadAjuste().intValue());
				mDAO.insertar(newinve);
				mDAO.insertar(ajuste);
			} else {
				throw new Exception("No hay este producto para el egresso");
			}
		}
	}

	public List<ParmaProducto> agregardatosCarrito(List<ParmaProducto> carrito, ParmaProducto p) {

		ParmaInventario parmai = new ParmaInventario();
		ParmaDetallePedido detalle = new ParmaDetallePedido();

		if (carrito == null)

			carrito = new ArrayList<ParmaProducto>();

		carrito.add(p);

		return carrito;

	}

	public List<ParmaDetallePedido> agregardatosCarritoCantidad(List<ParmaDetallePedido> carrito, ParmaProducto p
		) {

		ParmaDetallePedido detalle = new ParmaDetallePedido();

		if (carrito == null) {

			carrito = new ArrayList<ParmaDetallePedido>();

			detalle.setCantidad(0);
			detalle.setParmaProducto(p);

			carrito.add(detalle);

		} else {
			
			int posicion = buscarproductoenCarrito(carrito, p);
			
			if (posicion != -1) {
				
				carrito.get(posicion).setCantidad(carrito.get(posicion).getCantidad());
				
			} else {
				
				detalle.setCantidad(0);
				detalle.setParmaProducto(p);

				carrito.add(detalle);


			}

		}


		return carrito;

	}

	public int buscarproductoenCarrito(List<ParmaDetallePedido> carrito, ParmaProducto producto) {

		for (int i = 0; i < carrito.size(); i++) {

			if (carrito.get(i).getParmaProducto().getIdParmaProducto() == producto.getIdParmaProducto()) {

				return i;

			}

		}

		return -1;

	}

	public List<ParmaAjuste> agregardatosCarrito1(List<ParmaAjuste> carrito, ParmaAjuste p) {

		List<ParmaInventario> inventario = mDAO.findWhere(ParmaInventario.class,
				"o.parmaProducto='" + p.getParmaProducto().getIdParmaProducto() + "'", null);

		ParmaInventario inve = inventario.get(0);

		if (carrito == null)

			carrito = new ArrayList<ParmaAjuste>();

		carrito.add(p);

		System.out.println("SE AÑADIO AL CARRITO");

		int cantidad = inve.getCantidad().intValue() - p.getCantidadAjuste().intValue();

		System.out.println("SE HIZO LA RESTA ");

		inve.setCantidad(cantidad);

		System.out.println("SE cambio el Dato ");

		return carrito;

	}

	public List<ParmaDetallePedido> eliminardatosCarrito(List<ParmaDetallePedido> carrito, int idproducto) {

		if (carrito == null)

			return null;

		int i = 0;

		for (ParmaDetallePedido producto : carrito) {

			if (producto.getParmaProducto().getIdParmaProducto() == idproducto) {

				carrito.remove(i);
				break;

			}

			i++;

		}

		return carrito;

	}
	
	
	public void insertarPedido (List<ParmaDetallePedido> carrito , ParmaPedido pedido, Integer idSegUsuario) throws Exception {
		
		
		SegUsuario usuario = (SegUsuario)mDAO.findById(SegUsuario.class, idSegUsuario);
		
		pedido.setSegUsuario(usuario);
		
		mDAO.insertar(pedido);
		
		for (int i = 0; i < carrito.size(); i++) {
			
			carrito.get(i).setParmaPedido(pedido);
			mDAO.insertar(carrito.get(i));
			
			
			
		}
		
		
		
	}
	
	

	public List<ParmaAjuste> eliminardatosCarrito1(List<ParmaAjuste> carrito, int idproducto) {

		if (carrito == null)

			return null;

		int i = 0;

		for (ParmaAjuste producto : carrito) {

			if (producto.getParmaProducto().getIdParmaProducto() == idproducto) {

				carrito.remove(i);
				break;

			}

			i++;

		}

		return carrito;

	}

	public double CalcularTotal(List<ParmaProducto> carrito) {

		double suma = 0;

		for (ParmaProducto producto : carrito)

			suma += producto.getPrecioProducto().doubleValue();

		return suma;

	}

	public double CalcularTotalparcial(ParmaProducto producto, int cantidad) {

		double respuesta = 0;

		respuesta = producto.getPrecioProducto().doubleValue() * cantidad;

		return respuesta;

	}

	/// Codigos ESpeciales

	public List<ParmaProducto> findAllProductosVenta() {
		String field = "parmaTipoProducto.nombreTipoProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat("venta").concat("'");
		List<ParmaProducto> productos = this.mDAO.findWhere(ParmaProducto.class, where, "idParmaProducto");
		return productos;
	}	

}
