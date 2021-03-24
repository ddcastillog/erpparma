package erpparma.model.inventario.managers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
import erpparma.model.core.entities.ThmEmpleado;
import erpparma.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerInventario
 */
@Stateless
@LocalBean
public class ManagerInventario {
	@EJB
	private ManagerDAO mDAO;

	public ManagerInventario() {
		// TODO Auto-generated constructor stub
	}

	// PRODUCTOS
	public List<ParmaProducto> findAllParmaProducto(Integer idParmaTipoProducto) {
		String field = "parmaTipoProducto";
		String where = "o.".concat(field).concat("=").concat(idParmaTipoProducto+"");
		return mDAO.findWhere(ParmaProducto.class,where, null);
	}
	// PRODUCTOS
		public List<ParmaProducto> findAllParmaProducto() 	{		
			return mDAO.findAll(ParmaProducto.class);
		}

	// Insertar productos
	public void insertarParmaProducto(ParmaProducto producto, Integer idParmaTipoProducto) throws Exception {
		ParmaTipoProducto tipoProducto = (ParmaTipoProducto) mDAO.findById(ParmaTipoProducto.class,
				idParmaTipoProducto);
		producto.setParmaTipoProducto(tipoProducto);
		ParmaInventario inventario = new ParmaInventario();
		inventario.setParmaProducto(producto);
		inventario.setActivo(true);
		inventario.setCantidad(0);
		mDAO.insertar(producto);
		mDAO.insertar(inventario);
	}

	// Actualizar producto
	public void actualizarParmaProducto(ParmaProducto producto, Integer idParmaTipoProducto) throws Exception {
		ParmaTipoProducto tipoProducto = (ParmaTipoProducto) mDAO.findById(ParmaTipoProducto.class,
				idParmaTipoProducto);
		producto.setParmaTipoProducto(tipoProducto);
		mDAO.actualizar(producto);
	}

	// Elimar producto
	public void eliminarParmaProducto(Integer idParmaProducto) throws Exception {
		mDAO.eliminar(ParmaProducto.class, idParmaProducto);
	}

	// Tipo de producto
	public List<ParmaTipoProducto> findAllParmaTipoProducto() {
		return mDAO.findAll(ParmaTipoProducto.class);
	}

	// Insertar Tipo de producto
	public void insertarParmaTipoProducto(ParmaTipoProducto tipoProducto) throws Exception {
		mDAO.insertar(tipoProducto);
	}

	// Actualizar Tipo de producto
	public void actualizarParmaTipoProducto(ParmaTipoProducto tipoProducto) throws Exception {
		mDAO.actualizar(tipoProducto);
	}

	// Elimar Tipo de producto
	public void eliminarParmaTipoProducto(Integer idParmaTipoProducto) throws Exception {
		mDAO.eliminar(ParmaTipoProducto.class, idParmaTipoProducto);
	}

	// Tipo de producto
	public List<ParmaAjuste> findAllParmaAjuste() {
		return mDAO.findAll(ParmaAjuste.class);
	}

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
				mDAO.actualizar(inve);
				mDAO.insertar(ajuste);
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
				newinve.setActivo(true);
				mDAO.insertar(newinve);
				mDAO.insertar(ajuste);
			} else {
				throw new Exception("No hay este producto para el egresso");
			}
		}
	}

	public ParmaProducto findProductoByName(String name) throws Exception {
		String field = "nombreProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat(name).concat("'");
		List<ParmaProducto> p = this.mDAO.findWhere(ParmaProducto.class, where, "nombreProducto");
		return p.get(0);

	}

	// Inventario
	public List<ParmaInventario> findAllInventario() {
		return mDAO.findAll(ParmaInventario.class);
	}

	public void activodesactivoProducto(Integer idInventario) throws Exception {
		ParmaInventario updateinve = (ParmaInventario) mDAO.findById(ParmaInventario.class, idInventario);
		if (updateinve.getActivo()) {
			updateinve.setActivo(false);
		} else {
			updateinve.setActivo(true);
		}
		mDAO.actualizar(updateinve);
	}

	public List<ParmaInventario> mayorcantodadInventario() {		
		List<ParmaInventario> inven = mDAO.findAll(ParmaInventario.class, "cantidad");
		List<ParmaInventario> cantidades = new ArrayList<ParmaInventario>();
		int aux = 0;
		for (int i = inven.size()-1; i>=0; i--) {
			if (aux < 3) {
				cantidades.add(inven.get(i));
				aux++;
			}
		}
		return cantidades;
	}

}
