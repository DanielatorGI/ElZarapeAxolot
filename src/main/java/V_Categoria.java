/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Moises
 */
import Controlador.C_Categoria;
import Modelo.Categoria;
import Conexion.ConexionMySQL;
import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class V_Categoria {
    public static void main(String[] args) {
        ConexionMySQL conexionMysql = new ConexionMySQL();
        Connection connection = null;
        C_Categoria controlador = null;

        try {
            // Establecer conexión con la base de datos
            connection = conexionMysql.open();
            controlador = new C_Categoria(connection);

            int opcion;
            do {
                // Mostrar el menú
                String[] opciones = {
                        "Insertar categoría",
                        "Actualizar categoría",
                        "Eliminar categoría",
                        "Buscar categoría por ID",
                        "Listar categorías",
                        "Listar categorías activas",
                        "Salir"
                };

                String seleccion = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione una opción",
                        "Gestión de Categorías",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                if (seleccion == null) break; // Si el usuario cierra el diálogo

                opcion = switch (seleccion) {
                    case "Insertar categoría" -> 1;
                    case "Actualizar categoría" -> 2;
                    case "Eliminar categoría" -> 3;
                    case "Buscar categoría por ID" -> 4;
                    case "Listar categorías" -> 5;
                    case "Listar categorías activas" -> 6;
                    case "Salir" -> 7;
                    default -> 0;
                };

                switch (opcion) {
                    case 1 -> {
                        // Insertar categoría
                        String descripcion = JOptionPane.showInputDialog("Ingrese la descripción:");
                        if (descripcion == null) break;

                        String tipo = JOptionPane.showInputDialog("Ingrese el tipo (A para Alimentos, B para Bebidas):");
                        if (tipo == null) break;

                        Categoria nuevaCategoria = new Categoria(0, descripcion, tipo.toUpperCase(), 1);
                        if (controlador.insertarCategoria(nuevaCategoria)) {
                            JOptionPane.showMessageDialog(null, "Categoría insertada correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al insertar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    case 2 -> {
                        // Actualizar categoría
                        String idCategoriaStr = JOptionPane.showInputDialog("Ingrese el ID de la categoría a actualizar:");
                        if (idCategoriaStr == null) break;

                        int idCategoria = Integer.parseInt(idCategoriaStr);

                        String descripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción:");
                        if (descripcion == null) break;

                        String tipo = JOptionPane.showInputDialog("Ingrese el nuevo tipo (A para Alimentos, B para Bebidas):");
                        if (tipo == null) break;

                        Categoria categoriaActualizada = new Categoria(idCategoria, descripcion, tipo.toUpperCase(), 1);
                        if (controlador.actualizarCategoria(categoriaActualizada)) {
                            JOptionPane.showMessageDialog(null, "Categoría actualizada correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al actualizar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    case 3 -> {
                        // Eliminar categoría
                        String idCategoriaStr = JOptionPane.showInputDialog("Ingrese el ID de la categoría a eliminar:");
                        if (idCategoriaStr == null) break;

                        int idCategoria = Integer.parseInt(idCategoriaStr);

                        if (controlador.eliminarCategoria(idCategoria)) {
                            JOptionPane.showMessageDialog(null, "Categoría eliminada (lógicamente) correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    case 4 -> {
                        // Buscar categoría
                        String idCategoriaStr = JOptionPane.showInputDialog("Ingrese el ID de la categoría a buscar:");
                        if (idCategoriaStr == null) break;

                        int idCategoria = Integer.parseInt(idCategoriaStr);

                        Categoria categoria = controlador.buscarCategoria(idCategoria);
                        if (categoria != null) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "=== Detalles de la Categoría ===\n" +
                                            "ID: " + categoria.getIdCategoria() + "\n" +
                                            "Descripción: " + categoria.getDescripcion() + "\n" +
                                            "Tipo: " + categoria.getTipo() + "\n" +
                                            "Activo: " + (categoria.getActivo() == 1 ? "Sí" : "No")
                            );
                        } else {
                            JOptionPane.showMessageDialog(null, "Categoría no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    case 5 -> {
                        // Listar categorías activas
                        List<Categoria> categorias = controlador.listarCategorias();
                        StringBuilder listado = new StringBuilder("=== Categorías ===\n");
                        for (Categoria categoria : categorias) {
                            listado.append("ID: ").append(categoria.getIdCategoria())
                                    .append(", Descripción: ").append(categoria.getDescripcion())
                                    .append(", Tipo: ").append(categoria.getTipo())
                                    .append(", Activo: ").append(categoria.getActivo() == 1 ? "Sí" : "No").append("\n");
                        }
                        JOptionPane.showMessageDialog(null, listado.toString());
                    }
                    case 6 -> {
                        // Listar categorías activas
                        List<Categoria> categorias = controlador.listarCategoriasActivas();
                        StringBuilder listado = new StringBuilder("=== Categorías Activas ===\n");
                        for (Categoria categoria : categorias) {
                            listado.append("ID: ").append(categoria.getIdCategoria())
                                    .append(", Descripción: ").append(categoria.getDescripcion())
                                    .append(", Tipo: ").append(categoria.getTipo()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, listado.toString());
                    }
                    case 7 -> JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } while (opcion != 6);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                conexionMysql.close();
            }
        }
    }
}
