package erpparma.model.facturacion.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.SegAsignacion;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.core.managers.ManagerDAO;
import erpparma.model.facturacion.dtos.DTOClientes;

@Stateless
@LocalBean
public class ManagerFacturacion {

	// pass admin1004291447

	@EJB
	private ManagerDAO mDao;

	public ManagerFacturacion() {

	}

	@SuppressWarnings("unchecked")
	public List<DTOClientes> findAllClientes() {
		String field = "segModulo";
		String where = "o.".concat(field).concat("=").concat("'").concat("4").concat("'");
		List<SegAsignacion> asigs = this.mDao.findWhere(SegAsignacion.class, where, "idSegAsignacion");
		List<DTOClientes> clientes = new ArrayList<DTOClientes>();
		for (SegAsignacion asig : asigs) {
			SegUsuario user = asig.getSegUsuario();
			DTOClientes cliente = new DTOClientes(user.getCodigo(), user.getApellidos(), user.getNombres(),
					user.getCorreo(), user.getActivo());
			clientes.add(cliente);
		}
		return clientes;
	}

	public DTOClientes findClienteById() {

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ParmaProducto> findAllProductosVenta() {
		String field = "parmaTipoProducto.nombreTipoProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat("venta").concat("'");
		List<ParmaProducto> productos = this.mDao.findWhere(ParmaProducto.class, where, "idParmaProducto");
		return productos;
	}

	public ParmaProducto findProductoVentaById(Integer id) throws Exception {
		ParmaProducto p = (ParmaProducto) this.mDao.findById(ParmaProducto.class, id);
		if (p.getParmaTipoProducto().getNombreTipoProducto() == "venta") {
			return p;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ParmaProducto findProductoVentaByName(String name) throws Exception {
		String field = "nombreProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat(name).concat("'");
		List<ParmaProducto> p = this.mDao.findWhere(ParmaProducto.class, where, "nombreProducto");

		if (p.get(0).getParmaTipoProducto().getNombreTipoProducto().equals("venta")) {
			System.out.println("Tamaño: " + p.get(0).getParmaTipoProducto().getNombreTipoProducto());
			return p.get(0);
		}
		return null;
	}

}
