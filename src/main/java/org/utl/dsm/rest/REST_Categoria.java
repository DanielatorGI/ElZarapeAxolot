package org.utl.dsm.rest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Moises
 */
import Controlador.C_Categoria;
import Modelo.Categoria;
import Conexion.ConexionMySQL;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/categorias")
public class REST_Categoria {
    private final C_Categoria controlador;

    public REST_Categoria() {
        ConexionMySQL conexionMysql = new ConexionMySQL();
        Connection connection = conexionMysql.open();
        controlador = new C_Categoria(connection);
    }

    @GET
    @Path("lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCategorias() {
        try {
            List<Categoria> categorias = controlador.listarCategorias();
            return Response.ok(categorias).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener las categorías").build();
        }
    }

    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarCategoria(Categoria categoria) {
        try {
            boolean agregado = controlador.insertarCategoria(categoria);
            if (agregado) {
                return Response.ok("Categoría agregada exitosamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error al agregar la categoría").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar la categoría").build();
        }
    }

    @PUT
    @Path("/actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarCategoria(Categoria categoria) {
        try {
            boolean actualizado = controlador.actualizarCategoria(categoria);
            if (actualizado) {
                return Response.ok("Categoría actualizada exitosamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error al actualizar la categoría").build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar la categoría").build();
        }
    }

@POST
@Path("/eliminar")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response eliminarCategoria(Categoria categoria) {
    try {
        boolean eliminado = controlador.eliminarCategoria(categoria.getIdCategoria());
        if (eliminado) {
            return Response.ok("Categoría eliminada exitosamente").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al eliminar la categoría").build();
        }
    } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar la categoría").build();
    }
}
}
