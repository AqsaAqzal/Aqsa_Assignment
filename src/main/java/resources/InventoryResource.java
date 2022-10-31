package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import domain.Item;
import jdk.nashorn.internal.runtime.JSONFunctions;
import services.InventoryService;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.*;

@Path("/inventory")
public class InventoryResource {

    private Item item = new Item();
    private Gson obj = new Gson();
    private InventoryService is = new InventoryService();
    private ArrayList<Item> items = new ArrayList<Item>();

    @POST
    @Path("/add")
    public Response addItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        if(item.getItemId() === null) {
            return Response.Status.BAD_REQUEST
        }
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
        Item itemData = is.readItemById(id);
        System.out.println(itemData.getItemName());
        String payload = obj.toJson(itemData, Item.class);
        System.out.println(payload);
        return payload;
    }

    @GET
    @Path("/getAll")
    public String getAllItems() throws SQLException {
        items = is.readAllItems();
        System.out.println(items);
        String str = obj.toJson(items);
        return str;
    }

    @PUT
    @Path("/update")
    public Response updateItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        is.updateItem(item);
        return Response.ok().build();
    }

    @GET
    @Path("/getByCategory/{category}")
    public String  getItemsByCategory(@PathParam("category") int category) throws SQLException {
        items = is.readItemsByCategory(category);
        String str = obj.toJson(items);
        return str;
    }

    @GET
    @Path("/getByLocation/{location}")
    public String  getItemsByLocation(@PathParam("location") int location) throws SQLException {
        items = is.readItemsByLocation(location);
        String str = obj.toJson(items);
        return str;
    }

    @GET
    @Path("/getByLocationandCategory/{location}/{category}")
    public String  getItemsByLocationAndCategory(@PathParam("location") int location, @PathParam("category") int category) throws SQLException {
        items = is.readItemsByLocationandCategory(location, category);
        String str = obj.toJson(items);
        return str;
    }

}
