package resources;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import domain.Inventory;
import services.InventoryService;
import services.InventoryServiceImpl;
import java.sql.SQLException;
import java.util.ArrayList;
@Path("/inventory")
public class InventoryResource {

    private Gson jsonObj = new Gson();
    private InventoryService inventoryService = new InventoryServiceImpl();

    /***
     * adds a new record of item in Inventory table
     * @param payload contains the properties of an item in json format
     * @return the method returns response with status 200 after successfully adding a new record in table
     * @throws SQLException
     */
    @POST
    @Path("/add")
    public Response addNewInventoryItem(@Context ContainerRequestContext request, String payload) throws SQLException {
        Inventory inventory = new Inventory();
        String authHeader = request.getHeaders().getFirst("authorization");
        inventory = jsonObj.fromJson(payload, Inventory.class);
       if(inventoryService.isAuthorized(authHeader)) {
            inventoryService.insertNewItem(inventory);
            return Response.ok(payload).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    /**
     * deletes a record from Inventory table provided the item id
     * @param id item id of the item that is requested to be deleted
     * @returnthe method returns a response with status 200 after successfully deleting a record from the table
     * @throws SQLException
     */
    @DELETE
    @Path("/{inventory_id}")
    public Response deleteExistingInventoryItem(@Context ContainerRequestContext request, @PathParam("inventory_id") int id) throws SQLException {
        String authHeader = request.getHeaders().getFirst("authorization");
        if(inventoryService.isAuthorized(authHeader)) {
        inventoryService.removeItem(id);
            return Response.ok("OK").build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    /**
     * reads a specific record of an item specified by item id and returns it as response in the form of json
     * @param id item id of an item that is requested as response
     * @return the method returns a record from table in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/{inventory_id}")
    public Response fetchInventoryItemById(@Context ContainerRequestContext request, @PathParam("inventory_id") int id) throws SQLException {
        String authHeader = request.getHeaders().getFirst("authorization");
        ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
        if(inventoryService.isAuthorized(authHeader)) {
        inventoryList = inventoryService.readItemById(id);
        String response = jsonObj.toJson(inventoryList);
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    /**
     * reads all records of items from inventory table and returns it as response
     * @return a list of all items in the form of json
     * @throws SQLException
     */
    @GET
    @Path("/list")
    public Response fetchAllInventoryItems(@Context ContainerRequestContext request) throws SQLException {
        String authHeader = request.getHeaders().getFirst("authorization");
        if (inventoryService.isAuthorized(authHeader)) {
            ArrayList<Inventory> inventoryList = inventoryService.readAllItems();
            String response = jsonObj.toJson(inventoryList);
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    /**
     * takes a payload and updates a particular record in table
     * @param payload contains the properties of an item that are to be updated on the basis of id
     * @return returns a response object with status 200 after successfully updating a record
     * @throws SQLException
     */
    @PUT
    @Path("/{inventory_id}")
    public Response updateExistingInventoryItem(@Context ContainerRequestContext request, String payload, @PathParam("inventory_id") int id) throws SQLException {
        String authHeader = request.getHeaders().getFirst("authorization");
        Inventory inventory = new Inventory();
        inventory = jsonObj.fromJson(payload, Inventory.class);
        if(inventoryService.isAuthorized(authHeader)) {
            inventoryService.updateItem(inventory, id);
            return Response.ok(payload).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

    /**
     * reads record/s on the basis of location and category id and returns it as response=
     * @param location -location id on the basis of which records are to be fetched
     * @param category category id on the basis of which records are to be fetched
     * @return returns a list of items of a specific location and category in the form of json
     * @throws SQLException
     * */

    @GET
    @Path("/list/categoryAndLocation")
    public Response  fetchAllInventoryItemsByLocationAndCategory(@Context ContainerRequestContext request, @QueryParam("category") int category, @QueryParam("location") int location) throws SQLException {
        String authHeader = request.getHeaders().getFirst("authorization");
        ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
        if(inventoryService.isAuthorized(authHeader)) {
        inventoryList = inventoryService.readItemsByLocationandCategory(location, category);
        String response = jsonObj.toJson(inventoryList);
            return Response.status(200).entity(response).build();
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
    }

}
