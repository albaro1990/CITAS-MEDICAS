package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.CiudadDao;
import com.citas.medicas.dao.EspecialidadDao;
import com.citas.medicas.entity.CitEspecialidad;
import com.citas.medicas.entity.FacCiudad;
import com.citas.medicas.entity.FacRol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDaoImpl implements EspecialidadDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitEspecialidad especialidad) throws SQLException {
        int idInserted = 0;
        try {
            conn = new ConexionDB().getConexion();
           
            pstmt = conn.prepareStatement("INSERT INTO CIT_ESPECIALIDAD(ESP_CODIGO,ESP_DESCRIPCION,ESP_ESTADO) "
                    + "values (CIT_SEQ_ESPECIALIDAD.NEXTVAL, '"+especialidad.getEspDescripcion().toUpperCase()+"',"+especialidad.getEspEstado()+")", new String[]{"ESP_CODIGO"});
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la especialidad");
            }
            rs = pstmt.getGeneratedKeys();  
            if (rs.next()) {
                idInserted = rs.getInt(1);
            }

            //System.out.println("Se inserto : "+idInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return idInserted;
    }

    @Override
    public int update(CitEspecialidad especialidad) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE CIT_ESPECIALIDAD SET ESP_DESCRIPCION='"+especialidad.getEspDescripcion().toUpperCase()+"',ESP_ESTADO="+especialidad.getEspEstado()+""
                    + " WHERE ESP_CODIGO = "+especialidad.getEspCodigo()+" ");
            

            nup = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return nup;
    }

    @Override
    public int delete(int id) throws SQLException {
        int ndel = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("DELETE FROM CIT_ESPECIALIDAD WHERE ESP_CODIGO = "+id+"");
            ndel = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return ndel;
    }

    @Override
    public List<CitEspecialidad> findAll() throws SQLException {
        List<CitEspecialidad> especialidades = new ArrayList<CitEspecialidad>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_ESPECIALIDAD ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitEspecialidad especialidad = new CitEspecialidad();
                especialidad.setEspCodigo(rs.getLong(1));
                especialidad.setEspDescripcion(rs.getString(2));
                especialidad.setEspEstado(rs.getInt(3));
                especialidades.add(especialidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    @Override
    public CitEspecialidad find(int id) throws SQLException {

        CitEspecialidad especialidad = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_ESPECIALIDAD WHERE ESP_CODIGO = ? AND ESP_ESTADO=1");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                especialidad= new CitEspecialidad();
                especialidad.setEspCodigo(rs.getLong(1));
                especialidad.setEspDescripcion(rs.getString(2));
                especialidad.setEspEstado(rs.getInt(3));
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn.close();
            pstmt.close();
        }

        return especialidad;
    }
    
    @Override
    public boolean existePorCampo(String nombre) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM CIT_ESPECIALIDAD C WHERE C.ESP_DESCRIPCION='" + nombre + "'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("CONTADOR");
            }
            return rowCount > 0;
        } catch (SQLException e) {
            throw e;
        }finally {
            conn.close();
            pstmt.close();
        }
    }

    @Override
    public Long nuevoCodigo() throws SQLException {
        try {
            Long max = Long.valueOf(0);
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT MAX(C.CIU_CODIGO)+1 FROM ESP_DESCRIPCION C");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                max = rs.getLong(1);
            }
            return max;
        } catch (SQLException e) {
            throw e;
        }finally {
            conn.close();
            pstmt.close();
        }
    }
}
