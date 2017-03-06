/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.entity;

import java.io.Serializable;

/**
 *
 * @author
 */

public class CitAntFamiliares implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Long antFamCodigo;
    
    private CitPaciente antFamPacCodigo;
    
    private String hepatopatia;
    
    private String alergias;
    
    private String asma;
      
    private String hipertension;
    
    private String cardipatia;
    
    private String nefropatia;
    
    private String cancer;
    
    private String otros;
    

    public CitAntFamiliares() {
    }

    public CitAntFamiliares(Long antFamCodigo) {
        this.antFamCodigo = antFamCodigo;
    }

    public CitAntFamiliares(Long antFamCodigo, String hepatopatia, String alergias, String asma, String hipertension,
            String cardipatia, String nefropatia, String cancer, String otros) {
        this.antFamCodigo=antFamCodigo;
        this.hepatopatia=hepatopatia;
        this.alergias=alergias;
        this.asma=asma;
        this.hipertension=hipertension;
        this.cardipatia=cardipatia;
        this.nefropatia=nefropatia;
        this.cancer=cancer;
        this.otros=otros;
       }

    public Long getAntFamCodigo() {
        return antFamCodigo;
    }

    public void setAntFamCodigo(Long antFamCodigo) {
        this.antFamCodigo = antFamCodigo;
    }

    public CitPaciente getAntFamPacCodigo() {
        return antFamPacCodigo;
    }

    public void setAntFamPacCodigo(CitPaciente antFamPacCodigo) {
        this.antFamPacCodigo = antFamPacCodigo;
    }

    public String getHepatopatia() {
        return hepatopatia;
    }

    public void setHepatopatia(String hepatopatia) {
        this.hepatopatia = hepatopatia;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getHipertension() {
        return hipertension;
    }

    public void setHipertension(String hipertension) {
        this.hipertension = hipertension;
    }

    public String getCardipatia() {
        return cardipatia;
    }

    public void setCardipatia(String cardipatia) {
        this.cardipatia = cardipatia;
    }

    public String getNefropatia() {
        return nefropatia;
    }

    public void setNefropatia(String nefropatia) {
        this.nefropatia = nefropatia;
    }

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
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
        hash += (antFamCodigo != null ? antFamCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitAntFamiliares)) {
            return false;
        }
        CitAntFamiliares other = (CitAntFamiliares) object;
        if ((this.antFamCodigo == null && other.antFamCodigo != null) || (this.antFamCodigo != null && !this.antFamCodigo.equals(other.antFamCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.citas.medicas.entity.CitAntFamiliares[ antFamCodigo=" + antFamCodigo + " ]";
    }
    
}
