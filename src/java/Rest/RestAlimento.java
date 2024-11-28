package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.controller.ControllerAlimento;
import org.utl.dsm.model.Alimento;

import java.util.List;

@Path("alimento")
public class RestAlimento extends Application {

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertAlimento(
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("descripcion") @DefaultValue("") String descripcion,
            @FormParam("foto") @DefaultValue("") String foto,
            @FormParam("precio") @DefaultValue("0") double precio,
            @FormParam("idCategoria") @DefaultValue("0") int idCategoria,
            @FormParam("activo") @DefaultValue("1") int activo
    ) {
        ControllerAlimento ca = new ControllerAlimento();
        Gson gson = new Gson();

        try {
            ca.insertAlimento(nombre, descripcion, foto, precio, idCategoria, activo);
            String out = gson.toJson("Alimento insertado correctamente");
            return Response.status(Response.Status.CREATED).entity(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al insertar el alimento");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAlimentos() {
        ControllerAlimento ca = new ControllerAlimento();
        Gson gson = new Gson();
        List<Alimento> lista;

        try {
            lista = ca.getAllAlimentos();
            String out = gson.toJson(lista);
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al obtener la lista de alimentos");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAlimento(
            @FormParam("idAlimento") @DefaultValue("0") int idAlimento,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("descripcion") @DefaultValue("") String descripcion,
            @FormParam("foto") @DefaultValue("") String foto,
            @FormParam("precio") @DefaultValue("0") double precio,
            @FormParam("idCategoria") @DefaultValue("0") int idCategoria,
            @FormParam("activo") @DefaultValue("1") int activo
    ) {
        ControllerAlimento ca = new ControllerAlimento();
        Gson gson = new Gson();

        try {
            ca.updateAlimento(idAlimento, nombre, descripcion, foto, precio, idCategoria, activo);
            String out = gson.toJson("Alimento actualizado correctamente");
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al actualizar el alimento");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlimento(@FormParam("idAlimento") @DefaultValue("0") int idAlimento) {
        ControllerAlimento ca = new ControllerAlimento();
        Gson gson = new Gson();

        try {
            ca.deleteAlimento(idAlimento);
            String out = gson.toJson("Alimento eliminado correctamente");
            return Response.ok(out).build();
        } catch (Exception e) {
            e.printStackTrace();
            String error = gson.toJson("Error al eliminar el alimento");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
