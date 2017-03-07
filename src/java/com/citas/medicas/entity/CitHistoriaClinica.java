/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */

public class CitHistoriaClinica implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long hisCodigo;
    
    private FacUsuario usuario;
    
    private CitPaciente cliCodigo;
    
    private Date citFechaCita;
    
    private Date horaCita;
    
    private Integer citEstado;
      
    private String citMotivo;
    
    private Integer edad;
    
    private String talla;
    
    private String imc;
    
    private String temperatura;
    
    private String ritmoCardiaco;
    
    private String presion;
    
    private String peso;
    
    private String tipoSangre;
    
    private String sintomas;
    
    private Date fechaAtencion;
    
    private String tratamiento;
    

    public CitHistoriaClinica() {
    }

    public CitHistoriaClinica(Long hisCodigo) {
        this.hisCodigo = hisCodigo;
    }

    public CitHistoriaClinica(Long hisCodigo, Date citFechaCita, Integer citEstado, String citMotivo) {
        this.hisCodigo = hisCodigo;
        this.citFechaCita = citFechaCita;
        this.citEstado = citEstado;
        this.citMotivo = citMotivo;

    }

    public Long getHisCodigo() {
        return hisCodigo;
    }

    public void setHisCodigo(Long hisCodigo) {
        this.hisCodigo = hisCodigo;
    }

    public FacUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(FacUsuario usuario) {
        this.usuario = usuario;
    }

    public Date getCitFechaCita() {
        return citFechaCita;
    }

    public void setCitFechaCita(Date citFechaCita) {
        this.citFechaCita = citFechaCita;
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

    public Integer getCitEstado() {
        return citEstado;
    }

    public void setCitEstado(Integer citEstado) {
        this.citEstado = citEstado;
    }

    public String getCitMotivo() {
        return citMotivo;
    }

    public void setCitMotivo(String citMotivo) {
        this.citMotivo = citMotivo;
    }


    public CitPaciente getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(CitPaciente cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public void setRitmoCardiaco(String ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisCodigo != null ? hisCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitHistoriaClinica)) {
            return false;
        }
        CitHistoriaClinica other = (CitHistoriaClinica) object;
        if ((this.hisCodigo == null && other.hisCodigo != null) || (this.hisCodigo != null && !this.hisCodigo.equals(other.hisCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + hisCodigo + " ]";
    }
    
}
