package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import domain.Item;
import services.InventoryService;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/inventory")
public class InventoryResource {

    private Item item = new Item();
    private Gson obj = new Gson();
    private InventoryService is = new InventoryService();
    private ArrayList<Item> items = new ArrayList<Item>();

    /***
     * adds a new record of item in Inventory table
     * @param payload contains the properties of an item in json format
     * @return the method returns response with status 200 after successfully adding a new record in table
     * @throws SQLException
     */
    @POST
    @Path("/add")
    public Response addItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        is.insertNewItem(item);
        return Response.ok().build();
    }

    /**
     * deletes a record from Inventory table provided the item id
     * @param id item id of the item that is requested to be deleted
     * @returnthe method returns a response with status 200 after successfully deleting a record from the table
     * @throws SQLException
     */
    @DELETE
    @Path("/delete/{id}")
    public Response deleteItem(@PathParam("id") int id) throws SQLException {
        is.removeItem(id);
        return Response.ok().build();
    }

    /**
     * reads a specific record of an item specified by item id and returns it as response in the form of json
     * @param id item id of an item that is requested as response
     * @return the method returns a record from table in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/getById/{id}")
    public String  getItemById(@PathParam("id") int id) throws SQLException {
        Item itemData = is.readItemById(id);
        String itemJson = obj.toJson(itemData, Item.class);
        return itemJson;
    }

    /**
     * reads all records of items from inventory table and returns it as response
     * @return a list of all items in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/getAll")
    public String getAllItems() throws SQLException {
        items = is.readAllItems();
        String itemsJson = obj.toJson(items);
        return itemsJson;
    }

    /**
     * takes a payload and updates a particular record in table
     * @param payload contains the properties of an item that are to be updated on the basis of id
     * @return returns a response object with status 200 after successfully updating a record
     * @throws SQLException
     */
    @PUT
    @Path("/update")
    public Response updateItem(String payload) throws SQLException {
        item = obj.fromJson(payload, Item.class);
        is.updateItem(item);
        return Response.ok().build();
    }

    /**
     *reads record/s on the basis of category id and returns it as response
     * @param category category id on the basis of which records are to be fetched
     * @return returns a list of items of a specific category in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/getByCategory/{category}")
    public String  getItemsByCategory(@PathParam("category") int category) throws SQLException {
        items = is.readItemsByCategory(category);
        String str = obj.toJson(items);
        return str;
    }

    /**
     *reads record/s on the basis of location id and returns it as response
     * @param location location id on the basis of which records are to be fetched
     * @return returns a list of items of a specific location in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/getByLocation/{location}")
    public String  getItemsByLocation(@PathParam("location") int location) throws SQLException {
        items = is.readItemsByLocation(location);
        String str = obj.toJson(items);
        return str;
    }

    /**
     * reads record/s on the basis of location and category id and returns it as response=
     * @param location -location id on the basis of which records are to be fetched
     * @param category category id on the basis of which records are to be fetched
     * @return returns a list of items of a specific location and category in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/getByLocationandCategory/{location}/{category}")
    public String  getItemsByLocationAndCategory(@PathParam("location") int location, @PathParam("category") int category) throws SQLException {
        items = is.readItemsByLocationandCategory(location, category);
        String str = obj.toJson(items);
        return str;
    }

}
