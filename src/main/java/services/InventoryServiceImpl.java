package services;

import connpooling.HikariCP;
import domain.Inventory;
import domain.ItemCategory;
import domain.ItemLocation;
import domain.User;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import util.ApplicationClass;
public class InventoryServiceImpl implements InventoryService {

    /**
     * method to check if the user is authorized or not
     *
     * @param authHeader auth details provided by client hitting the api
     * @return returns true if user is authorized else returns false
     */
    public boolean isAuthorized(String authHeader) {
        String[] headerDetails = authHeader.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(headerDetails[1]);
        String decodedString = new String(decodedBytes);
        String[] credentials = decodedString.split(":");
        String username = credentials[0], password = credentials[1];

        boolean authCheck = false;
        ArrayList<User> usersList = new ArrayList<User>();
        usersList = ApplicationClass.getUsersList();


        for (User users : usersList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                authCheck = true;
            }
        }
        return authCheck;
    }

    /**
     * a method for executing the result set
     *
     * @param rs the result set that is to be executed
     * @return a list of items in result set
     * @throws SQLException throws a sql exception when there is an issue in manipulating the result set
     */

    public Inventory executeResultSet(ResultSet rs) throws SQLException {
        Inventory inventory = new Inventory();
        ItemCategory itemCategory = new ItemCategory();
        ItemLocation itemLocation = new ItemLocation();
        inventory.setId(rs.getInt("i.id"));
        inventory.setItem_name(rs.getString("i.item_name"));
        inventory.setItem_quantity(rs.getInt("i.item_quantity"));
        itemCategory.setId(rs.getInt("i.item_category_id"));
        itemCategory.setCategory_name(rs.getString("c.category_name"));
        itemLocation.setId(rs.getInt("i.item_location_id"));
        itemLocation.setLocation_name(rs.getString("l.location_name"));
        inventory.setItem_category(itemCategory);
        inventory.setItem_location(itemLocation);
        return inventory;
    }

    /**
     * --
     * connects with db and inserts a new record in inventory table
     *
     * @param item an object of item that is to be created in table as a new record
     */
    public void insertNewInventoryItem(Inventory item) throws SQLException {
        Inventory.validate(item);
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(InventorySQL.ADD_INVENTORY);
            //     preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(1, item.getItem_name());
            preparedStatement.setInt(2, item.getItem_quantity());
            preparedStatement.setInt(3, item.getItem_category().getId());
            preparedStatement.setInt(4, item.getItem_location().getId());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    /**
     * connects with db and updates a particular record in inventory table
     *
     * @param item an item objects whose values are to be updated
     */

    public void updateExistingInventoryItem(Inventory item) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Inventory.validate(item);

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(InventorySQL.UPDATE_INVENTORY);
            preparedStatement.setString(1, item.getItem_name());
            preparedStatement.setInt(2, item.getItem_quantity());
            preparedStatement.setInt(3, item.getItem_category().getId());
            preparedStatement.setInt(4, item.getItem_location().getId());
            preparedStatement.setInt(5, item.getId());
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    /**
     * connects to db and fetches a particular item from inventory table for a specified item id
     *
     * @param id item id whose record is required
     * @return returns an item object for the item id specified as parameter
     */

    public Inventory readInventoryItemById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection con = null;
        ResultSet rs = null;
        Inventory inventory = new Inventory();

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(InventorySQL.FETCH_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                inventory = executeResultSet(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return inventory;
    }

    /**
     * connects to db and fetches records for a particular category and a particular location
     *
     * @param location location id for which the records are required
     * @param category category id for which the records are required
     * @return returns a list of items that matches a particular location and category that are specified in the parameters
     */
    public ArrayList<Inventory> readAllInventoryItems(Integer location, Integer category) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        Inventory inventory = new Inventory();

        try {
            DataSource dataSource = HikariCP.getDataSource();
            connection = dataSource.getConnection();

            if (category == null && location == null) {
                preparedStatement = connection.prepareStatement(InventorySQL.FETCH_ALL_INVENTORIES);
            } else if (category == null) {
                preparedStatement = connection.prepareStatement(InventorySQL.FETCH_BY_LOCATION);
                preparedStatement.setInt(1, location);
            } else if (location == null) {
                preparedStatement = connection.prepareStatement(InventorySQL.FETCH_BY_CATEGORY);
                preparedStatement.setInt(1, category);
            } else {
                preparedStatement = connection.prepareStatement(InventorySQL.FETCH_BY_CATEGORY_AND_LOCATION);
                preparedStatement.setInt(1, location);
                preparedStatement.setInt(2, category);
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                inventory = executeResultSet(rs);
                inventories.add(inventory);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return inventories;
    }

    /**
     * connects to db and deletes a particular record from inventory table
     *
     * @param id item id whose record is to be deleted
     */

    public void removeExistingInventoryItem(Integer id) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(InventorySQL.DELETE_INVENTORY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}


