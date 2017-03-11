package com.citas.medicas.dao;

import com.citas.medicas.entity.CitAntFamiliares;
import com.citas.medicas.entity.CitCita;
import java.sql.SQLException;
import java.util.List;

public interface AntFamiliaresDao {

    public int save(CitAntFamiliares citAntFamiliares) throws SQLException;

    public int update(CitAntFamiliares citAntFamiliares) throws SQLException;

    public int delete(int id) throws SQLException;

    public CitAntFamiliares findXIdPaciente(int id) throws SQLException;
    
    public CitAntFamiliares findXCedPaciente(String cedula) throws SQLException;
}
