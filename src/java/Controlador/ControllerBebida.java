package org.utl.dsm.controller;

import org.utl.dsm.bd.ConexionMysql;
import org.utl.dsm.model.Bebida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerBebida {

    public void insertBebida(String nombre, String descripcion, String foto, double precio, int idCategoria, int activo) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL insertar_bebida(?, ?, ?, ?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setString(2, descripcion);
        stmt.setString(3, foto);
        stmt.setDouble(4, precio);
        stmt.setInt(5, idCategoria);
        stmt.setInt(6, activo);
        stmt.execute();

        conn.close();
    }

    public List<Bebida> getAllBebidas() throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM vista_bebidas");

        List<Bebida> lista = new ArrayList<>();
        while (rs.next()) {
            Bebida bebida = new Bebida(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Descripcion"),
                    rs.getString("Foto"),
                    rs.getDouble("Precio"),
                    rs.getString("Categoria"),
                    rs.getInt("Estado")
            );
            lista.add(bebida);
        }

        conn.close();
        return lista;
    }

    public void updateBebida(int idBebida, String nombre, String descripcion, String foto, double precio, int idCategoria, int activo) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL actualizar_bebida(?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, idBebida);
        stmt.setString(2, nombre);
        stmt.setString(3, descripcion);
        stmt.setString(4, foto);
        stmt.setDouble(5, precio);
        stmt.setInt(6, idCategoria);
        stmt.setInt(7, activo);
        stmt.execute();

        conn.close();
    }

    public void deleteBebida(int idBebida) throws SQLException {
        ConexionMysql conn = new ConexionMysql();
        Connection connection = conn.open();

        CallableStatement stmt = connection.prepareCall("CALL eliminar_bebida(?)");
        stmt.setInt(1, idBebida);
        stmt.execute();

        conn.close();
    }
}