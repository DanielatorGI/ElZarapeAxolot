package org.utl.dsm.controller;

import org.utl.dsm.bd.ConexionMysql;
import org.utl.dsm.model.Alimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerAlimento {

    public void insertAlimento(String nombre, String descripcion, String foto, double precio, int idCategoria, int activo) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL insertar_alimento(?, ?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setString(2, descripcion);
        stmt.setString(3, foto);
        stmt.setDouble(4, precio);
        stmt.setInt(5, idCategoria);
        stmt.setInt(6, activo);
        stmt.execute();

        conn.close();
    }

    public List<Alimento> getAllAlimentos() throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM vista_alimento");

        List<Alimento> lista = new ArrayList<>();
        while (rs.next()) {
            Alimento alimento = new Alimento(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Descripcion"),
                    rs.getString("Foto"),
                    rs.getDouble("Precio"),
                    rs.getString("Categoria"),
                    rs.getInt("Estado")
            );
            lista.add(alimento);
        }

        conn.close();
        return lista;
    }

    public void updateAlimento(int idAlimento, String nombre, String descripcion, String foto, double precio, int idCategoria, int activo) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL actualizar_alimento(?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, idAlimento);
        stmt.setString(2, nombre);
        stmt.setString(3, descripcion);
        stmt.setString(4, foto);
        stmt.setDouble(5, precio);
        stmt.setInt(6, idCategoria);
        stmt.setInt(7, activo);
        stmt.execute();

        conn.close();
    }

    public void deleteAlimento(int idAlimento) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL eliminar_alimento(?)");
        stmt.setInt(1, idAlimento);
        stmt.execute();

        conn.close();
    }
}
