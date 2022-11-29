import com.google.gson.Gson;
import domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import resources.InventoryResource;
import services.InventoryService;
import services.InventoryServiceImpl;
import util.ApplicationClass;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;

public class InventoryTest {

    @Test
    public void addNewInventoryItemTest() {
        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        String payload = "{\n" +
                "    \"item_name\": \"tester3\",\n" +
                "    \"item_quantity\": 10,\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";
        Gson jsonObj = new Gson();
        Inventory inventory = jsonObj.fromJson(payload, Inventory.class);

        try {
            Assertions.assertEquals(Response.ok(jsonObj.toJson(inventory)).build(), inventoryResource.addNewInventoryItem(request, payload));

        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void addNewInventoryItemWithBadRequestExceptionTest() {
        final InventoryResource inventoryResource = new InventoryResource();
        final ContainerRequestContext request = null;
        final String payload = "{\n" +
                "    \"item_quantity\": 10,\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";
        Gson jsonObj = new Gson();
        InventoryService inventoryService = new InventoryServiceImpl();
        Inventory inventory = jsonObj.fromJson(payload, Inventory.class);
        ErrorResponse errorResponse = new ErrorResponse("Item name field is null");
        try {
            Assertions.assertThrows(NullPointerException.class, new Executable() {
                public void execute() throws Throwable {
                    inventoryResource.addNewInventoryItem(request, payload);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void negativeInsertIntoTable() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        String payload =  "{\n" +
                "    \"id\": 6,\n" +
                "    \"item_quantity\": 10,\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";

        try {
            Assertions.assertEquals(Response.status(400).build(), inventoryResource.addNewInventoryItem(request, payload));

        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void deleteFromTable() {

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
    public void deleteFromTableException() {

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
        ContainerRequestContext request = null;
        InventoryResource inventoryResource = new InventoryResource();
        int id = 5;
        String str = "{\"id\":5,\"item_name\":\"macbook\",\"item_quantity\":20,\"item_category\":{\"id\":2,\"category_name\":\"laptop\"},\"item_location\":{\"id\":2,\"location_name\":\"India\"}}";

        try {
            inventoryResource.fetchInventoryItemById(request, id);
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
            Assertions.assertEquals(str, inventoryResource.fetchAllInventoryItems(request, category, location));
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
            Assertions.assertEquals(Response.ok().build(), inventoryResource.updateExistingInventoryItem(request, payload, id));

        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void notUpdateInventory() {

        InventoryResource inventoryResource = new InventoryResource();
        ContainerRequestContext request = null;
        int id = 5;
        String payload =  "{\n" +
                "    \"item_name\": \"tester\",\n" +
                "    \"item_category\": {\n" +
                "    \"id\": 2\n" +
                "    },\n" +
                "    \"item_location\": {\n" +
                "        \"id\": 3\n" +
                "    }\n" +
                "}";

        try {
            Assertions.assertEquals(Response.status(400).build(), inventoryResource.updateExistingInventoryItem(request, payload, id));

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
            applicationClass.loadUsersFromDb();
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void testInsertAuth() {

        InventoryResource inventoryResource = new InventoryResource();
        InventoryService inventoryService = new InventoryServiceImpl();
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
            Assertions.assertEquals(Response.status(401).build(), inventoryResource.addNewInventoryItem(request, payload));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void insertNewInventoryItemTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        final Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(8);
        inventory.setItem_name("tester3");
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        inventory.setItem_location(itemLocation);

        try {
                    inventoryService.insertNewInventoryItem(inventory);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }


    @Test
    public void updateExistingInventoryItemTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        final Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(8);
        inventory.setItem_name("tester3");
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        inventory.setItem_location(itemLocation);

        try {
                    inventoryService.updateExistingInventoryItem(inventory);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void isAuthorizedTest() {
        InventoryService inventoryService = new InventoryServiceImpl();
        String authHeader = "Basic YXFzYToxMjM=";
        try {
            Assertions.assertEquals(false, inventoryService.isAuthorized(authHeader));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void readAllInventoryItemsWithCategoryAndLocation2Test() {
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
        inventory.setItem_location(itemLocation);

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
        inventory.setItem_location(itemLocation2);
        inventories.add(inventory2);
        inventories.add(inventory2);

        try {
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(2,2));
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(null, null));
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(2, null));
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(null, 2));
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
        inventory.setItem_location(itemLocation);

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
        inventory.setItem_location(itemLocation2);
        inventories.add(inventory2);
        inventories.add(inventory2);
        Integer category = null, location =null;

        try {
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(category, location));
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
        inventory.setItem_location(itemLocation);
        int id = 5;

        try {
            inventoryService.readInventoryItemById(id);
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void removeExistingInventoryItemTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        try {
                    inventoryService.removeExistingInventoryItem(7);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void validateWithNameNullTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        final Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(8);
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        inventory.setItem_location(itemLocation);


        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    Inventory.validate(inventory);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void validateWithQuantityNullTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        final Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(8);
        inventory.setItem_name("tester3");
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        inventory.setItem_location(itemLocation);
        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    Inventory.validate(inventory);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void validateWithCategoryNullTest() {
        final Inventory inventory = new Inventory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(8);
        inventory.setItem_name("tester3");
        inventory.setItem_quantity(20);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");
        inventory.setItem_location(itemLocation);

        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    Inventory.validate(inventory);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void validateWithLocationNullTest() {
        final Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        inventory.setId(8);
        inventory.setItem_name("tester3");
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    Inventory.validate(inventory);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
    @Test
    public void getIdTest() {
        Inventory inventory = new Inventory();
        inventory.setId(4);
        Assertions.assertEquals(4, inventory.getId());
    }
    @Test
    public void ErrorResponseTest() {
        ErrorResponse errorResponse = new ErrorResponse("Test error");
        errorResponse.setError_message("Test error");
        Assertions.assertEquals("Test error", errorResponse.getError_message());
    }
    @Test
    public void ItemCategoryTest() {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(2);
        itemCategory.setCategory_name("phone");
        Assertions.assertEquals(2, itemCategory.getId());
        Assertions.assertEquals("phone", itemCategory.getCategory_name());
    }
    @Test
    public void ItemLocationTest() {
        ItemLocation itemLocation = new ItemLocation();
        itemLocation.setId(2);
        itemLocation.setLocation_name("Pakistan");
        Assertions.assertEquals(2, itemLocation.getId());
        Assertions.assertEquals("Pakistan", itemLocation.getLocation_name());
    }
    @Test
    public void UserTest() {
        User user = new User();
        user.setUsername("aqsa");
        user.setPassword("123");
        Assertions.assertEquals("aqsa", user.getUsername());
        Assertions.assertEquals("123", user.getPassword());
    }
}
