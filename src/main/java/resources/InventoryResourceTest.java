package resources;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.runner.BaseTestRunner;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;


public class InventoryResourceTest extends TestCase{

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
}

