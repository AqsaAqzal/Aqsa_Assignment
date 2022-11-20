package services;

import domain.Inventory;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryService {
    boolean isAuthorized(String authHeader);

    void insertNewInventoryItem(Inventory item) throws SQLException;

    void removeExistingInventoryItem(int id) throws SQLException;

    Inventory readInventoryItemById(int id) throws SQLException;

    void updateExistingInventoryItem(Inventory item, int id) throws SQLException;

    ArrayList<Inventory> readAllInventoryItems(int location, int category) throws SQLException;
}
