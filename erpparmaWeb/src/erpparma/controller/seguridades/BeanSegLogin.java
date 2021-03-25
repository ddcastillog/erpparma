package erpparma.controller.seguridades;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import erpparma.controller.JSFUtil;
import erpparma.controller.inventario.BeanParmaAjustes;
import erpparma.model.core.entities.ParmaInventario;
import erpparma.model.core.entities.SegModulo;

import erpparma.model.core.entities.SegUsuario;

import erpparma.model.inventario.managers.ManagerInventario;

import erpparma.model.seguridades.dtos.LoginDTO;
import erpparma.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanSegLogin implements Serializable {
	private int idSegUsuario;
	private String clave;
	private String direccionIP;
	private LoginDTO loginDTO;
	@EJB
	private ManagerSeguridades mSeguridades;
	private PieChartModel pieModel;
	@EJB
	private ManagerInventario mInventario;
	
	
	
	public BeanSegLogin() {
		
	}
	@PostConstruct
	public void inicializar() {
		HttpServletRequest req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String agente=req.getHeader("user-agent");
		String ipAddress=req.getHeader("X-FORWARDED-FOR");
		if(ipAddress==null) {
			ipAddress=req.getRemoteAddr();
		}
		direccionIP=ipAddress;
	}
	
	public String actionLogin() {
		try {
			loginDTO=mSeguridades.login(idSegUsuario, clave,direccionIP);
			return "menu?faces-redirect=true";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public String actionMenu(String ruta) {
		if(ruta.equals("inventario/menu")) {
			createPieModel();
		}
		return ruta+"?faces-redirect=true";
	}
	
	public String actionCerrarSesion(){
		mSeguridades.cerrarSesion(loginDTO.getIdSegUsuario());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}
	
	public void actionVerificarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String requestPath=ec.getRequestPathInfo();
		
		//primero validamos si loginDTO aun no se ha inicializado es porque
		//el usuario aun no ha pasado por la pantalla de login:
		if(loginDTO==null || loginDTO.getIdSegUsuario()==0)
		{
			try {
				mSeguridades.accesoNoPermitido(0, requestPath);
				ec.redirect(ec.getRequestContextPath()+"/faces/login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//Extraemos la parte inicial de la ruta a la que se esta accediendo:
		String rutaUsuario=requestPath.substring(1);
		rutaUsuario=rutaUsuario.substring(0, rutaUsuario.indexOf("/"));
		//validacion de la ruta de acceso:
		boolean verificado=false;
		for(SegModulo modulo:loginDTO.getListaModulos()) {
			//extraemos el inicio de la ruta (nombre de la carpeta):
			String rutaModulo=modulo.getRutaAcceso();
			rutaModulo=rutaModulo.substring(0,rutaModulo.indexOf("/"));
			//verificamos con la ruta a la que se está accediendo:
			if(rutaUsuario.equals(rutaModulo)){
				verificado=true;
				break;
			}
		}
		try {
			if(verificado==false) {
				mSeguridades.accesoNoPermitido(loginDTO.getIdSegUsuario(), requestPath);
				ec.redirect(ec.getRequestContextPath()+"/faces/login.xhtml");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionListenerInicialiarDemo() {
		try {
			mSeguridades.inicializarDemo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionMenuNuevoUsuario1() {
		
		return "usuarios_ciente";
	}

	public int getIdSegUsuario() {
		return idSegUsuario;
	}

	public void setIdSegUsuario(int idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}	
	private  void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<ParmaInventario> cantidades=mInventario.mayorcantodadInventario();
        List<Number> values = new ArrayList<>();
        for (int i = 0; i < cantidades.size(); i++) {
			values.add(cantidades.get(i).getCantidad());
		}        
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < cantidades.size(); i++) {
        	labels.add(cantidades.get(i).getParmaProducto().getNombreProducto());
		}  
        data.setLabels(labels);

        pieModel.setData(data);
    }

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}
	
}
