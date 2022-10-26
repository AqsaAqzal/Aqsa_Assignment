package resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


@Path("/inventory")

public class InventoryResource {

    @POST
    @Path("/add")
    public Response addItem() {

    }

}
