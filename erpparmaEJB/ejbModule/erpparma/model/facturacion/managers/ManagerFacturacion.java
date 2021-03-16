package erpparma.model.facturacion.managers;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import erpparma.model.core.managers.ManagerDAO;


@Stateless
@LocalBean
public class ManagerFacturacion {

	//pass admin1004291447
	
	@EJB
	private ManagerDAO mDao;
   
    public ManagerFacturacion() {
       
    }
    
    
    
    
    

}
