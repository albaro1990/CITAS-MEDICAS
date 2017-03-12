package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.UsuarioAplicacionDao;
import com.citas.medicas.entity.FacRol;
import com.citas.medicas.entity.FacUsuario;
import com.citas.medicas.entity.FacUsuarioAplicacion;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAplicacionDaoImpl implements UsuarioAplicacionDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacUsuarioAplicacion usuarioAplicacion) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append(" INSERT INTO CIT_USUARIO_APLICACION(UAP_CODIGO, USU_CODIGO, ROL_CODIGO, UAP_ESTADO,UAP_FECHA_CREACION) "
                    + " VALUES (CIT_SEQ_USUARIO_APLICACION.NEXTVAL, "+usuarioAplicacion.getUsuCodigo().getUsuCodigo()+", "
                    + " "+usuarioAplicacion.getRolCodigo().getRolCodigo()+", "
                    + " "+usuarioAplicacion.getUsuCodigo().getUsuEstado()+", SYSDATE)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"UAP_CODIGO"});
           /* pstmt.setLong(1, usuarioAplicacion.getUsuCodigo().getUsuCodigo());
            pstmt.setBigDecimal(2, usuarioAplicacion.getRolCodigo().getRolCodigo());
            pstmt.setInt(3, usuarioAplicacion.getUsuCodigo().getUsuEstado());
            pstmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));*/
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear el usuario aplicacion");
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
    public int update(FacUsuarioAplicacion usuarioAplicacion) throws SQLException {
        int nup = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("UPDATE CIT_USUARIO_APLICACION SET USU_CODIGO = ?, ROL_CODIGO = ?, UAP_FECHA_ACTUALIZACION = ?, UAP_ESTADO = ? WHERE UAP_CODIGO = ?");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, usuarioAplicacion.getUsuCodigo().getUsuCodigo().intValue());
            pstmt.setInt(2, usuarioAplicacion.getRolCodigo().getRolCodigo().intValue());
            pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setInt(4, usuarioAplicacion.getUsuCodigo().getUsuEstado());
            pstmt.setInt(5, usuarioAplicacion.getUapCodigo().intValue());
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
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("DELETE FROM CIT_USUARIO_APLICACION WHERE UAP_CODIGO = ?");
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, id);
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
    public List<FacUsuarioAplicacion> findAll() throws SQLException {
        List<FacUsuarioAplicacion> usuariosAplicacion = new ArrayList<FacUsuarioAplicacion>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_USUARIO_APLICACION WHERE UAP_ESTADO = 1");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacUsuarioAplicacion uap = new FacUsuarioAplicacion();
                uap.setUsuCodigo(new FacUsuario());
                uap.setRolCodigo(new FacRol());
                uap.setUapCodigo(rs.getBigDecimal(1));
                uap.getUsuCodigo().setUsuCodigo(rs.getLong(2));
                uap.getRolCodigo().setRolCodigo(rs.getBigDecimal(3));
                uap.setUapFechaActualizacion(rs.getDate(4));
                uap.setUapEstado(rs.getInt(5));
                uap.setUapFechaCreacion(rs.getDate(6));
                usuariosAplicacion.add(uap);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            conn.close();
            pstmt.close();
        }

        return usuariosAplicacion;
    }

    @Override
    public FacUsuarioAplicacion find(int id) throws SQLException {
        FacUsuarioAplicacion uap = null;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_USUARIO WHERE USU_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                uap = new FacUsuarioAplicacion();
                uap.setUapCodigo(rs.getBigDecimal(1));
                uap.getUsuCodigo().setUsuCodigo(rs.getLong(2));
                uap.getRolCodigo().setRolCodigo(rs.getBigDecimal(3));
                uap.setUapFechaActualizacion(rs.getDate(4));
                uap.setUapEstado(rs.getInt(5));
                uap.setUapFechaCreacion(rs.getDate(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return uap;
    }
}
