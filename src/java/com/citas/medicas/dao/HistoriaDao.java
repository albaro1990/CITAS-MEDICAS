package com.citas.medicas.dao;
import com.citas.medicas.entity.CitHistoriaClinica;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface HistoriaDao {

    public int save(CitHistoriaClinica historia) throws SQLException;

    public int update(CitHistoriaClinica historia) throws SQLException;

    public int delete(int id) throws SQLException;

    public List<CitHistoriaClinica> findAll() throws SQLException;

    public CitHistoriaClinica find(int id) throws SQLException;
    
    public int cacelar(int id) throws SQLException;
    
    public List<CitHistoriaClinica> findAllXPaciente(Long codigoMedico) throws SQLException;
    
    public boolean existeCita(Long codigoDoc, Date fechaCita, Date horaCita) throws SQLException;
    
    public List<CitHistoriaClinica> findAllXCed(String cedula) throws SQLException;
}
