package resources;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import domain.Inventory;
import services.InventoryService;
import services.InventoryServiceImpl;
import java.util.ArrayList;
@Path("/inventory")
public class InventoryResource {

    private final Gson jsonObj = new Gson();
    private final InventoryService inventoryService = new InventoryServiceImpl();
    Inventory inventory = new Inventory();
    ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();

    /***
     * adds a new record of item in Inventory table
     * @param payload contains the properties of an item in json format
     * @return the method returns response with status 200 after successfully adding a new record in table
     */
    @POST
    public Response addNewInventoryItem(@Context ContainerRequestContext request, String payload) { //todo handle exception in catch block
        String authHeader = request.getHeaders().getFirst("authorization");
        inventory = jsonObj.fromJson(payload, Inventory.class);
       if(!inventoryService.isAuthorized(authHeader)) {
           return Response.status(401).entity("Unauthorized").build();//todo move authorization above
        }
       try {
           inventoryService.insertNewInventoryItem(inventory);
       }
       catch (Exception ex) {
           return Response.status(500).entity("Internal Server Error").build();
       }
        return Response.ok(payload).build();
    }

    /**
     * takes a payload and updates a particular record in table
     * @param payload contains the properties of an item that are to be updated on the basis of id
     * @return returns a response object with status 200 after successfully updating a record
     */
    @PUT
    @Path("/{inventory_id}")
    public Response updateExistingInventoryItem(@Context ContainerRequestContext request, String payload, @PathParam("inventory_id") int id) {
        String authHeader = request.getHeaders().getFirst("authorization");
        inventory = jsonObj.fromJson(payload, Inventory.class);
        if(!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        try {
            inventoryService.updateExistingInventoryItem(inventory, id);
        }
        catch (Exception ex) {
            return Response.status(500).entity("Internal Server Error").build();
        }
        return Response.ok(payload).build();
    }

    /**
     * reads a specific record of an item specified by item id and returns it as response in the form of json
     * @param id item id of an item that is requested as response
     * @return the method returns a record from table in the form of json
     */
    @GET
    @Path("/{inventory_id}")
    public Response fetchInventoryItemById(@Context ContainerRequestContext request, @PathParam("inventory_id") int id) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if(!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        try {
            inventory = inventoryService.readInventoryItemById(id);
        }
        catch (Exception ex) {
            return Response.status(500).entity("Internal Server Error").build();
        }
        String response = jsonObj.toJson(inventory);
        return Response.status(200).entity(response).build();
    }

    /**
     * reads record/s on the basis of location and category id and returns it as response=
     * @param location -location id on the basis of which records are to be fetched
     * @param category category id on the basis of which records are to be fetched
     * @return returns a list of items of a specific location and category in the form of json
     * */

    //todo remove this API and move query params to /list API
    @GET
    @Path("/list")
    public Response fetchAllInventoryItems(@Context ContainerRequestContext request, @QueryParam("category") int category, @QueryParam("location") int location) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if(!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        try {
            inventoryList = inventoryService.readAllInventoryItems(location, category);
        }
        catch (Exception ex) {
            return Response.status(500).entity("Internal Server Error").build();
        }
        String response = jsonObj.toJson(inventoryList);
        return Response.status(200).entity(response).build();
    }

    /**
     * deletes a record from Inventory table provided the item id
     * @param id item id of the item that is requested to be deleted
     * @return the method returns a response with status 200 after successfully deleting a record from the table
     */
    @DELETE
    @Path("/{inventory_id}")
    public Response deleteExistingInventoryItem(@Context ContainerRequestContext request, @PathParam("inventory_id") int id) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if(!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        try {
            inventoryService.removeExistingInventoryItem(id);
        }
        catch (Exception ex) {
            return Response.status(500).entity("Internal Server Error").build();
        }
        return Response.ok("OK").build();
    }
}
