package com.citas.medicas.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


public abstract class GenericBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public GenericBean() {
        super();
    }

    public void agregarDialogMensaje(FacesContext context, Severity severity, String titulo, String cuerpo, RequestContext requestContext) {
        FacesMessage message = new FacesMessage(severity, titulo, cuerpo);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void saveMessageFatal(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, null));
    }

    public void saveMessageFatalDetail(String msg, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, detail));
    }

    public void saveMessageError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
    }

    public void saveMessageErrorDetail(String msg, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail));
    }

    public void saveMessageWarn(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
    }

    public void saveMessageWarnDetail(String msg, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, msg, detail));
    }

    public void saveMessageInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
    }

    public void saveMessageInfoDetail(String msg, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail));
    }
    
    public HttpSession getHttpSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
}
