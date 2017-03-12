/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.dao.RolDao;
import com.citas.medicas.dao.UsuarioAplicacionDao;
import com.citas.medicas.dao.UsuarioDao;
import com.citas.medicas.dao.impl.EspecialidadDaoImpl;
import com.citas.medicas.dao.impl.RolDaoImpl;
import com.citas.medicas.dao.impl.UsuarioAplicacionDaoImpl;
import com.citas.medicas.dao.impl.UsuarioDaoImpl;
import com.citas.medicas.entity.CitEspecialidad;
import com.citas.medicas.entity.FacRol;
import com.citas.medicas.entity.FacUsuario;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import com.citas.medicas.utilitarios.ValidadorCedulaRuc;
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
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(UsuarioBean.class);
    private UsuarioDao usuarioDAO = new UsuarioDaoImpl();
    private RolDao rolDAO = new RolDaoImpl();
    private EspecialidadDao especilidadDAO = new EspecialidadDaoImpl();
    private UsuarioAplicacionDao usuarioAplicacionDao = new UsuarioAplicacionDaoImpl();
    private List<FacRol> roles = new ArrayList<FacRol>();
    private List<CitEspecialidad> especialidades = new ArrayList<CitEspecialidad>();
    private List<FacUsuarioAplicacion> listaUsuarioAplicacion = new ArrayList<FacUsuarioAplicacion>();
    private FacUsuario usuario;
    private List<FacUsuario> usuarioRol;
    private FacUsuarioAplicacion usuarioAplicacion;
    private Integer codigoRol;
    private Integer codigoEstado;
    private String confirmarClave;
    private boolean mostrarEspecialidad=false;
    private Integer codigoEsp;

    /**
     * Creates a new instance of JsfManejadorUsuarioBean
     */
    public UsuarioBean() {
        usuario = new FacUsuario();
        usuarioAplicacion = new FacUsuarioAplicacion();
        usuarioRol = new ArrayList<FacUsuario>();
        
        cargarCombos();
        cargarDependencias();

    }

    public void inicializar(ActionEvent actionEvent) {
        usuario = new FacUsuario();
        codigoRol = null;
        confirmarClave = null;
        listaUsuarioAplicacion = new ArrayList<FacUsuarioAplicacion>();
        try {
            cargarDependencias();
        } catch (Exception e) {
        }
    }

    public void cargarCombos() {
        try {
            roles = rolDAO.findAll();
            
            if(codigoRol!=null && codigoRol==3){
                mostrarEspecialidad=true;
                especialidades = especilidadDAO.findAll();
            }else{
                especialidades = new ArrayList<CitEspecialidad>();
                mostrarEspecialidad=false;
                codigoEsp = null;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void cargarDependencias() {
        try {
            listaUsuarioAplicacion = usuarioAplicacionDao.findAll();
            for (FacUsuarioAplicacion item : listaUsuarioAplicacion) {
                item.setUsuCodigo(usuarioDAO.find(item.getUsuCodigo().getUsuCodigo().intValue()));
                item.setRolCodigo(rolDAO.find(item.getRolCodigo().getRolCodigo().intValue()));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void create(ActionEvent actionEvent) {
        try {
            boolean existeUsu=false;
            Long codigoUsu= new Long(0);
            if (ValidadorCedulaRuc.isCedulaValido(usuario.getUsuIdentificacion())) {
                usuarioRol = usuarioDAO.findXCedula(usuario.getUsuIdentificacion());
            }else {
                saveMessageErrorDetail("Usuario", "La cedula es incorrecta");
            }
            
            //si existen suusarios creados con esa cedula validamos si tienen el rol creado si no para crearlos
            if(usuarioRol!=null && usuarioRol.size()>0){
                for (FacUsuario usu : usuarioRol) {
                     if(usu.getRol().equalsIgnoreCase(rolDAO.find(codigoRol).getRolNombre())){
                         existeUsu=true;
                         codigoUsu=usu.getUsuCodigo();
                     }
                 }
                if(existeUsu==true){
                    usuarioDAO.update(usuario);
                    usuarioAplicacion.setUsuCodigo(usuarioDAO.find(codigoUsu.intValue()));
                    usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                    usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                    usuarioAplicacionDao.update(usuarioAplicacion);
                    cargarDependencias();
                    saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " modificado correctamente");
                    this.inicializar(actionEvent);
                         saveMessageErrorDetail("Usuario", "Ya Existe Usuario con el rol seleccionado ");
                     }else{
                            usuarioDAO.update(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
                            usuarioAplicacion.setUsuCodigo(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
                            usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                            usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                            usuarioAplicacionDao.save(usuarioAplicacion);
                            cargarDependencias();
                            saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " modificado correctamente");
                            this.inicializar(actionEvent);
                     }
            }else if (usuario.getUsuCodigo() == null) {
                if (!usuarioDAO.existePorCampo(usuario.getUsuLogin())) {
                    if (ValidadorCedulaRuc.isCedulaValido(usuario.getUsuIdentificacion())) {
                        if (usuario.getUsuClave().equals(confirmarClave)) {
                            if(codigoEsp!=null && codigoEsp>0){
                            usuario.setCitEspecialidad(especilidadDAO.find(codigoEsp));
                            }
                            int idUsuario = usuarioDAO.save(usuario);
                            usuarioAplicacion.setUsuCodigo(usuarioDAO.find(idUsuario));
                            usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                            usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                            usuarioAplicacionDao.save(usuarioAplicacion);
                            cargarDependencias();
                            saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " creado correctamente");
                            this.inicializar(actionEvent);
                        } else {
                            saveMessageErrorDetail("Usuario", "La clave no coinside");
                        }
                    } else {
                        saveMessageErrorDetail("Usuario", "La cedula es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " ya existe");
                }
            } else if (usuario.getUsuCodigo() != null) {
                usuarioDAO.update(usuario);
                usuarioAplicacion.setUsuCodigo(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
                usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
                usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
                usuarioAplicacionDao.update(usuarioAplicacion);
                cargarDependencias();
                saveMessageInfoDetail("Usuario", "Usuario " + usuario.getUsuLogin() + " modificado correctamente");
                this.inicializar(actionEvent);
            }
        } catch (SQLException e) {
            saveMessageErrorDetail("Usuario", e.getMessage());
            LOG.error(e.getMessage(), e);
        }

    }

    public void edit(ActionEvent event) {
        usuarioAplicacion = new FacUsuarioAplicacion();
        usuario = new FacUsuario();
        try {
            usuarioAplicacion = (FacUsuarioAplicacion) event.getComponent().getAttributes().get("objetoEditar");
            usuario = usuarioAplicacion.getUsuCodigo();
            codigoRol = usuarioAplicacion.getRolCodigo().getRolCodigo().intValue();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void remove(ActionEvent event) {
        usuarioAplicacion = new FacUsuarioAplicacion();
        try {
            usuarioAplicacion = (FacUsuarioAplicacion) event.getComponent().getAttributes().get("objetoEliminar");
            int ndel = usuarioAplicacionDao.delete(usuarioAplicacion.getUapCodigo().intValue());
            if (ndel > 0) {
                usuarioDAO.delete(usuarioAplicacion.getUsuCodigo().getUsuCodigo().intValue());
                cargarDependencias();
                this.inicializar(event);
                saveMessageInfoDetail("Usuario", "Usuario " + usuarioAplicacion.getUsuCodigo().getUsuLogin() + " eliminado correctamente");
            } else {
                saveMessageErrorDetail("Usuario", "Usuario " + usuarioAplicacion.getUsuCodigo().getUsuLogin() + " error al eliminar");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public FacUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(FacUsuario usuario) {
        this.usuario = usuario;
    }

    public RolDao getRolDAO() {
        return rolDAO;
    }

    public void setRolDAO(RolDao rolDAO) {
        this.rolDAO = rolDAO;
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

    public List<FacRol> getRoles() {
        return roles;
    }

    public void setRoles(List<FacRol> roles) {
        this.roles = roles;
    }

    public List<FacUsuarioAplicacion> getListaUsuarioAplicacion() {
        return listaUsuarioAplicacion;
    }

    public void setListaUsuarioAplicacion(List<FacUsuarioAplicacion> listaUsuarioAplicacion) {
        this.listaUsuarioAplicacion = listaUsuarioAplicacion;
    }

    public List<CitEspecialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<CitEspecialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public EspecialidadDao getEspecilidadDAO() {
        return especilidadDAO;
    }

    public void setEspecilidadDAO(EspecialidadDao especilidadDAO) {
        this.especilidadDAO = especilidadDAO;
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

}
