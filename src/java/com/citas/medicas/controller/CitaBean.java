/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.ArticuloDao;
import com.citas.medicas.dao.ArticuloDetalleDao;
import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.dao.DetalleFacturaDao;
import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.dao.UsuarioDao;
import com.citas.medicas.dao.impl.ArticuloDaoImpl;
import com.citas.medicas.dao.impl.ArticuloDetalleDaoImpl;
import com.citas.medicas.dao.impl.CitaDaoImpl;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.dao.impl.ClienteDaoImpl;
import com.citas.medicas.dao.impl.DetalleFacturaDaoImpl;
import com.citas.medicas.dao.impl.EspecialidadDaoImpl;
import com.citas.medicas.dao.impl.UsuarioDaoImpl;
import com.citas.medicas.entity.FacArticulo;
import com.citas.medicas.entity.FacArticuloDetalle;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitEspecialidad;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacCiudad;
import com.citas.medicas.entity.FacDetalleFactura;
import com.citas.medicas.entity.FacUsuario;
import com.citas.medicas.utilitarios.Utils;
import com.citas.medicas.utilitarios.ValidadorCedulaRuc;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.citas.medicas.dao.CitaDao;

@ManagedBean(name = "citasBean")
@ViewScoped
public class CitaBean extends GenericBean {

    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CitaBean.class);

    private CitPaciente paciente;
    private CitPaciente clienteNuevo;
    private FacArticuloDetalle articuloDetalle;
    private CitCita cita;
    private CitEspecialidad especialidad;
    private List<CitCita> listaCitas;
    private List<FacArticulo> listaArticulos;
    private List<FacUsuario> listaUsuMedicos;
    private List<FacCiudad> ciudades = new ArrayList<FacCiudad>();
    private List<CitEspecialidad> especialidades = new ArrayList<CitEspecialidad>();
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();
    private CitaDao citaDao = new CitaDaoImpl();
    private DetalleFacturaDao detalleFacturaDao = new DetalleFacturaDaoImpl();
    private ArticuloDao articuloDao = new ArticuloDaoImpl();
    private UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private EspecialidadDao especilidadDAO = new EspecialidadDaoImpl();
    private ArticuloDetalleDao articuloDetalleDao = new ArticuloDetalleDaoImpl();
    private FacArticulo articuloSeleccionado;
    private FacDetalleFactura detalleFactura;
    private Integer codigoCiudad;
    private Integer codigoEsp;
    private Integer codigoMedico;
    private Integer codigoPaciente;
    
    public CitaBean() {
        try {
            
            
            especialidad = new CitEspecialidad();
            paciente = new CitPaciente();
            clienteNuevo = new CitPaciente();
            articuloDetalle = new FacArticuloDetalle();
            detalleFactura = new FacDetalleFactura();
            articuloSeleccionado = new FacArticulo();
            ciudades = ciudadDAO.findAll();
            listaCitas = citaDao.findAll();
            cita = new CitCita();
 
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

       /* listaArticulos = new ArrayList<>();
        listaUsuMedicos = new ArrayList<>();
        listaCitas = new ArrayList<>();*/
        this.cargarCombos();
    }

    public void inicializar(ActionEvent actionEvent) {
        try {
            paciente = new CitPaciente();
            cita = new CitCita();
//            cita.setCabFechaCreacion(new Date());
//            cita.setCabAutorizacion(String.valueOf(Random.class.newInstance().nextInt()));
            detalleFactura = new FacDetalleFactura();
             listaCitas = new ArrayList<>();
            listaArticulos = new ArrayList<>();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void procesarArticulo() {
//        cita.setCabSubtotal(BigDecimal.ZERO);
//        cita.setCabTotal(BigDecimal.ZERO);
//        cita.setCabIva(BigDecimal.ZERO);
        Double subtotal = new Double(0);
        try {
            if (!listaCitas.isEmpty()) {
                for (CitCita item :  listaCitas) {
                   /* if (item.getDetCatidad() != null) {
                        FacArticulo art = articuloDao.find(item.getArtCodigo().getArtCodigo());
                        if (art != null) {
                            if (item.getDetCatidad() <= art.getArtCantidadIngresada().intValue()) {
                                if (item.getDetValorUnitario().doubleValue() > item.getArtCodigo().getArtValorUnitario().doubleValue()) {
                                    item.setDetSubtotal(item.getDetValorUnitario().multiply(BigDecimal.valueOf(item.getDetCatidad())));
                                    BigDecimal i = item.getDetValorUnitario().multiply(BigDecimal.valueOf(item.getDetCatidad()));
                                    subtotal += i.doubleValue();
                                } else {
                                    saveMessageErrorDetail("Artículo", "El Artículo " + item.getArtCodigo().getArtDescripcion() + "debe tener un valor unitario mayor " + item.getArtCodigo().getArtValorUnitario().doubleValue());
                                }
                            } else {
                                saveMessageErrorDetail("Artículo", "El Artículo " + item.getArtCodigo().getArtDescripcion() + " no tiene la cantidad suficiente para la venta");
                            }
                        } else {
                        }
                    } else {
                    }*/
                }
//                cita.setCabSubtotal(BigDecimal.valueOf(Utils.redondearNumero(BigDecimal.valueOf(subtotal).divide(BigDecimal.valueOf(1.14), 2, BigDecimal.ROUND_HALF_UP), 2, true)));
//                cita.setCabIva(BigDecimal.valueOf(Utils.redondearNumero(cita.getCabSubtotal().multiply(BigDecimal.valueOf(0.14)), 2, true)));
//                cita.setCabTotal(cita.getCabSubtotal().add(cita.getCabIva()));
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    public void cargarCombos() {
        try {
            especialidades = especilidadDAO.findAll();
            ciudades = ciudadDAO.findAll();
            if(codigoEsp==null){
                codigoEsp=0;
            }
            listaUsuMedicos = usuarioDao.findDoctoresXEsp(codigoEsp);
            listaCitas = citaDao.findAll();
            for (CitCita item : listaCitas) {
                item.setCliCodigo(clienteDao.findXId(item.getCliCodigo().getPacCodigo().intValue()));
                item.setUsuario(usuarioDao.find(item.getUsuario().getUsuCodigo().intValue()));
                item.getUsuario().setCitEspecialidad(especilidadDAO.find(item.getUsuario().getCitEspecialidad().getEspCodigo().intValue()));
                
            }
            //listaArticulos = articuloDao.findAll();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void addArticulo(ActionEvent actionEvent) {
        listaArticulos = new ArrayList<>();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (paciente.getPacIdentificacin() != null) {
                cargarCombos();
                requestContext.execute("PF('dlListaArticulo').show()");
            } else {
                agregarDialogMensaje(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, "Factura", "Ingresar cliente", RequestContext.getCurrentInstance());
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void addCliente(ActionEvent actionEvent) {
        clienteNuevo = new CitPaciente();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            requestContext.execute("PF('dlListaCliente').show()");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }


    public void create(ActionEvent actionEvent) {
        try {
            //VALIDAMOS SI EXISTE UNA CITA MEDICA PARA ESE DOCTOR EN EL MISMO DIA Y A LA MISMA HORA CON MAS 30 MIN
            boolean existeCita=citaDao.existeCita(codigoMedico.longValue(), cita.getCitFechaCita(), cita.getHoraCita());
//            if (cita.getCitCodigo().intValue() > 0) {
        if(existeCita==false){
                if (cita.getCitCodigo() == null) {
                    cita.setCliCodigo(paciente);
                    cita.setUsuario(usuarioDao.find(codigoMedico));
                    cita.setCitEstado(1);
                    int idc = citaDao.save(cita);
                    if (idc > 0) {
//                        for (FacDetalleFactura item : listaDetalleFacturas) {
//                            item.setDetAplicaIva(1);
//                            item.setCabCodigo(cabeceraFacturaDao.find(idc));
//                            detalleFacturaDao.save(item);
//                        }
//                        saveMessageInfoDetail("Factura", "La factura se creo correctamente");
//                        createKardex(listaDetalleFacturas);
                        inicializar(actionEvent);
                    } else {
                        saveMessageWarnDetail("Cita", "Error al crear la factura");
                    }
                } else if (cita.getCitCodigo() != null) {
                citaDao.update(cita);
                cargarCombos();
//                usuarioAplicacion.setUsuCodigo(usuarioDAO.find(usuario.getUsuCodigo().intValue()));
//                usuarioAplicacion.setRolCodigo(rolDAO.find(codigoRol));
//                usuarioAplicacion.setUapEstado(usuario.getUsuEstado());
//                usuarioAplicacionDao.update(usuarioAplicacion);
//                cargarDependencias();
                saveMessageInfoDetail("Cita", "Cita " + cita.getCitCodigo() + " modificado correctamente");
                this.inicializar(actionEvent);
            }
            } else {
                saveMessageWarnDetail("Cita", "No existe citas disponibles para ese doctor en ese rango de tiempo");
                saveMessageWarnDetail("Cita", "La cita debe ser mayor a la fecha y hora actual en al menos 30 min");
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void createKardex(List<FacDetalleFactura> listaDetalleFacturas) {
        try {
            if (!listaDetalleFacturas.isEmpty()) {
                for (FacDetalleFactura item : listaDetalleFacturas) {
                    articuloDetalle.setFacArticulo(articuloDao.find(item.getArtCodigo().getArtCodigo()));
                    articuloDetalle.setArtCantidadIgresada(null);
                    articuloDetalle.setArtCantidadSaliente(BigDecimal.valueOf(item.getDetCatidad()));
                    articuloDetalle.setArtValorUnitario(item.getDetValorUnitario());
//                    articuloDetalle.setArtAutorizacion(item.getCabCodigo().getCabAutorizacion());
                    articuloDetalle.setArtSaldo(item.getArtCodigo().getArtCantidadIngresada().subtract(BigDecimal.valueOf(item.getDetCatidad())));
                    articuloDetalleDao.save(articuloDetalle);
                    item.getArtCodigo().setArtCantidadIngresada(articuloDetalle.getArtSaldo());
                    articuloDao.update(item.getArtCodigo());
                }
            }
        } catch (Exception e) {
        }
    }

    public void createCliente(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            if (clienteNuevo.getPacCodigo() == null) {
                if (!clienteDao.existePorCampo(clienteNuevo.getPacIdentificacin())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(clienteNuevo.getPacIdentificacin())) {
                        clienteNuevo.setPacEstado(1);
                        clienteNuevo.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                        int idc = clienteDao.save(clienteNuevo);
                        if (idc > 0) {
                            paciente.setPacIdentificacin(clienteNuevo.getPacIdentificacin());
                            saveMessageInfoDetail("Paciente", "Paciente " + clienteNuevo.getPacIdentificacin() + " creado correctamente");
                            requestContext.execute("PF('dlListaCliente').hide()");
                        }
                    } else {
                        saveMessageErrorDetail("Paciente", "La cédula o ruc es incorrecta");
                    }
                } else {
                    saveMessageErrorDetail("Paciente", "Paciente " + clienteNuevo.getPacIdentificacin() + " ya existe");
                }
            }

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    public void findCliente(ActionEvent actionEvent) {
        try {
            if (paciente.getPacIdentificacin() != null) {
                if (ValidadorCedulaRuc.isRucCedulaValido(paciente.getPacIdentificacin().trim())) {
                    paciente = clienteDao.find(paciente.getPacIdentificacin());
                    if (paciente != null) {
                        saveMessageInfoDetail("Paciente", "Paciente encontrado con exito");
                    } else {
                        paciente = new CitPaciente();
                        saveMessageWarnDetail("Paciente", "El cliente no existe");
                    }
                } else {
                    paciente = new CitPaciente();
                    saveMessageWarnDetail("Paciente", "El ruc o la cédula es incorrecta");
                }
            }
        } catch (Exception e) {
        }

    }

    public void edit(ActionEvent event) {
        cita = new CitCita();
        especialidad = new CitEspecialidad();
        try {
            cita = (CitCita) event.getComponent().getAttributes().get("objetoEditar");
            especialidad = cita.getUsuario().getCitEspecialidad();
            codigoEsp = cita.getUsuario().getCitEspecialidad().getEspCodigo().intValue();
            codigoMedico = cita.getUsuario().getUsuCodigo().intValue();
            paciente = clienteDao.findXId(cita.getCliCodigo().getPacCodigo().intValue());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public void remove(ActionEvent event) {
        cita = new CitCita();
        try {
            cita = (CitCita) event.getComponent().getAttributes().get("objetoRemover");
           int ndel = citaDao.cacelar(cita.getCitCodigo().intValue());
            if (detalleFactura != null) {
              //  listaDetalleFacturas.remove(detalleFactura);
                procesarArticulo();
            }
        } catch (Exception e) {
        }

    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente cliente) {
        this.paciente = cliente;
    }

    public CitCita getCita() {
        return cita;
    }

    public void setCita(CitCita cita) {
        this.cita = cita;
    }

    public List<FacArticulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<FacArticulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public FacArticulo getArticuloSeleccionado() {
        return articuloSeleccionado;
    }

    public void setArticuloSeleccionado(FacArticulo articuloSeleccionado) {
        this.articuloSeleccionado = articuloSeleccionado;
    }

    public CitPaciente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(CitPaciente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public List<FacCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<FacCiudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public List<FacUsuario> getListaUsuMedicos() {
        return listaUsuMedicos;
    }

    public void setListaUsuMedicos(List<FacUsuario> listaUsuMedicos) {
        this.listaUsuMedicos = listaUsuMedicos;
    }

    public CitaDao getCabeceraFacturaDao() {
        return citaDao;
    }

    public void setCabeceraFacturaDao(CitaDao citaDao) {
        this.citaDao = citaDao;
    }

    public DetalleFacturaDao getDetalleFacturaDao() {
        return detalleFacturaDao;
    }

    public void setDetalleFacturaDao(DetalleFacturaDao detalleFacturaDao) {
        this.detalleFacturaDao = detalleFacturaDao;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public FacDetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(FacDetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public Integer getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(Integer codigoMedico) {
        this.codigoMedico = codigoMedico;
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

    public Integer getCodigoEsp() {
        return codigoEsp;
    }

    public void setCodigoEsp(Integer codigoEsp) {
        this.codigoEsp = codigoEsp;
    }

    public List<CitCita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<CitCita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public Integer getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(Integer codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

}
