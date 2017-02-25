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

public class CitAntPersonales implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long antPerCodigo;
        
    private CitPaciente pacCodigo;
    
    private Integer numHijos;
    
    private Integer numAbortos;
    
    private String enfInfancia;
    
    private String quirurgicos;
      
    private String alergias;
    
    private String vih;
    
    private Integer edadMenarquia;
    
    private String ritmoMenstrual;
    
    private Date fechaUltMesnstruacion;
    
    private String antTraumaticas;
    
    private String hospitalizacionAnteriores;
    
    private String adicciones;
    
    private String otros;

    public CitAntPersonales() {
    }

    public CitAntPersonales(Long antPerCodigo) {
        this.antPerCodigo = antPerCodigo;
    }

    public CitAntPersonales(Long antPerCodigo, Integer numHijos, Integer numAbortos, String enfInfancia, String quirurgicos, 
            String alergias, String vih, Integer edadMenarquia, String ritmoMenstrual, Date fechaUltMesnstruacion, 
            String antTraumaticas, String hospitalizacionAnteriores, String adicciones, String otros) {
        this.antPerCodigo = antPerCodigo;
        this.numHijos=numHijos;
        this.numAbortos=numAbortos;
        this.enfInfancia=enfInfancia;
        this.quirurgicos=quirurgicos;
        this.alergias=alergias;
        this.vih=vih;
        this.edadMenarquia=edadMenarquia;
        this.ritmoMenstrual=ritmoMenstrual;
        this.fechaUltMesnstruacion=fechaUltMesnstruacion;    
        this.antTraumaticas=antTraumaticas;    
        this.hospitalizacionAnteriores=hospitalizacionAnteriores;
        this.adicciones=adicciones;
        this.otros=otros;
    }

    public Long getAntPerCodigo() {
        return antPerCodigo;
    }

    public void setAntPerCodigo(Long antPerCodigo) {
        this.antPerCodigo = antPerCodigo;
    }

    public CitPaciente getPacCodigo() {
        return pacCodigo;
    }

    public void setPacCodigo(CitPaciente pacCodigo) {
        this.pacCodigo = pacCodigo;
    }

    public Integer getNumHijos() {
        return numHijos;
    }

    public void setNumHijos(Integer numHijos) {
        this.numHijos = numHijos;
    }

    public Integer getNumAbortos() {
        return numAbortos;
    }

    public void setNumAbortos(Integer numAbortos) {
        this.numAbortos = numAbortos;
    }

    public String getEnfInfancia() {
        return enfInfancia;
    }

    public void setEnfInfancia(String enfInfancia) {
        this.enfInfancia = enfInfancia;
    }

    public String getQuirurgicos() {
        return quirurgicos;
    }

    public void setQuirurgicos(String quirurgicos) {
        this.quirurgicos = quirurgicos;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getVih() {
        return vih;
    }

    public void setVih(String vih) {
        this.vih = vih;
    }

    public Integer getEdadMenarquia() {
        return edadMenarquia;
    }

    public void setEdadMenarquia(Integer edadMenarquia) {
        this.edadMenarquia = edadMenarquia;
    }


    public String getRitmoMenstrual() {
        return ritmoMenstrual;
    }

    public void setRitmoMenstrual(String ritmoMenstrual) {
        this.ritmoMenstrual = ritmoMenstrual;
    }

    public Date getFechaUltMesnstruacion() {
        return fechaUltMesnstruacion;
    }

    public void setFechaUltMesnstruacion(Date fechaUltMesnstruacion) {
        this.fechaUltMesnstruacion = fechaUltMesnstruacion;
    }

    public String getAntTraumaticas() {
        return antTraumaticas;
    }

    public void setAntTraumaticas(String antTraumaticas) {
        this.antTraumaticas = antTraumaticas;
    }

    public String getHospitalizacionAnteriores() {
        return hospitalizacionAnteriores;
    }

    public void setHospitalizacionAnteriores(String hospitalizacionAnteriores) {
        this.hospitalizacionAnteriores = hospitalizacionAnteriores;
    }

    public String getAdicciones() {
        return adicciones;
    }

    public void setAdicciones(String adicciones) {
        this.adicciones = adicciones;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (antPerCodigo != null ? antPerCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitAntPersonales)) {
            return false;
        }
        CitAntPersonales other = (CitAntPersonales) object;
        if ((this.antPerCodigo == null && other.antPerCodigo != null) || (this.antPerCodigo != null && !this.antPerCodigo.equals(other.antPerCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.facturacion.entity.FacCabeceraFactura[ cabCodigo=" + antPerCodigo + " ]";
    }
    
}
