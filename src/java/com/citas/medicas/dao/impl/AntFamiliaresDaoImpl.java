package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.AntFamiliaresDao;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.citas.medicas.dao.CitaDao;
import com.citas.medicas.entity.CitAntFamiliares;
import com.citas.medicas.entity.FacUsuario;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AntFamiliaresDaoImpl implements AntFamiliaresDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitAntFamiliares citAntFamiliares) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO CIT_FAMILILAR_ANT(ANFA_CODIGO,PAC_CODIGO,ANFA_HEPATOPATIA,ANFA_ALERGIAS,ANFA_ASMA,"
                    + " ANFA_HIPERTENSION,ANFA_CARDIOPATIA,ANFA_NEFROPATIA,ANFA_CANCER,ANFA_OTROS)"
                    + " VALUES (CIT_SEQ_ANT_FAM.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
              pstmt = conn.prepareStatement(sql.toString(), new String[]{"ANFA_CODIGO"});
            pstmt.setLong(1, citAntFamiliares.getAntFamPacCodigo().getPacCodigo());
            if (citAntFamiliares.getHepatopatia() != null) {
                pstmt.setString(2, citAntFamiliares.getHepatopatia());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }
            if (citAntFamiliares.getAlergias() != null) {
                pstmt.setString(3, citAntFamiliares.getAlergias());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }
            if (citAntFamiliares.getAsma() != null) {
                pstmt.setString(4, citAntFamiliares.getAsma());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            if (citAntFamiliares.getHipertension() != null) {
                pstmt.setString(5, citAntFamiliares.getHipertension());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }
            if (citAntFamiliares.getCardipatia() != null) {
                pstmt.setString(6, citAntFamiliares.getCardipatia());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
            }
            if (citAntFamiliares.getNefropatia() != null) {
                pstmt.setString(7, citAntFamiliares.getNefropatia());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
            }
            if (citAntFamiliares.getCancer() != null) {
                pstmt.setString(8, citAntFamiliares.getCancer());
            } else {
                pstmt.setNull(8, Types.VARCHAR);
            }
            if (citAntFamiliares.getOtros() != null) {
                pstmt.setString(9, citAntFamiliares.getOtros());
            } else {
                pstmt.setNull(9, Types.VARCHAR);
            }
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear Ant Personales");
            }

            rs = pstmt.getGeneratedKeys();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear la factura");
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idInserted = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return idInserted;
    }

    @Override
    public int update(CitAntFamiliares citAntFamiliares) throws SQLException {
        int nup = 0;
         StringBuilder sql = new StringBuilder();
        try {
            
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_FAMILILAR_ANT SET ANFA_HEPATOPATIA=?,ANFA_ALERGIAS=?,ANFA_ASMA=?,"
                    + " ANFA_HIPERTENSION=?,ANFA_CARDIOPATIA=?,ANFA_NEFROPATIA=?,ANFA_CANCER=?,ANFA_OTROS=?"
                    + " WHERE ANFA_CODIGO = ? ");
            if (citAntFamiliares.getHepatopatia() != null) {
                pstmt.setString(1, citAntFamiliares.getHepatopatia());
            } else {
                pstmt.setNull(1, Types.VARCHAR);
            }
            if (citAntFamiliares.getAlergias() != null) {
                pstmt.setString(2, citAntFamiliares.getAlergias());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }
            if (citAntFamiliares.getAsma() != null) {
                pstmt.setString(3, citAntFamiliares.getAsma());
            } else {
                pstmt.setNull(3, Types.VARCHAR);
            }
            if (citAntFamiliares.getHipertension() != null) {
                pstmt.setString(4, citAntFamiliares.getHipertension());
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            if (citAntFamiliares.getCardipatia() != null) {
                pstmt.setString(5, citAntFamiliares.getCardipatia());
            } else {
                pstmt.setNull(5, Types.VARCHAR);
            }
            if (citAntFamiliares.getNefropatia() != null) {
                pstmt.setString(6, citAntFamiliares.getNefropatia());
            } else {
                pstmt.setNull(6, Types.VARCHAR);
            }
            if (citAntFamiliares.getCancer() != null) {
                pstmt.setString(7, citAntFamiliares.getCancer());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
            }
            if (citAntFamiliares.getOtros() != null) {
                pstmt.setString(8, citAntFamiliares.getOtros());
            } else {
                pstmt.setNull(8, Types.VARCHAR);
            }
            pstmt.setLong(9, citAntFamiliares.getAntFamCodigo());
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
            pstmt = conn.prepareStatement("DELETE FROM CIT_CITA WHERE CAB_CODIGO = " + id + "");
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
    public CitAntFamiliares findXIdPaciente(int id) throws SQLException {

        CitAntFamiliares citAntFamiliares = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_FAMILILAR_ANT WHERE PAC_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                citAntFamiliares = new CitAntFamiliares();
                citAntFamiliares.setAntFamCodigo(rs.getLong(1));
                citAntFamiliares.setAntFamPacCodigo(new CitPaciente());
                citAntFamiliares.getAntFamPacCodigo().setPacCodigo(rs.getLong(2));
                citAntFamiliares.setHepatopatia(rs.getString(3));
                citAntFamiliares.setAlergias(rs.getString(4));
                citAntFamiliares.setAsma(rs.getString(5));
                citAntFamiliares.setHipertension(rs.getString(6));
                citAntFamiliares.setCardipatia(rs.getString(7));
                citAntFamiliares.setNefropatia(rs.getString(8)); 
                citAntFamiliares.setCancer(rs.getString(9));
                citAntFamiliares.setOtros(rs.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return citAntFamiliares;
    }
    
    
    public static String formatHoras (Date date, String formato){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		String hora = simpleDateFormat.format(date);
		hora = hora.substring(11, 16);
		return  hora;
		
	}
    
     
    
    	/**
	 * Método que retorna un objeto de tipo Date dado la fecha basado en el formato dd/mm/yyyy
	 * @param fecha La fecha que se desea parsear.
	 * @return La fecha en formato Date.
	 */
 public static Date formatDate(String fecha) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		try {
			return simpleDateFormat.parse(fecha);
		} catch (ParseException e) {
			throw new RuntimeException("Error en el parseo de la fecha: "	+ fecha);
		}
 }
 
 
 
 public static Date formatFecha(String fecha, String formato) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		try {
			return simpleDateFormat.parse(fecha);
		} catch (ParseException e) {
			throw new RuntimeException("Error en el parseo de la fecha: "	+ fecha);
		}
 }
  
   public static String formatFechaString (Date date, String formato){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		String fecha = simpleDateFormat.format(date);
		return  fecha;
		
	}   

}
