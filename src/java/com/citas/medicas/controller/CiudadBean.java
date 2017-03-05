/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.entity.FacCiudad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "ciudadBean")
@ViewScoped
public class CiudadBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CiudadBean.class);
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private List<FacCiudad> listaCiudades = new ArrayList<FacCiudad>();
    private FacCiudad ciudad = new FacCiudad();
    private Integer codigoEstado;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public CiudadBean() {
        
            ciudad = new FacCiudad();
            cargarDependencias();
            
        
    }

    public void inicializar(ActionEvent actionEvent) {
        ciudad = new FacCiudad();
    }

    private void cargarDependencias() {
        try{
        listaCiudades = ciudadDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (ciudad.getCiuCodigo() == null) {
                if (!ciudadDAO.existePorCampo(ciudad.getCiuNombre().toUpperCase())) {
                            int idCiudad = ciudadDAO.save(ciudad);
                            cargarDependencias();
                            saveMessageInfoDetail("Ciudad", "Ciudad " + ciudad.getCiuNombre() + " creada correctamente");
                            this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("Ciudad", "Ciudad " + ciudad.getCiuNombre()+ " ya existe");
                }
            } else if(ciudad.getCiuCodigo() != null){
                if (!ciudadDAO.existePorCampo(ciudad.getCiuNombre().toUpperCase())) {
                ciudadDAO.update(ciudad);
                cargarDependencias();
                this.inicializar(actionEvent);
                }else {
                    saveMessageErrorDetail("Ciudad", "Ciudad " + ciudad.getCiuNombre()+ " ya existe");
                }
            }
            
                cargarDependencias();
        } catch (SQLException e) {
            saveMessageErrorDetail("Usuario", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        ciudad = new FacCiudad();
        try {
            ciudad = (FacCiudad) event.getComponent().getAttributes().get("objetoEditar");

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        try {
            ciudad = (FacCiudad) event.getComponent().getAttributes().get("objetoEliminar");
            ciudadDAO.delete(ciudad.getCiuCodigo().intValue());
             cargarDependencias();
             this.inicializar(event);
        } catch (Exception e) {
        }
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public List<FacCiudad> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<FacCiudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public FacCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(FacCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }


}
