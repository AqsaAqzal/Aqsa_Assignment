import domain.Inventory;
import domain.ItemCategory;
import domain.ItemLocation;
import domain.User;
import org.junit.jupiter.api.Test;
import resources.InventoryResource;
import services.InventoryService;
import services.InventoryServiceImpl;
import util.ApplicationClass;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

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

    @Test
    public void readItemWithId5() {
        InventoryService inventoryService = new InventoryServiceImpl();
        Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(5);
        inventory.setItem_name("macbook");
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        int id = 5;

        try {
            assertEquals(inventory, inventoryService.readInventoryItemById(id));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void readAllInventoryItemsTest() {
        InventoryService inventoryService = new InventoryServiceImpl();
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(5);
        inventory.setItem_name("macbook");
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");

        Inventory inventory2 = new Inventory();
        ItemCategory itemCategory2 = new ItemCategory();
        ItemLocation itemLocation2 = new ItemLocation();
        inventory2.setId(5);
        inventory2.setItem_name("lenovo");
        inventory2.setItem_quantity(20);
        itemCategory2.setId(2);
        itemCategory2.setCategory_name("laptop");
        inventory2.setItem_category(itemCategory2);
        itemLocation2.setId(2);
        itemLocation2.setLocation_name("India");
        inventories.add(inventory2);
        inventories.add(inventory2);
        Integer category = 2, location =2;

        try {
            assertEquals(inventories, inventoryService.readAllInventoryItems(category, location));
            assertNotNull(category);
            assertNotNull(location);
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void loadUsersFromDbTest() {

        ArrayList<User> users = new ArrayList<User>();
        ApplicationClass applicationClass = new ApplicationClass();

        try {
            assertEquals(users, applicationClass.loadUsersFromDb());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void isAuthorizedTest() {
        InventoryService inventoryService = new InventoryServiceImpl();
        String authHeader = "";
        boolean authCheck = false;
        String uname = "", password = "";
        try {
            assertEquals(authCheck, inventoryService.isAuthorized(authHeader));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }


}
