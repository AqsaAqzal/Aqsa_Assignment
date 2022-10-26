package resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
