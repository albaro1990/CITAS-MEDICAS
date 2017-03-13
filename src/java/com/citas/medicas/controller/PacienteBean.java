/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.dao.RolDao;
import com.citas.medicas.dao.UsuarioAplicacionDao;
import com.citas.medicas.dao.UsuarioDao;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.dao.impl.ClienteDaoImpl;
import com.citas.medicas.dao.impl.EspecialidadDaoImpl;
import com.citas.medicas.dao.impl.RolDaoImpl;
import com.citas.medicas.dao.impl.UsuarioAplicacionDaoImpl;
import com.citas.medicas.dao.impl.UsuarioDaoImpl;
import com.citas.medicas.entity.CitEspecialidad;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacCiudad;
import com.citas.medicas.entity.FacRol;
import com.citas.medicas.entity.FacUsuario;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import com.citas.medicas.utilitarios.ValidadorCedulaRuc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rene.travez
 */
@ManagedBean(name = "pacienteBean")
@ViewScoped
public class PacienteBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(UsuarioBean.class);
    private ClienteDao clienteDAO = new ClienteDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private List<FacCiudad> ciudades = new ArrayList<FacCiudad>();
    private List<CitPaciente> pacientes = new ArrayList<CitPaciente>();
    private CitPaciente paciente;
    private FacCiudad ciudad;
    private Integer codigoRol;
    private Integer codigoEstado;
    private String confirmarClave;
    private boolean mostrarEspecialidad=false;
    private Integer codigoEsp;
    private Integer codigoCiudad;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public PacienteBean() {
        paciente = new CitPaciente();
        cargarCombos();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        paciente = new CitPaciente();
        codigoRol = null;
        confirmarClave = null;
        ciudades = new ArrayList<FacCiudad>();
        pacientes = new ArrayList<CitPaciente>();
        try {
            ciudades = ciudadDAO.findAll();
            pacientes = clienteDAO.findAll();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }

    public void cargarCombos() {
        try {
            ciudades = ciudadDAO.findAll();
            pacientes = clienteDAO.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarDependencias() {
        try {
            paciente = new CitPaciente();
            ciudades = ciudadDAO.findAll();
            pacientes = clienteDAO.findAll();
            for (CitPaciente item : pacientes) {
                item.setCodigoCiudad(ciudadDAO.find(item.getCodigoCiudad().getCiuCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
         RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (paciente.getPacCodigo() == null) {
                if (!clienteDAO.existePorCampo(paciente.getPacIdentificacin())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(paciente.getPacIdentificacin())) {
                        paciente.setPacEstado(1);
                        paciente.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                        int idc = clienteDAO.save(paciente);
                        if (idc > 0) {
                            saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacIdentificacin() + " creado correctamente");
                            requestContext.execute("PF('dlListaCliente').hide()");
                        }
                    } else {
                        saveMessageErrorDetail("Paciente", "La cédula o ruc es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Paciente", "Paciente " + paciente.getPacIdentificacin() + " ya existe");
                }
            }else if (paciente.getPacCodigo() != null) {
                paciente.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                clienteDAO.update(paciente);
                cargarDependencias();
                //this.inicializar(actionEvent);
                saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacNombres() + " modificado correctamente");
                
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Paciente", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        paciente = new CitPaciente();
        try {
            paciente = (CitPaciente) event.getComponent().getAttributes().get("objetoEditar");
            ciudad = paciente.getCodigoCiudad();
            codigoCiudad = ciudad.getCiuCodigo().intValue();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void remove(ActionEvent event) {
        paciente = new CitPaciente();
        try {
            paciente = (CitPaciente) event.getComponent().getAttributes().get("objetoEliminar");
            int ndel = clienteDAO.delete(paciente.getPacCodigo().intValue());
            if (ndel > 0) {
                cargarDependencias();
                this.inicializar(event);
                saveMessageInfoDetail("Paciente", "Paciente " + paciente.getPacNombres() + " eliminado correctamente");
            } else {
                saveMessageErrorDetail("Paciente", "Paciente " + paciente.getPacNombres() + " error al eliminar");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public ClienteDao getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDao clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente paciente) {
        this.paciente = paciente;
    }

    public FacCiudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(FacCiudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public Integer getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public boolean isMostrarEspecialidad() {
        return mostrarEspecialidad;
    }

    public void setMostrarEspecialidad(boolean mostrarEspecialidad) {
        this.mostrarEspecialidad = mostrarEspecialidad;
    }

    public Integer getCodigoEsp() {
        return codigoEsp;
    }

    public void setCodigoEsp(Integer codigoEsp) {
        this.codigoEsp = codigoEsp;
    }

    public List<FacCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<FacCiudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    public List<CitPaciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<CitPaciente> pacientes) {
        this.pacientes = pacientes;
    }

}
