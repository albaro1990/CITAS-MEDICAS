package com.citas.medicas.dao;

import com.citas.medicas.entity.FacUsuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao extends Serializable{

    public int save(FacUsuario usuario) throws SQLException;

    public int update(FacUsuario usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<FacUsuario> findAll() throws SQLException;

    public FacUsuario find(int id) throws SQLException;
    
    public boolean existePorCampo(String username) throws SQLException;
    
    public Long nuevoCodigo() throws SQLException;
    
    public List<FacUsuario> findDoctoresXEsp(int codEspecialidad) throws SQLException; 
    
    public List<FacUsuario> findXCedula(String cedula) throws SQLException;
}
