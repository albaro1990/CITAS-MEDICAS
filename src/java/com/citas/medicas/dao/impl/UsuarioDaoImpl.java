package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.UsuarioDao;
import com.citas.medicas.entity.FacUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(FacUsuario usuario) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO CIT_USUARIO(USU_CODIGO, USU_LOGIN, USU_CLAVE, USU_NOMBRE, USU_APELLIDOS, USU_CORREO, USU_IDENTIFICACION, USU_DIRECCION, USU_TELEFONO, USU_ESTADO, USU_FECHA_CREACION) "
                    + "VALUES (CIT_SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"USU_CODIGO"});
            pstmt.setString(1, usuario.getUsuLogin());
            pstmt.setString(2, usuario.getUsuClave());
            pstmt.setString(3, usuario.getUsuNombres());
            pstmt.setString(4, usuario.getUsuApellidos());
            pstmt.setString(5, usuario.getUsuCorreo());
            pstmt.setString(6, usuario.getUsuIdentificacion());
            pstmt.setString(7, usuario.getUsuDireccion());
            pstmt.setString(8, usuario.getUsuTelefono());
            pstmt.setInt(9, usuario.getUsuEstado());
            pstmt.setDate(10, new java.sql.Date(new java.util.Date().getTime()));
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Error al crear el usuario");
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
    public int update(FacUsuario usuario) throws SQLException {
        int nup = 0;
        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE CIT_USUARIO SET USU_LOGIN='" + usuario.getUsuLogin() + "',USU_CLAVE='" + usuario.getUsuClave() + "',"
                    + "USU_NOMBRES='" + usuario.getUsuNombres() + "',USU_APELLIDOS='" + usuario.getUsuApellidos() + "',USU_CORREO='" + usuario.getUsuCorreo() + "',"
                    + "USU_IDENTIFICACION='" + usuario.getUsuIdentificacion() + "',USU_DIRECCION='" + usuario.getUsuDireccion() + "',USU_TELEFONO='" + usuario.getUsuTelefono() + "',"
                    + "USU_ESTADO=" + usuario.getUsuEstado() + ", USU_FECHA_MODIFICACION= SYSDATE WHERE USU_CODIGO = " + usuario.getUsuCodigo() + " ");

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
            sql.append("DELETE FROM CIT_USUARIO WHERE USU_CODIGO = ?");
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
    public List<FacUsuario> findAll() throws SQLException {
        List<FacUsuario> usuarios = new ArrayList<FacUsuario>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_USUARIO ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FacUsuario usuario = new FacUsuario();
                //usuario.setUsuCodigo(rs.getBigDecimal(1));
                usuario.setUsuLogin(rs.getString(2));
                usuario.setUsuClave(rs.getString(3));
                usuario.setUsuNombres(rs.getString(4));
                usuario.setUsuApellidos(rs.getString(5));
                usuario.setUsuCorreo(rs.getString(6));
                usuario.setUsuIdentificacion(rs.getString(7));
                usuario.setUsuDireccion(rs.getString(8));
                usuario.setUsuTelefono(rs.getString(9));
                usuario.setUsuEstado(rs.getInt(10));
                usuario.setFechaCreacion(rs.getDate(11));
                usuario.setFechaModificacion(rs.getDate(11));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return usuarios;
    }

    @Override
    public FacUsuario find(int id) throws SQLException {
        FacUsuario usuario = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_USUARIO WHERE USU_CODIGO = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario = new FacUsuario();
                usuario.setUsuCodigo(rs.getLong(1));
                usuario.setUsuLogin(rs.getString(2));
                usuario.setUsuClave(rs.getString(3));
                usuario.setUsuNombres(rs.getString(4));
                usuario.setUsuApellidos(rs.getString(5));
                usuario.setUsuCorreo(rs.getString(6));
                usuario.setUsuIdentificacion(rs.getString(7));
                usuario.setUsuDireccion(rs.getString(8));
                usuario.setUsuTelefono(rs.getString(9));
                usuario.setUsuEstado(rs.getInt(10));
                usuario.setFechaCreacion(rs.getDate(11));
                usuario.setFechaModificacion(rs.getDate(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }
        return usuario;
    }

    @Override
    public boolean existePorCampo(String username) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM CIT_USUARIO U WHERE U.USU_LOGIN='" + username + "'");
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

    @Override
    public Long nuevoCodigo() throws SQLException {
        try {
            Long max = Long.valueOf(0);
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT MAX(U.USU_CODIGO)+1 FROM CIT_USUARIO U");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                max = rs.getLong(1);
            }
            return max;
        } catch (SQLException e) {
            throw e;

        } finally {
            conn.close();
            pstmt.close();
        }
    }
}
