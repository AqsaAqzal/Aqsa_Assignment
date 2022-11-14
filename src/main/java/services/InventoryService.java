package services;

import common.Credentials;
import connpooling.HikariCPTest;
import domain.Item;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
public class InventoryService {

    private ArrayList<Item> itemsList = new ArrayList<Item>();

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

        if (Credentials.username.equals(username) && Credentials.password.equals(password)) {
            return true;
        }
            else {
                return false;
            }
        }

    /**
     * a method for executing the result set
     * @param rs the result set that is to be executed
     * @return a list of items in result set
     * @throws SQLException
     */
    public ArrayList<Item> executeResultSet(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Item item = new Item();
            item.setItemId(rs.getInt("id"));
            item.setItemName(rs.getString("item_name"));
            item.setItemQuantity(rs.getInt("item_quantity"));
            item.setItemCategoryId(rs.getInt("category_id"));
            item.setItemLocationId(rs.getInt("location_id"));
            itemsList.add(item);
        }
        return itemsList;
    }

    /**
     * connects with db and inserts a new record in inventory table
     * @param item an object of item that is to be created in table as a new record
     * @throws SQLException
     */

    public void insertNewItem(Item item) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "insert into Inventory(id, item_name, item_quantity, category_id, location_id) values (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, item.getItemId());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getItemQuantity());
            preparedStatement.setInt(4, item.getItemCategoryId());
            preparedStatement.setInt(5, item.getItemLocationId());
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
        }
    }

    /**
     * connects to db and deletes a particular record from inventory table
     * @param id item id whose record is to be deleted
     * @throws SQLException
     */

    public void removeItem(int id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "DELETE FROM Inventory WHERE id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
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

    /**
     * connects to db and fetches a particular item from inventory table for a specified item id
     * @param id item id whose record is required
     * @return returns an item object for the item id specified as parameter
     * @throws SQLException
     */

    public Item readItemById(int id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory where id = ?";

        PreparedStatement preparedStatement = null;
        Connection con = null;
        ResultSet rs = null;

        Item item = null;
        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItemId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemQuantity(rs.getInt("item_quantity"));
                item.setItemCategoryId(rs.getInt("category_id"));
                item.setItemLocationId(rs.getInt("location_id"));
                System.out.println(item);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return item;
        }
    }

    /**
     * connects to db and fetches all records from inventor table
     * @return returns a list of item objects whose records were present in inventory table
     * @throws SQLException
     */

    public ArrayList<Item> readAllItems() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            itemsList = executeResultSet(rs);

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return itemsList;
        }
    }

        /**
         * connects with db and updates a particular record in inventory table
         * @param item an item objects whose values are to be updated
         * @throws SQLException
         */

    public void updateItem(Item item) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "UPDATE Inventory SET item_name = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(2, item.getItemId());
            preparedStatement.setString(1, item.getItemName());
           // preparedStatement.setInt(3, item.getItemQuantity());
           // preparedStatement.setInt(4, item.getItemCategoryId());
           // preparedStatement.setInt(5, item.getItemLocationId());
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
        }
    }

    /**
     * connects to db and fetches record/s for a particular category that is specified
     * @param category category id whose records are required
     * @return returns a list of items for a specific category
     * @throws SQLException
     */
    public ArrayList<Item> readItemsByCategory(int category) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory where category_id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, category);
            rs = preparedStatement.executeQuery();
            itemsList = executeResultSet(rs);

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return itemsList;
        }
    }

    /**
     * fetches record/s for a particular location that is specified
     * @param location location id for which the records are required to be fetched
     * @return returns a list of items for the specified location
     * @throws SQLException
     */

    public ArrayList<Item> readItemsByLocation(int location) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory where location_id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, location);
            rs = preparedStatement.executeQuery();
            itemsList = executeResultSet(rs);

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return itemsList;
        }
    }

    /**
     * connects to db and fetches records for a particular category and a particular location
     * @param location location id for which the records are required
     * @param category category id for which the records are required
     * @return returns a list of items that matches a particular location and category that are specified in the parameters
     * @throws SQLException
     */
    public ArrayList<Item> readItemsByLocationandCategory(int location, int category) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory where location_id = ? && category_id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, location);
            preparedStatement.setInt(2, category);
            rs = preparedStatement.executeQuery();
            itemsList = executeResultSet(rs);

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return itemsList;
        }
    }

}

