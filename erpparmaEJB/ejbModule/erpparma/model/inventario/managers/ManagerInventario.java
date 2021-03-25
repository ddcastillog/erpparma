package erpparma.model.inventario.managers;

import java.math.BigDecimal;
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

import erpparma.model.auditoria.managers.ManagerAuditoria;
import erpparma.model.core.entities.ParmaAjuste;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.ParmaTipoProducto;
import erpparma.model.core.entities.ThmEmpleado;
import erpparma.model.core.managers.ManagerDAO;
import erpparma.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerInventario
 */
@Stateless
@LocalBean
public class ManagerInventario {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	public ManagerInventario() {
		// TODO Auto-generated constructor stub
	}

	// PRODUCTOS
	public List<ParmaProducto> findAllParmaProducto(Integer idParmaTipoProducto) {
		String field = "parmaTipoProducto";
		String where = "o.".concat(field).concat("=").concat(idParmaTipoProducto + "");
		return mDAO.findWhere(ParmaProducto.class, where, null);
	}

	// PRODUCTOS
	public List<ParmaProducto> findAllParmaProducto() {
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
	public List<ParmaAjuste> findAllParmaAjuste(boolean tipoAjuste) {
		String field = "tipoAjuste";
		String where = "o.".concat(field).concat("=").concat(tipoAjuste + "");
		return mDAO.findWhere(ParmaAjuste.class, where, null);
	}

	// Tipo de producto
	public void insertarParmaAjuste(ParmaAjuste ajuste, LoginDTO loginDTO) throws Exception {
		GregorianCalendar cal = new GregorianCalendar();
		long millis = cal.getTimeInMillis();
		ajuste.setFechaAjuste(new Timestamp(millis));
		List<ParmaInventario> inventario = mDAO.findWhere(ParmaInventario.class,
				"o.parmaProducto=" + ajuste.getParmaProducto().getIdParmaProducto(), null);
		try {
			ParmaInventario inve = inventario.get(0);
			if (ajuste.getTipoAjuste()) {
				int cantidad = inve.getCantidad().intValue() + ajuste.getCantidadAjuste().intValue();
				inve.setCantidad(cantidad);
				mDAO.actualizar(inve);
				mDAO.insertar(ajuste);
				mAuditoria.mostrarLog(loginDTO, getClass(), "insertarParmaAjuste",
						"Realizo un ingreso el usuario: " + loginDTO.getIdSegUsuario() + " al producto: "
								+ ajuste.getParmaProducto().getIdParmaProducto());
			} else {
				int cantidad = inve.getCantidad().intValue() - ajuste.getCantidadAjuste().intValue();
				
				if (cantidad >= 0) {
					inve.setCantidad(cantidad);
					mDAO.actualizar(inve);
					mDAO.insertar(ajuste);
					mAuditoria.mostrarLog(loginDTO, getClass(), "insertarParmaAjuste",
							"Realizo un egreso el usuario: " + loginDTO.getIdSegUsuario() + " al producto: "
									+ ajuste.getParmaProducto().getIdParmaProducto());					
				} else {
					System.out.println(cantidad);
					throw new Exception("No hay tantos productos para el egreso");
				}
			}
		} catch (Exception e) {
			throw new Exception("No hay tantos productos para el egreso");
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

	// Inventario filtrado por tipo
	public List<ParmaInventario> findAllInventario(Integer idtipoProducto) {
		String field = "parmaProducto.parmaTipoProducto";
		String where = "o.".concat(field).concat("=").concat(idtipoProducto + "");
		return mDAO.findWhere(ParmaInventario.class, where, null);
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

	public List<ParmaInventario> stockVenta() {
		List<ParmaTipoProducto> parmaTipoProductos = mDAO.findAll(ParmaTipoProducto.class);
		String field = "parmaProducto.parmaTipoProducto";
		String where = "o.".concat(field).concat("=").concat(parmaTipoProductos.get(0).getIdParmaTipoProducto() + "").concat("and o.activo=true");
		return mDAO.findWhere(ParmaInventario.class, where, null);

	}

	public boolean ajusteFactura(List<ParmaFacturacionDetalle> parmaFacturacionDetalles,LoginDTO loginDTO)throws Exception {
		boolean transacion=true;
		for (int i = 0; i < parmaFacturacionDetalles.size(); i++) {
			int cantidad=parmaFacturacionDetalles.get(i).getParmaProducto().getParmaInventarios().get(0).getCantidad().intValue()
					-parmaFacturacionDetalles.get(i).getCantidad().intValue();
			if(!(cantidad>=0)) {
				transacion= false;
				break;
			}
		}
		if(transacion && parmaFacturacionDetalles.size()>0) {
			GregorianCalendar cal = new GregorianCalendar();
			long millis = cal.getTimeInMillis();			
			ParmaAjuste egresos;
			for (int i = 0; i < parmaFacturacionDetalles.size(); i++) {
				int cantidad=parmaFacturacionDetalles.get(i).getParmaProducto().getParmaInventarios().get(0).getCantidad().intValue()
						-parmaFacturacionDetalles.get(i).getCantidad().intValue();
				ParmaInventario inventario=parmaFacturacionDetalles.get(i).getParmaProducto().getParmaInventarios().get(0);
				inventario.setCantidad(cantidad);
				egresos= new ParmaAjuste();
				egresos.setCantidadAjuste(new BigDecimal(parmaFacturacionDetalles.get(i).getCantidad()));
				egresos.setParmaProducto(parmaFacturacionDetalles.get(i).getParmaProducto());
				egresos.setDescripcion("Venta");
				egresos.setFechaAjuste(new Timestamp(millis));
				egresos.setTipoAjuste(false);
				mDAO.actualizar(inventario);
				mDAO.insertar(egresos);
				mAuditoria.mostrarLog(loginDTO, getClass(), "ajusteFactura",
						"Realizo un egresso el usuario: " + loginDTO.getIdSegUsuario() + " al producto: "
								+ egresos.getParmaProducto().getIdParmaProducto());
			}
		}
		return transacion;
	}

	public List<ParmaInventario> mayorcantodadInventario() {
		List<ParmaInventario> inven = mDAO.findAll(ParmaInventario.class, "cantidad");
		List<ParmaInventario> cantidades = new ArrayList<ParmaInventario>();
		int aux = 0;
		for (int i = inven.size() - 1; i >= 0; i--) {
			if (aux < 3) {
				cantidades.add(inven.get(i));
				aux++;
			}
		}
		return cantidades;
	}

}
