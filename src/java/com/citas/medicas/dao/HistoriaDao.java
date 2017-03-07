package com.citas.medicas.dao;

import com.citas.medicas.entity.CitCita;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface HistoriaDao {

    public int save(CitCita factura) throws SQLException;

    public int update(CitCita usuario) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<CitCita> findAll() throws SQLException;

    public CitCita find(int id) throws SQLException;
    
    public int cacelar(int id) throws SQLException;
    
    public List<CitCita> findAllXMedico(Long codigoMedico) throws SQLException;
    
    public boolean existeCita(Long codigoDoc, Date fechaCita, Date horaCita) throws SQLException;
}
