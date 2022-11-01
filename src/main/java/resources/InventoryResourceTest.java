package resources;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.runner.BaseTestRunner;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class InventoryResourceTest {

    @Test
    public void insertIntoTable() {

        InventoryResource ir = new InventoryResource();
        String payload = "    \"itemId\": 2,\n" +
                "    \"itemName\": \"iphone\",\n" +
                "    \"itemQuantity\": 2,\n" +
                "    \"itemCategoryId\": 1,\n" +
                "    \"itemLocationId\": 1\n";

        try {
            assertEquals(Response.ok().build(), ir.addItem(payload));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }


    @Test
    public void updateTable() {

        InventoryResource ir = new InventoryResource();
        String payload = "    \"itemId\": 1,\n" +
                "    \"itemName\": \"hp\"\n";

        try {
            assertEquals(Response.ok().build(), ir.updateItem(payload));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     *
     * @throws SQLException
     */
   @Test
    public void deleteFromTable() throws SQLException {

        InventoryResource ir = new InventoryResource();
        int id = 3;
        Response res = ir.deleteItem(id);
        try {
            assertEquals( 200, res.getStatus());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void itemWithId3() {

        InventoryResource ir = new InventoryResource();
        int id = 3;
        String str = "{\"itemId\":3,\"itemName\":\"samsung galaxy\",\"itemQuantity\":33,\"itemCategoryId\":1,\"itemLocationId\":2}";

        try {
            assertEquals(str, ir.getItemById(id));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void allItemsOfTable() {

        InventoryResource ir = new InventoryResource();
        String str = "[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3},{\"itemId\":2,\"itemName\":\"iphone\",\"itemQuantity\":2,\"itemCategoryId\":1,\"itemLocationId\":2}]";

        try {
            assertEquals(str, ir.getAllItems());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void itemWithCategoryIdequalsTo2() {
        InventoryResource ir = new InventoryResource();
        String  str ="[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3}]";
        int category = 2;

        try {
            assertEquals(str, ir.getItemsByCategory(category));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void itemWithLocationIdequalsTo3() {
        InventoryResource ir = new InventoryResource();
        String  str = "[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3}]";
        int location = 3;

        try {
            assertEquals(str, ir.getItemsByLocation(location));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void itemWithLocationIdequalsTo2AndCategoryEqualsTo1() {
        InventoryResource ir = new InventoryResource();
        String str = "[{\"itemId\":2,\"itemName\":\"iphone\",\"itemQuantity\":2,\"itemCategoryId\":1,\"itemLocationId\":2}]";
        int location = 2;
        int category =1;

        try {
            assertEquals(str, ir.getItemsByLocationAndCategory(location, category));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void notAllItemsOfTable() {

        InventoryResource ir = new InventoryResource();
        String str = "[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3}]";

        try {
            assertNotEquals(str, ir.getAllItems());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }


}

