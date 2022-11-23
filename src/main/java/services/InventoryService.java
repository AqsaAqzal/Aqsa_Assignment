package services;

import domain.Inventory;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryService {
    boolean isAuthorized(String authHeader);

    void insertNewInventoryItem(Inventory item) throws SQLException;

    void removeExistingInventoryItem(Integer id) throws SQLException, ClassNotFoundException;

    Inventory readInventoryItemById(int id) throws SQLException;

    void updateExistingInventoryItem(Inventory item) throws SQLException;

    ArrayList<Inventory> readAllInventoryItems(Integer location, Integer category) throws SQLException;
}
