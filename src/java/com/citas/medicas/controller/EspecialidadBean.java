/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.dao.impl.EspecialidadDaoImpl;
import com.citas.medicas.entity.CitEspecialidad;
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
 * @author
 */
@ManagedBean(name = "especialidadBean")
@ViewScoped
public class EspecialidadBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(EspecialidadBean.class);
    private EspecialidadDao especialidadDAO = new EspecialidadDaoImpl();
    private List<CitEspecialidad> listaEspecialidades = new ArrayList<CitEspecialidad>();
    private CitEspecialidad especialidad = new CitEspecialidad();
    private Integer codigoEstado;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public EspecialidadBean() {

        especialidad = new CitEspecialidad();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        especialidad = new CitEspecialidad();
    }

    private void cargarDependencias() {
        try {
            listaEspecialidades = especialidadDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            if (especialidad.getEspCodigo() == null) {
                if (!especialidadDAO.existePorCampo(especialidad.getEspDescripcion().toUpperCase())) {
                    int idEspecialidad = especialidadDAO.save(especialidad);
                    cargarDependencias();
                    saveMessageInfoDetail("Especialidad", "Especialidad " + especialidad.getEspDescripcion() + " creada correctamente");
                    this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("Especialidad", "Especialidad " + especialidad.getEspDescripcion() + " ya existe");
                }
            } else if (especialidad.getEspCodigo() != null) {
                if (!especialidadDAO.existePorCampo(especialidad.getEspDescripcion().toUpperCase())) {
                    especialidadDAO.update(especialidad);
                    cargarDependencias();
                    this.inicializar(actionEvent);
                } else {
                    saveMessageErrorDetail("Especialidad", "Especialidad " + especialidad.getEspDescripcion() + " ya existe");
                }
            }

            cargarDependencias();
        } catch (SQLException e) {
            saveMessageErrorDetail("Especialidad", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        especialidad = new CitEspecialidad();
        try {
            especialidad = (CitEspecialidad) event.getComponent().getAttributes().get("objetoEditar");

        } catch (Exception e) {
        }

    }

    public void remove(ActionEvent event) {
        try {
            especialidad = (CitEspecialidad) event.getComponent().getAttributes().get("objetoEliminar");
            especialidadDAO.delete(especialidad.getEspCodigo().intValue());
            cargarDependencias();
            this.inicializar(event);
        } catch (Exception e) {
        }
    }

    public EspecialidadDao getEspecialidadDAO() {
        return especialidadDAO;
    }

    public void setEspecialidadDAO(EspecialidadDao especialidadDAO) {
        this.especialidadDAO = especialidadDAO;
    }

    public List<CitEspecialidad> getListaEspecialidades() {
        return listaEspecialidades;
    }

    public void setListaEspecialidades(List<CitEspecialidad> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
    }

    public CitEspecialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(CitEspecialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
}
