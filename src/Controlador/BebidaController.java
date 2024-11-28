package Controlador;

import BD.ConexionMysql;
import Modelo.Bebida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BebidaController {

    private final ConexionMysql conexionMysql;

    public BebidaController() {
        this.conexionMysql = new ConexionMysql();
    }

    // Insertar bebida
    public boolean insertarBebida(String nombre, String descripcion, double precio, int idCategoria) {
        String sql = "{ CALL insertar_bebida(?, ?, ?, ?) }";
        Connection conn = null;
        try {
            conn = conexionMysql.open();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.setDouble(3, precio);
            stmt.setInt(4, idCategoria);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar bebida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                conexionMysql.close();
            }
        }
    }

    // Actualizar bebida
    public boolean actualizarBebida(int idProducto, String nombre, String descripcion, double precio, int idCategoria) {
        String sql = "{ CALL actualizar_bebida(?, ?, ?, ?, ?) }";
        Connection conn = null;
        try {
            conn = conexionMysql.open();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, idProducto);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);
            stmt.setDouble(4, precio);
            stmt.setInt(5, idCategoria);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar bebida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                conexionMysql.close();
            }
        }
    }

    // Eliminar bebida
    public boolean eliminarBebida(int idProducto) {
        String sql = "{ CALL eliminar_bebida(?) }";
        Connection conn = null;
        try {
            conn = conexionMysql.open();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, idProducto);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar bebida: " + e.getMessage());
            return false;

        } finally {
            if (conn != null) {
                conexionMysql.close();
            }
        }
    }

    // Listar bebidas
    public List<Bebida> listarBebidas() {
        List<Bebida> bebidas = new ArrayList<>();
        String sql = "SELECT * FROM vista_bebidas";
        Connection conn = null;
        try {
            conn = conexionMysql.open();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bebida bebida = new Bebida(
                        rs.getInt("idBebida"),
                        rs.getString("nombreBebida"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("categoria")
                );
                bebidas.add(bebida);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar bebidas: " + e.getMessage());

        } finally {
            if (conn != null) {
                conexionMysql.close();
            }
        }
        return bebidas;
    }
}
