/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.controller;

import com.citas.medicas.dao.AntFamiliaresDao;
import com.citas.medicas.dao.AntPersonalesDao;
import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.dao.UsuarioDao;
import com.citas.medicas.dao.impl.CitaDaoImpl;
import com.citas.medicas.dao.impl.CiudadDaoImpl;
import com.citas.medicas.dao.impl.ClienteDaoImpl;
import com.citas.medicas.dao.impl.EspecialidadDaoImpl;
import com.citas.medicas.dao.impl.UsuarioDaoImpl;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitEspecialidad;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacCiudad;
import com.citas.medicas.entity.FacUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
import com.citas.medicas.dao.impl.AntFamiliaresDaoImpl;
import com.citas.medicas.dao.impl.AntPersonalesDaoImpl;
import com.citas.medicas.entity.CitAntFamiliares;
import com.citas.medicas.entity.CitAntPersonales;
import com.citas.medicas.utilitarios.ValidadorCedulaRuc;
import java.util.Calendar;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean(name = "scheduleView")
@SessionScoped
public class ScheduleView extends GenericBean {
    
    private static final long serialVersionUID = 1L;
    private final Logger LOG = LoggerFactory.getLogger(CitaBean.class);
    
    private List<CitCita> listaCitas;
    
    private CitaDao citaDao = new CitaDaoImpl();
    private AntFamiliaresDao antFamiliaresDao = new AntFamiliaresDaoImpl();
    private AntPersonalesDao antPersonalesDao = new AntPersonalesDaoImpl();
    
    private ScheduleModel eventModel;
     
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private CitPaciente paciente;
    private CitPaciente clienteNuevo;

    private CitCita cita;
    private CitEspecialidad especialidad;
    private CitAntPersonales antPersonales;
    private CitAntFamiliares antFamiliares;


    private List<FacUsuario> listaUsuMedicos;
    private List<FacCiudad> ciudades = new ArrayList<FacCiudad>();
    private List<CitEspecialidad> especialidades = new ArrayList<CitEspecialidad>();
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private CiudadDao ciudadDAO = new CiudadDaoImpl();

    private UsuarioDao usuarioDao = new UsuarioDaoImpl();
    private EspecialidadDao especilidadDAO = new EspecialidadDaoImpl();
    private Integer codigoCiudad;
    private Integer codigoEsp;
    private Integer codigoMedico;
    private Integer codigoPaciente;
    private Integer codigoCita;
    
 
     public ScheduleView() {
            
        
    }
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        try {
          listaCitas=   citaDao.findAllXMedico();
            for (CitCita listaCita : listaCitas) {                
                 eventModel.addEvent(new DefaultScheduleEvent("CHEQUEO DE RUTINA",listaCita.getCitFechaCita(),listaCita.getHoraCita(),listaCita));
                 
            }
            
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ScheduleView.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        lazyEventModel = new LazyScheduleModel() {
             
            @Override
            public void loadEvents(Date start, Date end) {
                Date random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
                 
                random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
            }   
        };
    }
     
    public void addAntPersonales(ActionEvent actionEvent) {
       
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
          
                requestContext.execute("PF('dlAntPersonales').show()");
            
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    
    
    public void addAntFamiliares(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            
                requestContext.execute("PF('dlAntFamiliares').show()");
         
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
         
        return t.getTime();
    }
     
    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);
         
        return t.getTime();
    }
     
    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);     
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
 
    private Date today6Pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);
         
        return t.getTime();
    }
     
    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);
         
        return t.getTime();
    }
     
    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        setCita((CitCita) event.getData());
        codigoPaciente = getCita().getCliCodigo().getPacCodigo().intValue();
        try {
           paciente =  clienteDao.findXId(codigoPaciente); 
        } catch (Exception e) {
        }
        
    }
    
     public void onEventIngresar(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        FacesContext context = FacesContext.getCurrentInstance();
        HistoriaClinicaBean historiaBean= context.getApplication().evaluateExpressionGet(context, "#{historiaClinicaBean}", HistoriaClinicaBean.class);
        historiaBean.setCita((CitCita) event.getData());
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(), (CitCita) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
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
//            if (detalleFactura != null) {
//              //  listaDetalleFacturas.remove(detalleFactura);
//                procesarArticulo();
//            }
        } catch (Exception e) {
        }

    }
    
     public void createAntPersonales(ActionEvent actionEvent) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            antPersonales.setPacCodigo(paciente);               
            antPersonalesDao.save(antPersonales);
            if (clienteNuevo.getPacCodigo() == null) {
                if (!clienteDao.existePorCampo(clienteNuevo.getPacIdentificacin())) {
                    if (ValidadorCedulaRuc.isRucCedulaValido(clienteNuevo.getPacIdentificacin())) {
                        clienteNuevo.setPacEstado(1);
                        clienteNuevo.setCodigoCiudad(ciudadDAO.find(codigoCiudad));
                        int idc = clienteDao.save(clienteNuevo);
                        if (idc > 0) {
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


    public List<CitCita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<CitCita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public CitaDao getCitaDao() {
        return citaDao;
    }

    public void setCitaDao(CitaDao citaDao) {
        this.citaDao = citaDao;
    }

    public CitPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(CitPaciente paciente) {
        this.paciente = paciente;
    }

    public CitPaciente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(CitPaciente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public CitCita getCita() {
        return cita;
    }

    public void setCita(CitCita cita) {
        this.cita = cita;
    }

    public CitEspecialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(CitEspecialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<FacUsuario> getListaUsuMedicos() {
        return listaUsuMedicos;
    }

    public void setListaUsuMedicos(List<FacUsuario> listaUsuMedicos) {
        this.listaUsuMedicos = listaUsuMedicos;
    }

    public List<FacCiudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<FacCiudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<CitEspecialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<CitEspecialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public CiudadDao getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDao ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public EspecialidadDao getEspecilidadDAO() {
        return especilidadDAO;
    }

    public void setEspecilidadDAO(EspecialidadDao especilidadDAO) {
        this.especilidadDAO = especilidadDAO;
    }

    public Integer getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Integer codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public Integer getCodigoEsp() {
        return codigoEsp;
    }

    public void setCodigoEsp(Integer codigoEsp) {
        this.codigoEsp = codigoEsp;
    }

    public Integer getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(Integer codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public Integer getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(Integer codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public Integer getCodigoCita() {
        return codigoCita;
    }

    public void setCodigoCita(Integer codigoCita) {
        this.codigoCita = codigoCita;
    }

    public CitAntPersonales getAntPersonales() {
        return antPersonales;
    }

    public void setAntPersonales(CitAntPersonales antPersonales) {
        this.antPersonales = antPersonales;
    }

    public CitAntFamiliares getAntFamiliares() {
        return antFamiliares;
    }

    public void setAntFamiliares(CitAntFamiliares antFamiliares) {
        this.antFamiliares = antFamiliares;
    }
    
    
}
