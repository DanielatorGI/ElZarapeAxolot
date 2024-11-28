package Rest;

import Controlador.BebidaController;
import Modelo.Bebida;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("bebida")
public class RestBebida extends Application {

    @Path("insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertBebida(String datosBebida) {
        Gson gson = new Gson();
        Bebida bebida = gson.fromJson(datosBebida, Bebida.class);
        BebidaController bc = new BebidaController();

        try {
            boolean result = bc.insertarBebida(
                bebida.getNombreBebida(),
                bebida.getDescripcion(),
                bebida.getPrecio(),
                Integer.parseInt(bebida.getCategoria()) // Aseguramos que la categoría se maneje como int
            );

            if (result) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(bebida)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"No se pudo insertar la bebida\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al insertar la bebida\"}").build();
        }
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBebidas() {
        Gson gson = new Gson();
        BebidaController bc = new BebidaController();

        try {
            List<Bebida> bebidas = bc.listarBebidas();
            return Response.status(Response.Status.OK).entity(gson.toJson(bebidas)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al obtener las bebidas\"}").build();
        }
    }

    @Path("update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBebida(String datosBebida) {
        Gson gson = new Gson();
        Bebida bebida = gson.fromJson(datosBebida, Bebida.class);
        BebidaController bc = new BebidaController();

        try {
            boolean result = bc.actualizarBebida(
                bebida.getIdBebida(),
                bebida.getNombreBebida(),
                bebida.getDescripcion(),
                bebida.getPrecio(),
                Integer.parseInt(bebida.getCategoria())
            );

            if (result) {
                return Response.ok(gson.toJson(bebida)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"No se pudo actualizar la bebida\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al actualizar la bebida\"}").build();
        }
    }

    @Path("delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBebida(@QueryParam("idBebida") int idBebida) {
        BebidaController bc = new BebidaController();

        try {
            boolean result = bc.eliminarBebida(idBebida);
            if (result) {
                return Response.ok("{\"mensaje\":\"Bebida eliminada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"No se pudo eliminar la bebida\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al eliminar la bebida\"}").build();
        }
    }
}