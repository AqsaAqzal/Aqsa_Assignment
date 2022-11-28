package services;

import domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

public class InventoryServiceImplTest {

    @Test
    public void insertNewInventoryItemTest() {
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

        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    inventoryService.insertNewInventoryItem(inventory);
                }
            });
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
        inventory.setItem_quantity(20);
        itemCategory.setId(2);
        itemCategory.setCategory_name("laptop");
        inventory.setItem_category(itemCategory);
        itemLocation.setId(2);
        itemLocation.setLocation_name("India");

        try {
            Assertions.assertThrows(BadRequestException.class, new Executable() {
                public void execute() throws Throwable {
                    inventoryService.updateExistingInventoryItem(inventory);
                }
            });
        } catch (Exception ex) {
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
            Assertions.assertEquals(authCheck, inventoryService.isAuthorized(authHeader));
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

        try {
            Assertions.assertEquals(inventories, inventoryService.readAllInventoryItems(2,2));
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(null, null));
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(2, null));
            Assertions.assertEquals(inventories, inventoryService.readAllInventoryItems(null, 2));
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
        Integer category = null, location =null;

        try {
            Assertions.assertNotEquals(inventories, inventoryService.readAllInventoryItems(category, location));
            Assertions.assertNotNull(category);
            Assertions.assertNotNull(location);
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
            Assertions.assertEquals(inventory, inventoryService.readInventoryItemById(id));
        }
        catch(Exception ex) {
            ex.getStackTrace();
        }
    }

    @Test
    public void removeExistingInventoryItemTest() {
        final InventoryService inventoryService = new InventoryServiceImpl();
        try {
            Assertions.assertThrows(Exception.class, new Executable() {
                public void execute() throws Throwable {
                    inventoryService.removeExistingInventoryItem(20);
                }
            });
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
}
