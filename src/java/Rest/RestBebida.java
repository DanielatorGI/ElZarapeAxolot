package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.controller.ControllerBebida;
import org.utl.dsm.model.Bebida;

import java.util.List;

@Path("bebida")
public class RestBebida extends Application {

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertBebida(
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("descripcion") @DefaultValue("") String descripcion,
            @FormParam("foto") @DefaultValue("") String foto,
            @FormParam("precio") @DefaultValue("0") double precio,
            @FormParam("idCategoria") @DefaultValue("0") int idCategoria,
            @FormParam("activo") @DefaultValue("1") int activo
    ) {
        ControllerBebida ca = new ControllerBebida();
        Gson gson = new Gson();

        try {
            ca.insertBebida(nombre, descripcion, foto, precio, idCategoria, activo);
            String out = gson.toJson("bebida insertada correctamente");
            return Response.status(Response.Status.CREATED).entity(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al insertar la bebida");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBebidas() {
        ControllerBebida ca = new ControllerBebida();
        Gson gson = new Gson();
        List<Bebida> lista;

        try {
            lista = ca.getAllBebidas();
            String out = gson.toJson(lista);
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al obtener la lista de bebidas");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBebida(
            @FormParam("idBebida") @DefaultValue("0") int idBebida,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("descripcion") @DefaultValue("") String descripcion,
            @FormParam("foto") @DefaultValue("") String foto,
            @FormParam("precio") @DefaultValue("0") double precio,
            @FormParam("idCategoria") @DefaultValue("0") int idCategoria,
            @FormParam("activo") @DefaultValue("1") int activo
    ) {
        ControllerBebida ca = new ControllerBebida();
        Gson gson = new Gson();

        try {
            ca.updateBebida(idBebida, nombre, descripcion, foto, precio, idCategoria, activo);
            String out = gson.toJson("bebida actualizado correctamente");
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al actualizar la bebida");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBebida(@FormParam("idBebida") @DefaultValue("0") int idBebida) {
        ControllerBebida ca = new ControllerBebida();
        Gson gson = new Gson();

        try {
            ca.deleteBebida(idBebida);
            String out = gson.toJson("Bebida eliminada correctamente");
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al eliminar la bebida");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}