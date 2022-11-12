package resources;

import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
public class InventoryResourceTest {

    /**
     * junit for insert new item to a table method
     */
    @Test
    public void insertIntoTable() {

        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        String payload = "    \"itemId\": 2,\n" +
                "    \"itemName\": \"iphone\",\n" +
                "    \"itemQuantity\": 2,\n" +
                "    \"itemCategoryId\": 1,\n" +
                "    \"itemLocationId\": 1\n";

        try {
            assertEquals(Response.ok().build(), ir.addItem(request, payload));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for update a record in table method
     */
    @Test
    public void updateTable() {
        ContainerRequestContext request = null;
        InventoryResource ir = new InventoryResource();
        String payload = "    \"itemId\": 1,\n" +
                "    \"itemName\": \"hp\"\n";

        try {
            assertEquals(Response.ok().build(), ir.updateItem(request, payload));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for delete a record from table method
     * @throws SQLException
     */
  @Test
    public void deleteFromTable() throws SQLException {

        InventoryResource ir = new InventoryResource();
      ContainerRequestContext request = null;
      int id = 3;
        Response res = ir.deleteItem(request, id);
        try {
            assertEquals( 200, res.getStatus());
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }
    /**
     * junit for getting the record of an item with id=3
     */
    @Test
    public void itemWithId3() {

        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        int id = 3;
        String str = "{\"itemId\":3,\"itemName\":\"samsung galaxy\",\"itemQuantity\":33,\"itemCategoryId\":1,\"itemLocationId\":2}";

        try {
            assertEquals(str, ir.getItemById(request, id));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for getting the records of all present items in table
     */


    @Test
    public void allItemsOfTable() {

        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        String str = "[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3},{\"itemId\":2,\"itemName\":\"iphone\",\"itemQuantity\":2,\"itemCategoryId\":1,\"itemLocationId\":2}]";

        try {
            assertEquals(str, ir.getAllItems(request));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }


    /**
     * junit for getting the records with category_id=2
     */
    @Test
    public void itemWithCategoryIdequalsTo2() {
        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        String  str ="[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3}]";
        int category = 2;

        try {
            assertEquals(str, ir.getItemsByCategory(request, category));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for getting the record/s of item/s with location_id=3
     */
    @Test
    public void itemWithLocationIdequalsTo3() {
        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        String  str = "[{\"itemId\":1,\"itemName\":\"hp\",\"itemQuantity\":5,\"itemCategoryId\":2,\"itemLocationId\":3}]";
        int location = 3;

        try {
            assertEquals(str, ir.getItemsByLocation(request, location));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for getting the record/s of item/s with location_id=2 and category_id=1
     */

    @Test
    public void itemWithLocationIdequalsTo2AndCategoryEqualsTo1() {
        InventoryResource ir = new InventoryResource();
        ContainerRequestContext request = null;
        String str = "[{\"itemId\":2,\"itemName\":\"iphone\",\"itemQuantity\":2,\"itemCategoryId\":1,\"itemLocationId\":2}]";
        int location = 2;
        int category =1;

        try {
            assertEquals(str, ir.getItemsByLocationAndCategory(request, location, category));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * junit for a negative test case for not getting all records from table
     */

    /*
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
     */

}

