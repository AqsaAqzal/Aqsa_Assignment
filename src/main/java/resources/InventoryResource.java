package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import domain.Item;
import services.InventoryService;

import java.sql.SQLException;


@Path("/inventory")
public class InventoryResource {

    private Item item = new Item();
    private Gson obj = new Gson();
    private InventoryService is = new InventoryService();

    @POST
    @Path("/add")
    public Response addItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        is.insertNewItem(item);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteItem(@PathParam("id") int id) throws SQLException {
        is.removeItem(id);
        return Response.ok().build();
    }


    @GET
    @Path("/getById/{id}")
    public String  getItemById(@PathParam("id") int id) throws SQLException {
        String itemData = is.readItemById(id);
        return itemData;
    }

    @GET
    @Path("/getAll")
    public String getAllItems() throws SQLException {
        String itemData = is.readAllItems();
        return itemData;
    }

    @PUT
    @Path("/update")
    public Response updateItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        is.updateItem(item);
        return Response.ok().build();
    }
}
