package erpparma.model.facturacion.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import erpparma.model.core.entities.ParmaFactura;
import erpparma.model.core.entities.ParmaFacturacionDetalle;

public class UtilFacturacion {

	public static double MINIMO_PARA_FACTURAR_PARMA = 10;
	public static double IVA_PARMA = 0.14;
	public static double DESCUENTO_PARMA = 0.10;

	public static ArrayList<ParmaFacturacionDetalle> getNewListDetalleFactura() {
		return new ArrayList<ParmaFacturacionDetalle>();
	}

	public static ParmaFactura getNewFactura() {
		return new ParmaFactura();
	}

	public static BigDecimal getNewBigDecimal(String value, int scale) {
		return new BigDecimal(value.toString()).setScale(scale, RoundingMode.HALF_UP);
	}

	public static BigDecimal calculateGlobalSubtotal(List<ParmaFacturacionDetalle> items) {
		Double total = .0;
		for (ParmaFacturacionDetalle item : items) {
			total = total + item.getSubtotal().doubleValue();
		}
		return getNewBigDecimal(total.toString(), 2);
	}

	public static BigDecimal calculateDifference(BigDecimal value, BigDecimal putOff) {
		Double difference = value.doubleValue() - putOff.doubleValue();
		return getNewBigDecimal(difference.toString(), 2);
	}

	public static BigDecimal calculateAdd(BigDecimal value, BigDecimal increase) {
		Double difference = value.doubleValue() + increase.doubleValue();
		return getNewBigDecimal(difference.toString(), 2);
	}

	public static BigDecimal calculateIVA(BigDecimal subtotal) {
		Double ivaValue = subtotal.doubleValue() * IVA_PARMA;
		return getNewBigDecimal(ivaValue.toString(), 2);
	}

	public static BigDecimal calculateDescuento(BigDecimal subtotal) {
		Double descuentoValue = subtotal.doubleValue() * DESCUENTO_PARMA;
		return getNewBigDecimal(descuentoValue.toString(), 2);
	}

	public static BigDecimal calculatePorcentageValue(BigDecimal value, Double pp) {
		Double porcentageValue = value.doubleValue() * pp;
		return getNewBigDecimal(porcentageValue.toString(), 2);
	}

}
