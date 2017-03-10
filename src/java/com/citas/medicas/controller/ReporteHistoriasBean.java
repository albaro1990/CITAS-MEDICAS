/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;


import com.citas.medicas.dao.AntFamiliaresDao;
import com.citas.medicas.dao.AntPersonalesDao;
import com.citas.medicas.dao.HistoriaDao;
import com.citas.medicas.dao.impl.AntFamiliaresDaoImpl;
import com.citas.medicas.dao.impl.AntPersonalesDaoImpl;
import com.citas.medicas.dao.impl.HistoriaDaoImpl;
import com.citas.medicas.entity.CitAntFamiliares;
import com.citas.medicas.entity.CitAntPersonales;
import com.citas.medicas.entity.CitHistoriaClinica;
import com.citas.medicas.entity.CitPaciente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "reporteHistoriasBean")
@ViewScoped
public class ReporteHistoriasBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(ReporteHistoriasBean.class);
    
    private List<CitHistoriaClinica> listHistorias = new ArrayList<CitHistoriaClinica>();
    
    private CitHistoriaClinica historia = new CitHistoriaClinica();
    private CitAntFamiliares antFamiliares = new CitAntFamiliares();
    private CitAntPersonales antPersonales = new CitAntPersonales();
    private CitPaciente paciente = new CitPaciente();
    
    private HistoriaDao historiaDao = new HistoriaDaoImpl();
    private AntFamiliaresDao antFamiliaresDao = new AntFamiliaresDaoImpl();
    private AntPersonalesDao antPersonalesDao = new AntPersonalesDaoImpl();
    
    private Date fechaDesde;
    private Date fechaHasta;
    private String cedula;
            
 

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public ReporteHistoriasBean() {
        fechaDesde = new Date();
        fechaHasta = new Date();
    }

    public void inicializar(ActionEvent actionEvent) {
        

    }

    public void buscarArticulo(ActionEvent actionEvent) {
        try {
           
                
                
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void create(ActionEvent actionEvent) {

    }

    public void edit(ActionEvent event) {
       
        
    }

    public void remove(ActionEvent event) {
      
    }

    public List<CitHistoriaClinica> getListHistorias() {
        return listHistorias;
    }

    public void setListHistorias(List<CitHistoriaClinica> listHistorias) {
        this.listHistorias = listHistorias;
    }

    public CitHistoriaClinica getHistoria() {
        return historia;
    }

    public void setHistoria(CitHistoriaClinica historia) {
        this.historia = historia;
    }

    public CitAntFamiliares getAntFamiliares() {
        return antFamiliares;
    }

    public void setAntFamiliares(CitAntFamiliares antFamiliares) {
        this.antFamiliares = antFamiliares;
    }

    public CitAntPersonales getAntPersonales() {
        return antPersonales;
    }

    public void setAntPersonales(CitAntPersonales antPersonales) {
        this.antPersonales = antPersonales;
    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente paciente) {
        this.paciente = paciente;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

   

}
