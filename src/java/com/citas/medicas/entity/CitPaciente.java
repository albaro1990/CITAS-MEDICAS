/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citas.medicas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author 
 */

public class CitPaciente implements Serializable {

    private static final long serialVersionUID = 1L;
    


    
    private Long pacCodigo;
    private FacCiudad facCiudad;
    private String pacNombres;
    private String pacApellidos;
    private String pacTelefono;
    private String pacDireccion;
    private String pacIdentificacin;
    private String pacCorreo;
    private Integer pacEstado;
    private String pacGenero;
    
    
    private List<FacCitaFactura> facCitaFacturaList;

    public CitPaciente() {
    }

    

    public CitPaciente(Long pacCodigo, String pacNombres, String pacIdentificacin, Integer pacEstado, String pacApellidos) {
        this.pacCodigo = pacCodigo;
        this.pacNombres = pacNombres;
        this.pacIdentificacin = pacIdentificacin;
        this.pacEstado = pacEstado;
        this.pacApellidos = pacApellidos;
    }

    public Long getPacCodigo() {
        return pacCodigo;
    }

    public void setPacCodigo(Long pacCodigo) {
        this.pacCodigo = pacCodigo;
    }

    public FacCiudad getFacCiudad() {
        return facCiudad;
    }

    public void setFacCiudad(FacCiudad facCiudad) {
        this.facCiudad = facCiudad;
    }

    public String getPacNombres() {
        return pacNombres;
    }

    public void setPacNombres(String pacNombres) {
        this.pacNombres = pacNombres;
    }

    public String getPacApellidos() {
        return pacApellidos;
    }

    public void setPacApellidos(String pacApellidos) {
        this.pacApellidos = pacApellidos;
    }

    public String getPacTelefono() {
        return pacTelefono;
    }

    public void setPacTelefono(String pacTelefono) {
        this.pacTelefono = pacTelefono;
    }

    public String getPacDireccion() {
        return pacDireccion;
    }

    public void setPacDireccion(String pacDireccion) {
        this.pacDireccion = pacDireccion;
    }

    public String getPacIdentificacin() {
        return pacIdentificacin;
    }

    public void setPacIdentificacin(String pacIdentificacin) {
        this.pacIdentificacin = pacIdentificacin;
    }

    public String getPacCorreo() {
        return pacCorreo;
    }

    public void setPacCorreo(String pacCorreo) {
        this.pacCorreo = pacCorreo;
    }

    public Integer getPacEstado() {
        return pacEstado;
    }

    public void setPacEstado(Integer pacEstado) {
        this.pacEstado = pacEstado;
    }

    public String getPacGenero() {
        return pacGenero;
    }

    public void setPacGenero(String pacGenero) {
        this.pacGenero = pacGenero;
    }

    public List<FacCitaFactura> getFacCitaFacturaList() {
        return facCitaFacturaList;
    }

    public void setFacCitaFacturaList(List<FacCitaFactura> facCitaFacturaList) {
        this.facCitaFacturaList = facCitaFacturaList;
    }

    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pacCodigo != null ? pacCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitPaciente)) {
            return false;
        }
        CitPaciente other = (CitPaciente) object;
        if ((this.pacCodigo == null && other.pacCodigo != null) || (this.pacCodigo != null && !this.pacCodigo.equals(other.pacCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.citas.entity.FacPaciente[ cliCodigo=" + pacCodigo + " ]";
    }
    
}
