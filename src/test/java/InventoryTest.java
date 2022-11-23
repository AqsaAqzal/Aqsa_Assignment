import org.junit.jupiter.api.Test;
import resources.InventoryResource;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

public class InventoryTest {

    @Test
    public void insertIntoTable() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        String payload =  "{\n" +
                "    \"id\": 6,\n" +
                "    \"item_name\": \"tester\",\n" +
                "    \"item_quantity\": 10,\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";

        try {
            assertEquals(Response.ok().build(), inventoryResource.addNewInventoryItem(request, payload));

        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void deleteFromTable() throws SQLException {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        int id = 6;
        try {
            Response res = inventoryResource.deleteExistingInventoryItem(request, id);
            assertEquals( 200, res.getStatus());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void deleteFromTableException() throws SQLException {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        HttpHeaders header = null;
        int id = -1;
        try {
            Response res = inventoryResource.deleteExistingInventoryItem(request, id);
            assertEquals( 500, res.getStatus());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void itemWithId5() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        int id = 5;
        String str = "{\"id\":5,\"item_name\":\"macbook\",\"item_quantity\":20,\"item_category\":{\"id\":2,\"category_name\":\"laptop\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}}";

        try {
            assertEquals(str, inventoryResource.fetchInventoryItemById(request, id));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void allItems() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        int category = 0, location = 2;
        String str = "[{\"id\":1,\"item_name\":\"hp\",\"item_quantity\":5,\"item_category\":{\"id\":2,\"category_name\":\"laptop\"},\"item_location\":{\"id\":3,\"location_name\":\"US\"}},{\"id\":2,\"item_name\":\"iphone\",\"item_quantity\":2,\"item_category\":{\"id\":1,\"category_name\":\"phone\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}},{\"id\":3,\"item_name\":\"samsung galaxy\",\"item_quantity\":6,\"item_category\":{\"id\":1,\"category_name\":\"phone\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}},{\"id\":4,\"item_name\":\"lenovo\",\"item_quantity\":20,\"item_category\":{\"id\":2,\"category_name\":\"laptop\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}},{\"id\":5,\"item_name\":\"macbook\",\"item_quantity\":20,\"item_category\":{\"id\":2,\"category_name\":\"laptop\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}}]";

        try {
            assertEquals(str, inventoryResource.fetchAllInventoryItems(request, category, location));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void updateInventory() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        int id = 5;
        String payload =  "{\n" +
                "    \"item_name\": \"tester\",\n" +
                "    \"item_quantity\": 10,\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";

        try {
            assertEquals(Response.ok().build(), inventoryResource.updateExistingInventoryItem(request, payload, id));

        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }


}
