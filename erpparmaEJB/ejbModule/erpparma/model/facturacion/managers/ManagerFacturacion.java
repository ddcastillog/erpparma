package erpparma.model.facturacion.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;
import erpparma.model.core.entities.ParmaProducto;
import erpparma.model.core.entities.SegAsignacion;
import erpparma.model.core.entities.SegModulo;
import erpparma.model.core.entities.SegUsuario;
import erpparma.model.core.entities.ThmEmpleado;
import erpparma.model.core.managers.ManagerDAO;
import erpparma.model.facturacion.dtos.DTOClientes;
import erpparma.model.facturacion.dtos.DTOFactura;

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
			DTOClientes cliente = new DTOClientes(user.getIdSegUsuario(), user.getCodigo(), user.getApellidos(),
					user.getNombres(), user.getCorreo(), user.getActivo());
			clientes.add(cliente);
		}
		return clientes;
	}

	public void crearCliente(SegUsuario cliente) throws Exception {
		cliente.setClave("parmanuevo");
		cliente.setActivo(true);
		this.mDao.insertar(cliente);
		SegModulo segModuloCliente = (SegModulo) this.mDao.findById(SegModulo.class, 4);
		SegAsignacion asignacion = new SegAsignacion();
		asignacion.setSegModulo(segModuloCliente);
		asignacion.setSegUsuario(cliente);
		this.mDao.insertar(asignacion);
	}

	@SuppressWarnings("unchecked")
	public DTOClientes findClienteByIdOrCorreo(String value) {
		String whereJPQL = "o.segModulo.idSegModulo = '4' AND (o.segUsuario.codigo = '" + value
				+ "' OR o.segUsuario.correo = '" + value + "')";
		List<SegAsignacion> asig = this.mDao.findWhere(SegAsignacion.class, whereJPQL, "idSegAsignacion");
		if ((asig.size() == 1)) {
			SegUsuario user = asig.get(0).getSegUsuario();
			return new DTOClientes(user.getIdSegUsuario(), user.getCodigo(), user.getApellidos(), user.getNombres(),
					user.getCorreo(), user.getActivo());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public DTOClientes findFacturas(DTOClientes cliente) {
		String whereJQPL = "segUsuario.idSegUsuario = '" + cliente.getId().toString() + "'";
		List<ParmaFactura> facturas = this.mDao.findWhere(ParmaFactura.class, whereJQPL, "idFactura");
		cliente.getFacturas().clear();
		for (ParmaFactura factura : facturas) {
			DTOFactura f = new DTOFactura();
			f.setIdFactura(factura.getIdFactura());
			f.setFechaFactura(factura.getFechaFactura());
			f.setDescuento(factura.getDescuento());
			f.setIva(factura.getIva());
			f.setTotal(factura.getTotal());
			f.setSubtotal(factura.getSubtotal());
			cliente.getFacturas().add(f);
		}
		return cliente;
	}

	@SuppressWarnings("unchecked")
	public DTOFactura findDetallesFactura(DTOFactura factura) {
		String whereJPQL = "o.parmaFactura.idFactura = '" + factura.getIdFactura().toString() + "'";
		List<ParmaFacturacionDetalle> detalles = this.mDao.findWhere(ParmaFacturacionDetalle.class, whereJPQL,
				"idDetalleFactura");
		factura.setItems(detalles);
		return factura;
	}

	@SuppressWarnings("unchecked")
	public List<ParmaProducto> findAllProductosVenta() {
		String field = "parmaTipoProducto.nombreTipoProducto";
		String where = "o.".concat(field).concat("=").concat("'").concat("venta").concat("'");
		List<ParmaProducto> productos = this.mDao.findWhere(ParmaProducto.class, where, "idParmaProducto");
		return productos;
	}

	@SuppressWarnings("unchecked")
	public void crearFactura(ParmaFactura factura, DTOClientes cliente, Integer idCajero) throws Exception {
		SegUsuario clienteFinal = (SegUsuario) this.mDao.findById(SegUsuario.class, cliente.getId());
		factura.setSegUsuario(clienteFinal);
		String whereCajero = "segUsuario.idSegUsuario = '" + idCajero.toString() + "'";
		List<ThmEmpleado> cajerosFound = this.mDao.findWhere(ThmEmpleado.class, whereCajero, "idThmEmpleado");
		ThmEmpleado cajero = cajerosFound.get(0);
		factura.setThmEmpleado(cajero);
		this.mDao.insertar(factura);
		List<ParmaFacturacionDetalle> detalles = factura.getParmaFacturacionDetalles();
		for (ParmaFacturacionDetalle det : detalles) {
			det.setParmaFactura(factura);
			this.mDao.insertar(det);
		}
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
