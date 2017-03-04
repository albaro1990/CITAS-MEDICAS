package com.citas.medicas.dao.impl;

import com.citas.medicas.conexion.ConexionDB;
import com.citas.medicas.dao.ClienteDao;
import com.citas.medicas.entity.CitPaciente;
import com.citas.medicas.entity.FacCiudad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements ClienteDao {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pstmt;

    @Override
    public int save(CitPaciente paciente) throws SQLException {
        int idInserted = 0;
        StringBuilder sql = new StringBuilder();
        try {
            conn = new ConexionDB().getConexion();
            sql.append("INSERT INTO CIT_PACIENTE(PAC_CODIGO,CIU_CODIGO,PAC_NOMBRES,PAC_APELLIDOS,PAC_TELEFONO,PAC_DIRECCION,PAC_CEDULA,PAC_CORREO,PAC_ESTADO, PAC_GENERO) VALUES (CIT_SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt = conn.prepareStatement(sql.toString(), new String[]{"PAC_CODIGO"});
            pstmt.setBigDecimal(1, paciente.getCodigoCiudad().getCiuCodigo());
            pstmt.setString(2, paciente.getPacNombres());
            pstmt.setString(3, paciente.getPacApellidos());
            pstmt.setString(4, paciente.getPacTelefono());
            pstmt.setString(5, paciente.getPacDireccion());
            pstmt.setString(6, paciente.getPacIdentificacin());
            pstmt.setString(7, paciente.getPacCorreo());
            pstmt.setInt(8, paciente.getPacEstado());
            pstmt.setString(9, paciente.getPacGenero());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error al crear el paciente");
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
    public int update(CitPaciente paciente) throws SQLException {
        int nup = 0;
        try {

            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("UPDATE CIT_PACIENTE SET CIU_CODIGO=" + paciente.getCodigoCiudad().getCiuCodigo() + ", PAC_NOMBRES='" + paciente.getPacNombres() + "',"
                    + "PAC_APELLIDOS='" + paciente.getPacApellidos() + "',PAC_TELEFONO='" + paciente.getPacTelefono()+ "',PAC_DIRECCION='" + paciente.getPacDireccion() + "',"
                    + "PAC_CEDULA='" + paciente.getPacIdentificacin() + "', PAC_CORREO='" + paciente.getPacCorreo() + "',PAC_ESTADO=" + paciente.getPacEstado() + ","
                    + "PAC_GENERO='"+paciente.getPacGenero()+"' WHERE PAC_CODIGO = "+ paciente.getPacCodigo() +" ");

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
            pstmt = conn.prepareStatement("DELETE FROM CIT_PACIENTE WHERE PAC_CODIGO = " + id + "");
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
    public List<CitPaciente> findAll() throws SQLException {
        List<CitPaciente> clientes = new ArrayList<CitPaciente>();

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_PACIENTE ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CitPaciente cliente = new CitPaciente();
                cliente.setPacCodigo(rs.getLong(1));
                cliente.setCodigoCiudad(new FacCiudad());
                cliente.getCodigoCiudad().setCiuCodigo(rs.getBigDecimal(2));
                cliente.setPacNombres(rs.getString(3));
                cliente.setPacApellidos(rs.getString(4));
                cliente.setPacTelefono(rs.getString(5));
                cliente.setPacDireccion(rs.getString(6));
                cliente.setPacIdentificacin(rs.getString(7));
                cliente.setPacCorreo(rs.getString(8));
                cliente.setPacEstado(rs.getInt(9));
                cliente.setPacGenero(rs.getString(10));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return clientes;
    }

    @Override
    public CitPaciente find(String id) throws SQLException {

        CitPaciente paciente = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_PACIENTE WHERE PAC_CEDULA = ? AND PAC_ESTADO = 1");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                paciente = new CitPaciente();
                
                paciente.setPacCodigo(rs.getLong(1));
                paciente.setCodigoCiudad(new FacCiudad());
                paciente.getCodigoCiudad().setCiuCodigo(rs.getBigDecimal(2));
                paciente.setPacNombres(rs.getString(3));
                paciente.setPacApellidos(rs.getString(4));
                paciente.setFechaNacimiento(rs.getDate(5));
                paciente.setEstadoCivil(rs.getString(6));
                paciente.setPacTelefono(rs.getString(7));
                paciente.setPacDireccion(rs.getString(8));
                paciente.setPacIdentificacin(rs.getString(9));
                paciente.setPacCorreo(rs.getString(10));
                paciente.setPacEstado(rs.getInt(11));
                paciente.setPacGenero(rs.getString(12));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return paciente;
    }

    @Override
    public boolean existePorCampo(String identificacion) throws SQLException {
        try {
            Integer rowCount = 0;
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS CONTADOR FROM CIT_PACIENTE WHERE PAC_CEDULA='" + identificacion + "'");
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
    public CitPaciente findXId(int id) throws SQLException {

        CitPaciente paciente = null;

        try {
            conn = new ConexionDB().getConexion();
            pstmt = conn.prepareStatement("SELECT * FROM CIT_PACIENTE WHERE PAC_CODIGO = ? AND PAC_ESTADO = 1");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                paciente = new CitPaciente();
                paciente.setPacCodigo(rs.getLong(1));
                paciente.setCodigoCiudad(new FacCiudad());
                paciente.getCodigoCiudad().setCiuCodigo(rs.getBigDecimal(2));
                paciente.setPacNombres(rs.getString(3));
                paciente.setPacApellidos(rs.getString(4));
                paciente.setFechaNacimiento(rs.getDate(5));
                //paciente.setEdad(rs.getInt(6));
                paciente.setEstadoCivil(rs.getString(6));
                paciente.setPacTelefono(rs.getString(7));
                paciente.setPacDireccion(rs.getString(8));
                paciente.setPacIdentificacin(rs.getString(9));
                paciente.setPacCorreo(rs.getString(10));
                paciente.setPacEstado(rs.getInt(11));
                paciente.setPacGenero(rs.getString(12));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
            pstmt.close();
        }

        return paciente;
    }
}
