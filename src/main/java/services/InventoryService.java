package services;

import domain.Inventory;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryService {
    boolean isAuthorized(String authHeader);

    void insertNewItem(Inventory item) throws SQLException;

    void removeItem(int id) throws SQLException;

    ArrayList<Inventory> readItemById(int id) throws SQLException;

    ArrayList<Inventory> readAllItems() throws SQLException;

    void updateItem(Inventory item, int id) throws SQLException;

    ArrayList<Inventory> readItemsByLocationandCategory(int location, int category) throws SQLException;
}
