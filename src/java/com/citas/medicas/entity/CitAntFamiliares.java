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

public class CitAntFamiliares implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long hisCodigo;
    
    private FacUsuario usuario;
    
    private CitPaciente cliCodigo;
    
    private Date citFechaCita;
    
    private Date horaCita;
    
    private Integer citEstado;
      
    private String citMotivo;
    

    public CitAntFamiliares() {
    }

    public CitAntFamiliares(Long hisCodigo) {
        this.hisCodigo = hisCodigo;
    }

    public CitAntFamiliares(Long hisCodigo, Date citFechaCita, Integer citEstado, String citMotivo) {
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



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisCodigo != null ? hisCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitAntFamiliares)) {
            return false;
        }
        CitAntFamiliares other = (CitAntFamiliares) object;
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
