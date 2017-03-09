package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.entity.CitCita;
import com.citas.medicas.entity.CitPaciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.citas.medicas.dao.HistoriaDao;
import com.citas.medicas.entity.CitHistoriaClinica;
import com.citas.medicas.entity.FacUsuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoriaDaoImpl implements HistoriaDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitHistoriaClinica historia) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            
            conn = new ConexionDB().getConexion();
            sql.append(" INSERT INTO CIT_HISTORIA_CLINICA(HIS_CODIGO,PAC_CODIGO, HIS_TIPO_DE_SANGRE, HIS_PESO,HIS_TALLA, HIS_INDICE_MASA_CORPORAL,"
                      +" HIS_PRESION_ARTERIAL, HIS_TRATAMIENTOS,HIS_SINTOMAS,HIS_MOTIVO,HIS_EDAD, HIS_FECHA_ATENCION)VALUES (CIT_SEQ_HISTORIA.NEXTVAL, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, SYSDATE)");
            

            pstmt = conn.prepareStatement(sql.toString(), new String[]{"HIS_CODIGO"});
            historia.setUsuario(new FacUsuario());
            pstmt.setLong(1, historia.getUsuario().getUsuCodigo());
            pstmt.setString(2, historia.getTipoSangre());
            pstmt.setString(3, historia.getPeso());
            pstmt.setString(4, historia.getTalla());
            pstmt.setString(5, historia.getImc());
            pstmt.setString(6, historia.getPresion());
            pstmt.setString(7, historia.getTratamiento());
            pstmt.setString(8, historia.getSintomas());
            pstmt.setString(9, historia.getCitMotivo());
            pstmt.setLong(10, historia.getEdad());

            int affectedRows = pstmt.executeUpdate();

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
    public int update(CitHistoriaClinica historia) throws SQLException {
        int nup = 0;
         StringBuilder sql = new StringBuilder();
        try {
//             String horaMin= this.formatHoras(cita.getHoraCita(), "dd/MM/yyyy HH:mm:ss");
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_CITA SET CIT_ESTADO= ?, CIT_HORA=?, CIT_FECHA=? WHERE CIT_CODIGO = ? ");
//            pstmt = conn.prepareStatement(sql.toString());
//            pstmt.setInt(1, cita.getCitEstado().intValue());
//            pstmt.setString(2, horaMin);
//            pstmt.setDate(3, new java.sql.Date(cita.getCitFechaCita().getTime()));
//            pstmt.setLong(4, cita.getCitCodigo());
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
    public List<CitHistoriaClinica> findAll() throws SQLException {
        List<CitHistoriaClinica> citas = new ArrayList<CitHistoriaClinica>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT CIT_CODIGO, USU_CODIGO, PAC_CODIGO, CIT_FECHA, to_char(to_date(CIT_HORA, 'hh24miss'), 'hh24:mi') as hora, CIT_ESTADO, CIT_MOTIVO FROM CIT_CITA WHERE CIT_ESTADO NOT IN(0) ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitCita cita = new CitCita();
                cita.setCitCodigo(rs.getLong(1));
                cita.setUsuario(new FacUsuario());
                cita.getUsuario().setUsuCodigo(rs.getLong(2));
                cita.setCliCodigo(new CitPaciente());
                cita.getCliCodigo().setPacCodigo(rs.getLong(3));
                cita.setCitFechaCita(rs.getDate(4));
                String hora = rs.getString(5);
                this.formatDate(hora);
                cita.setHoraCita(this.formatDate(hora));
                cita.setCitEstado(rs.getInt(6));
                if(rs.getString(7)!=null){
                    cita.setCitMotivo(rs.getString(7));
                }
//                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return citas;
    }

    @Override
    public CitHistoriaClinica find(int id) throws SQLException {

        CitHistoriaClinica factura = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT CIT_CODIGO, USU_CODIGO, PAC_CODIGO, CIT_FECHA, to_char(to_date(CIT_HORA, 'hh24miss'), 'hh24:mi') as hora, CIT_ESTADO, CIT_MOTIVO FROM CIT_HISTORIA_CLINICA WHERE CIT_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                factura = new CitHistoriaClinica();
//                factura.setUapCodigo(new FacUsuarioAplicacion());
                factura.setCliCodigo(new CitPaciente());
                
//                factura.setCabCodigo(rs.getBigDecimal(1));
//                factura.getUapCodigo().setUapCodigo(rs.getBigDecimal(2));
//                factura.getCliCodigo().setPacCodigo(rs.getLong(3));
//                factura.setCabFechaCreacion(rs.getDate(4));
//                factura.setCabEstado(rs.getInt(5));
//                factura.setCabAutorizacion(rs.getString(6));
//                factura.setCabIdentificacion(rs.getString(7));//ruc de la empresa emisora no del cliente
//                factura.setCabTotal(rs.getBigDecimal(8));
//                factura.setCabIva(rs.getBigDecimal(9));
//                factura.setCabSubtotal(rs.getBigDecimal(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return factura;
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

    @Override
    public int cacelar(int id) throws SQLException {
         int nup = 0;
         StringBuilder sql = new StringBuilder();
        try {
            
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_CITA SET CIT_ESTADO= 0 WHERE CIT_CODIGO = ? ");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, id);
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
    public List<CitHistoriaClinica> findAllXPaciente(Long codigoPaciente) throws SQLException {
        List<CitHistoriaClinica> historias = new ArrayList<CitHistoriaClinica>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT DISTINCT HIS_CODIGO, PAC_NOMBRES||PAC_APELLIDOS AS PACIENTE, "
                      + " HIS_TIPO_DE_SANGRE, HIS_PESO, HIS_TALLA, HIS_INDICE_MASA_CORPORAL, " 
                      + " HIS_PRESION_ARTERIAL,HIS_TRATAMIENTOS,HIS_SINTOMAS,HIS_MOTIVO,HIS_EDAD, HIS_FECHA_ATENCION "
                      + " FROM CIT_HISTORIA_CLINICA HIS, CIT_PACIENTE PAC " 
                      + " WHERE HIS.PAC_CODIGO = PAC.PAC_CODIGO " 
                      + " AND HIS.PAC_CODIGO ="+codigoPaciente+"");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CitHistoriaClinica historia = new CitHistoriaClinica();
                historia.setHisCodigo(rs.getLong(1));
                historia.setTipoSangre(rs.getString(3));
                historia.setPeso(rs.getString(4));
                historia.setTalla(rs.getString(5));
                historia.setImc(rs.getString(6));
                historia.setPresion(rs.getString(7));
                historia.setTratamiento(rs.getString(8));
                historia.setSintomas(rs.getString(9));
                historia.setCitMotivo(rs.getString(10));
                historia.setEdad(rs.getInt(11));
                historia.setFechaAtencion(rs.getDate(12));              
                historias.add(historia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return historias;
    }

    @Override
    public boolean existeCita(Long codigoDoc, Date fechaCita, Date horaCita) throws SQLException {
        try {
            Integer rowCount = 0;
            String fechaCit=formatFechaString(fechaCita, "DD/MM/YYYY");
            horaCita.getTime();
            conn = new ConexionDB().getConexion();
            StringBuffer ssql = new StringBuffer();
            ssql.append(" SELECT COUNT(*) AS CONTADOR ");
            ssql.append(" FROM CIT_CITA CIT ");
            ssql.append(" WHERE CIT.USU_CODIGO= "+ codigoDoc+" ");
            ssql.append(" AND CIT.CIT_FECHA=TO_DATE('"+new java.sql.Date(fechaCita.getTime())+"','YYYY/MM/DD') ");
            ssql.append(" AND ( to_char(to_date(CIT.CIT_HORA, 'hh24miss'), 'hh24:mi')='"+formatHoras(horaCita,"dd/MM/yyyy HH:mm:ss")+"' OR '"+formatHoras(horaCita,"dd/MM/yyyy HH:mm:ss")+"' <= to_char(to_date(CIT.CIT_HORA, 'hh24miss')+ 29/1440, 'hh24:mi'))");
          /*  if(codEspecialidad>0){
            ssql.append("AND ESP_CODIGO = ?  ");
            }*/
            pstmt = conn.prepareStatement(ssql.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rowCount = rs.getInt("CONTADOR");
            }
            return rowCount > 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            pstmt.close();
        }
    }      

}
