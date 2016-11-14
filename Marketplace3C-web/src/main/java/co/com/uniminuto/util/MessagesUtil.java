/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author pc
 */
@ManagedBean
public class MessagesUtil{
    
    public void configuracionClientes() {
        addMessage("Ingreso a configuracion clientes", "Data saved");
    }
     
    public void configuracionHoteles() {
        addMessage("Success", "Data updated");
    }
     
    public void configuracionParques() {
        addMessage("Success", "Data deleted");
    }
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
