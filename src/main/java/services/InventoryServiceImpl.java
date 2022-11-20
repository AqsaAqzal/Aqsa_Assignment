package services;

import common.AuthCredentials;
import connpooling.HikariCP;
import domain.Inventory;
import domain.ItemCategory;
import domain.ItemLocation;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
public class InventoryServiceImpl implements InventoryService{

    /**
     * method to check if the user is authorized or not
     * @param authHeader auth details provided by client hitting the api
     * @return returns true if user is authorized else returns false
     */
    public boolean isAuthorized(String authHeader) {
        String headerDetails[] = authHeader.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(headerDetails[1]);
        String decodedString = new String(decodedBytes);
        String credentials[] = decodedString.split(":");
        String username = credentials[0], password = credentials[1];

        return AuthCredentials.authUsername.equals(username) && AuthCredentials.authPassword.equals(password);
        }

    /**
     * a method for executing the result set
     * @param rs the result set that is to be executed
     * @return a list of items in result set
     * @throws SQLException
     */

    // todo change this to return only single inventory object - move while loop to place where this function is being called
    public Inventory executeResultSet(ResultSet rs) throws SQLException {
        Inventory inventory = new Inventory();
        if (rs.next()) {
            ItemCategory itemCategory=new ItemCategory();
            ItemLocation itemLocation = new ItemLocation();
            inventory.setId(rs.getInt("id"));
            inventory.setItem_name(rs.getString("item_name"));
            inventory.setItem_quantity(rs.getInt("item_quantity"));
            itemCategory.setId(rs.getInt("item_category_id"));
            itemCategory.setCategory_name(rs.getString("category_name"));
            itemLocation.setId(rs.getInt("item_location_id"));
            itemLocation.setLocation_name(rs.getString("location_name"));
            inventory.setItem_category(itemCategory);
            inventory.setItem_location(itemLocation);
        }
        return inventory;
    }

    /**--
     * connects with db and inserts a new record in inventory table
     * @param item an object of item that is to be created in table as a new record
     */
    public void insertNewInventoryItem(Inventory item) throws SQLException {
        String query = "insert into inventory(id, item_name, item_quantity, item_category_id, item_location_id) values (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getItem_name());
            preparedStatement.setInt(3, item.getItem_quantity());
            preparedStatement.setInt(4, item.getItem_category().getId());
            preparedStatement.setInt(5, item.getItem_location().getId());
            preparedStatement.executeUpdate();
        }
            //todo remove exception from here and handle in resource
             finally {
            preparedStatement.close();
            con.close();
        }
    }

    /**
     * connects with db and updates a particular record in inventory table
     * @param item an item objects whose values are to be updated
     */

    public void updateExistingInventoryItem(Inventory item, int id) throws SQLException {
        String query = "UPDATE inventory SET item_name = ?, item_quantity = ?, item_category_id = ?, item_location_id = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, item.getItem_name());
            preparedStatement.setInt(2, item.getItem_quantity());
            preparedStatement.setInt(3, item.getItem_category().getId());
            preparedStatement.setInt(4, item.getItem_location().getId());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
        }
    }

    /**
     * connects to db and fetches a particular item from inventory table for a specified item id
     * @param id item id whose record is required
     * @return returns an item object for the item id specified as parameter
     */

    public Inventory readInventoryItemById(int id) throws SQLException {
        String query = "SELECT i.*, c.*, l.*  FROM inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && i.id = ?";
        //todo use alias.* like i.*, c.* to in query and fetch in resultset using alias like i.id, c.name etc
        PreparedStatement preparedStatement = null;
        Connection con = null;
        ResultSet rs = null;
        Inventory inventory = new Inventory();

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            inventory = executeResultSet(rs);
        } finally {
            preparedStatement.close();
            con.close();
        }
        return inventory;
    }

    /**
     * connects to db and fetches records for a particular category and a particular location
     * @param location location id for which the records are required
     * @param category category id for which the records are required
     * @return returns a list of items that matches a particular location and category that are specified in the parameters
     */
    public ArrayList<Inventory> readAllInventoryItems(int location, int category) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        Inventory inventory = new Inventory();


        try
        {
            DataSource dataSource = HikariCP.getDataSource();
            connection = dataSource.getConnection();

            if(category == 0 && location == 0) {
                preparedStatement = connection.prepareStatement("SELECT i.*, c.*, l.* FROM inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id");
            }

            else if(category == 0) {
                preparedStatement = connection.prepareStatement("select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_location_id = ?");
                preparedStatement.setInt(1, location);
            }
            else if(location == 0) {
                preparedStatement = connection.prepareStatement("select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_category_id = ?");
                preparedStatement.setInt(1, category);
            }
            else {
                preparedStatement = connection.prepareStatement("select i.*, c.*, l.* from Inventory i, item_category c, item_location l where i.item_category_id = c.id && i.item_location_id = l.id && item_location_id = ? && item_category_id = ?");
                preparedStatement.setInt(1, location);
                preparedStatement.setInt(2, category);
            }
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                inventory = executeResultSet(rs);
                inventories.add(inventory);
                System.out.println(inventories.size());
            }
        }
        catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            connection.close();
        }
        return inventories;
    }

    /**
     * connects to db and deletes a particular record from inventory table
     * @param id item id whose record is to be deleted
     */

    public void removeExistingInventoryItem(int id) throws SQLException {
        String query = "DELETE FROM inventory WHERE id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCP.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
        }
    }
}


