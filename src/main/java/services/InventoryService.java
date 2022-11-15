package services;

import domain.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryService {
    boolean isAuthorized(String authHeader);

    void insertNewItem(Item item) throws SQLException;

    void removeItem(int id) throws SQLException;

    Item readItemById(int id) throws SQLException;

    ArrayList<Item> readAllItems() throws SQLException;

    void updateItem(Item item) throws SQLException;

    ArrayList<Item> readItemsByCategory(int category) throws SQLException;

    ArrayList<Item> readItemsByLocation(int location) throws SQLException;

    ArrayList<Item> readItemsByLocationandCategory(int location, int category) throws SQLException;
}
