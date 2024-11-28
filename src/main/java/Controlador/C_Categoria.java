package Controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Moises
 */
import Modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class C_Categoria {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public C_Categoria(Connection connection) {
        this.connection = connection;
    }

    // Método para agregar una categoría
    public boolean insertarCategoria(Categoria categoria) throws SQLException {
        String sql = "CALL sp_insertar_categoria(?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.getDescripcion());
            stmt.setString(2, categoria.getTipo());
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para actualizar una categoría
    public boolean actualizarCategoria(Categoria categoria) throws SQLException {
        String sql = "CALL sp_actualizar_categoria(?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoria.getIdCategoria());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setString(3, categoria.getTipo());
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar (lógicamente) una categoría
    public boolean eliminarCategoria(int idCategoria) throws SQLException {
        String sql = "CALL sp_eliminar_categoria(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para buscar una categoría por ID
    public Categoria buscarCategoria(int idCategoria) throws SQLException {
        String sql = "CALL sp_buscar_categoria(?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("idCategoria"));
                    categoria.setDescripcion(rs.getString("descripcion"));
                    categoria.setTipo(rs.getString("tipo"));
                    categoria.setActivo(rs.getInt("activo"));
                    return categoria;
                }
            }
        }
        return null;
    }

    // Método para listar todas las categorías activas
    public List<Categoria> listarCategoriasActivas() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE activo = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipo(rs.getString("tipo"));
                categoria.setActivo(rs.getInt("activo"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }
    
    public List<Categoria> listarCategorias() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setTipo(rs.getString("tipo"));
                categoria.setActivo(rs.getInt("activo"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }
}


