package resources;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import domain.Inventory;
import domain.ErrorResponse;
import services.InventoryService;
import services.InventoryServiceImpl;
import java.util.ArrayList;
@Path("/inventory")
public class InventoryResource {

    private final Gson jsonObj = new Gson();
    private final InventoryService inventoryService = new InventoryServiceImpl();


    /***
     * adds a new record of item in Inventory table
     * @param payload contains the properties of an item in json format
     * @return the method returns response with status 200 after successfully adding a new record in table
     */
    @POST
    public Response addNewInventoryItem(@Context ContainerRequestContext request, String payload) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if (!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Inventory inventory = new Inventory();
        inventory = jsonObj.fromJson(payload, Inventory.class);
            try {
                inventoryService.insertNewInventoryItem(inventory);
            } catch (BadRequestException badRequestException) {
                ErrorResponse errorResponse = new ErrorResponse(badRequestException.getMessage());
                return Response.status(400).entity(jsonObj.toJson(errorResponse)).build();
            } catch (Exception ex) {
                ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");
                return Response.status(500).entity(jsonObj.toJson(errorResponse)).build();
            }
            return Response.ok(jsonObj.toJson(inventory)).build();
        }

    /**
     * takes a payload and updates a particular record in table
     *
     * @param payload contains the properties of an item that are to be updated on the basis of id
     * @return returns a response object with status 200 after successfully updating a record
     */

    @PUT
    @Path("/{inventory_id}")
    public Response updateExistingInventoryItem(@Context ContainerRequestContext request, String payload, @PathParam("inventory_id") int id) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if (!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Inventory inventory = new Inventory();
        inventory = jsonObj.fromJson(payload, Inventory.class);
        inventory.setId(id);
        try {
            inventoryService.updateExistingInventoryItem(inventory);
        } catch (BadRequestException badRequestException) {
            ErrorResponse errorResponse = new ErrorResponse(badRequestException.getMessage());
            errorResponse.setError_message(badRequestException.getMessage());
            return Response.status(400).entity(jsonObj.toJson(errorResponse)).build();
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");
            return Response.status(500).entity(jsonObj.toJson(errorResponse)).build();
        }
        return Response.ok(jsonObj.toJson(inventory)).build();
    }

    /**
     * reads a specific record of an item specified by item id and returns it as response in the form of json
     *
     * @param id item id of an item that is requested as response
     * @return the method returns a record from table in the form of json
     */
    @GET
    @Path("/{inventory_id}")
    public Response fetchInventoryItemById(@Context ContainerRequestContext request, @PathParam("inventory_id") int id) {
        String authHeader = request.getHeaders().getFirst("authorization");
        if (!inventoryService.isAuthorized(authHeader)) {
            return Response.status(401).entity("Unauthorized").build();
        }
        Inventory inventory = new Inventory();
        try {
            inventory = inventoryService.readInventoryItemById(id);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");
            return Response.status(500).entity(jsonObj.toJson(errorResponse)).build();
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

        @GET
        @Path("/list")
        public Response fetchAllInventoryItems(@Context ContainerRequestContext request, @QueryParam("category") Integer category, @QueryParam("location") Integer location){
            String authHeader = request.getHeaders().getFirst("authorization");
            if (!inventoryService.isAuthorized(authHeader)) {
                return Response.status(401).entity("Unauthorized").build();
            }
            ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
            try {
                inventoryList = inventoryService.readAllInventoryItems(location, category);
            } catch (Exception ex) {
                ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");
                return Response.status(500).entity(jsonObj.toJson(errorResponse)).build();
                // todo handle error response as per assignment - create new Error class
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
        public Response deleteExistingInventoryItem (@Context ContainerRequestContext request, @PathParam("inventory_id") Integer id){
            String authHeader = request.getHeaders().getFirst("authorization");
            if (!inventoryService.isAuthorized(authHeader)) {
                return Response.status(401).entity("Unauthorized").build();
            }
                try {
                    inventoryService.removeExistingInventoryItem(id);
                } catch (Exception ex) {
                    ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");
                    return Response.status(500).entity(jsonObj.toJson(errorResponse)).build();
                }
                return Response.ok("OK").build();
            }
    }
