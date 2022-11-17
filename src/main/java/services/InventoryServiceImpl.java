package services;

import connpooling.HikariCPTest;
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

        if (System.getenv("AUTH_USERNAME").equals(username) && System.getenv("AUTH_PASSWORD").equals(password)) {
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
    public ArrayList<Inventory> executeResultSet(ResultSet rs) throws SQLException {
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();
        while (rs.next()) {
            Inventory item = new Inventory();
            ItemCategory itemCategory=new ItemCategory();
            ItemLocation itemLocation = new ItemLocation();
            item.setItemId(rs.getInt("id"));
            item.setItemName(rs.getString("item_name"));
            item.setItemQuantity(rs.getInt("item_quantity"));
            itemCategory.setCategoryId(rs.getInt("item_category_id"));
            itemCategory.setCategoryName(rs.getString("category_name"));
            itemLocation.setLocationId(rs.getInt("item_location_id"));
            itemLocation.setLocationName(rs.getString("location_name"));
            item.setItemCategory(itemCategory);
            item.setItemLocation(itemLocation);
            inventories.add(item);
        }
        return inventories;
    }
    /**--
     * connects with db and inserts a new record in inventory table
     * @param item an object of item that is to be created in table as a new record
     */

    public void insertNewItem(Inventory item) throws SQLException {
        String query = "insert into inventory(id, item_name, item_quantity, item_category_id, item_location_id) values (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        System.out.println(item.getItemCategory().getCategoryId());

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, item.getItemId());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getItemQuantity());
            preparedStatement.setInt(4, item.getItemCategory().getCategoryId());
            preparedStatement.setInt(5, item.getItemLocation().getLocationId());
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
     */

    public void removeItem(int id) throws SQLException {
        String query = "DELETE FROM inventory WHERE id = ?";

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
     */

    public ArrayList<Inventory> readItemById(int id) throws SQLException {
        String query = "SELECT i.id, i.item_name, i.item_quantity, i.item_category_id, i.item_location_id, c.category_name, l.location_name FROM inventory i, item_category c, item_location l where i.item_category_id = c.category_id && i.item_location_id = l.location_id && i.id = ?";

        PreparedStatement preparedStatement = null;
        Connection con = null;
        ResultSet rs = null;
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            inventories = executeResultSet(rs);
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return inventories;
        }
    }

    /**
     * connects to db and fetches all records from inventor table
     * @return returns a list of item objects whose records were present in inventory table
     */

    public ArrayList<Inventory> readAllItems() throws SQLException {
        String query = "SELECT i.id, i.item_name, i.item_quantity, i.item_category_id, i.item_location_id, c.category_name, l.location_name FROM inventory i, item_category c, item_location l where i.item_category_id = c.category_id && i.item_location_id = l.location_id";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            inventories = executeResultSet(rs);

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            preparedStatement.close();
            con.close();
            return inventories;
        }
    }

        /**
         * connects with db and updates a particular record in inventory table
         * @param item an item objects whose values are to be updated
         */

    public void updateItem(Inventory item, int id) throws SQLException {
        String query = "UPDATE inventory SET item_name = ?, item_quantity = ?, item_category_id = ?, item_location_id = ? WHERE id = ?";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            DataSource dataSource = HikariCPTest.getDataSource();
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, item.getItemName());
            preparedStatement.setInt(2, item.getItemQuantity());
            preparedStatement.setInt(3, item.getItemCategory().getCategoryId());
            preparedStatement.setInt(4, item.getItemLocation().getLocationId());
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
     * connects to db and fetches records for a particular category and a particular location
     * @param location location id for which the records are required
     * @param category category id for which the records are required
     * @return returns a list of items that matches a particular location and category that are specified in the parameters
     */
    public ArrayList<Inventory> readItemsByLocationandCategory(int location, int category) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Inventory> inventories = new ArrayList<Inventory>();

        try
        {
            DataSource dataSource = HikariCPTest.getDataSource();
            connection = dataSource.getConnection();

            if(category == 0) {
                pstmt = connection.prepareStatement("select i.id, i.item_name, i.item_quantity, i.item_category_id, i.item_location_id, c.category_name, l.location_name from Inventory i, item_category c, item_location l where i.item_category_id = c.category_id && i.item_location_id = l.location_id && item_location_id = ?");
                pstmt.setInt(1, location);
            }
            else if(location == 0) {
                pstmt = connection.prepareStatement("select i.id, i.item_name, i.item_quantity, i.item_category_id, i.item_location_id, c.category_name, l.location_name from Inventory i, item_category c, item_location l where i.item_category_id = c.category_id && i.item_location_id = l.location_id && item_category_id = ?");
                pstmt.setInt(1, category);
            }
            else {
                pstmt = connection.prepareStatement("select i.id, i.item_name, i.item_quantity, i.item_category_id, i.item_location_id, c.category_name, l.location_name from Inventory i, item_category c, item_location l where i.item_category_id = c.category_id && i.item_location_id = l.location_id && item_location_id = ? && item_category_id = ?");
                pstmt.setInt(1, location);
                pstmt.setInt(2, category);
            }
            rs = pstmt.executeQuery();
            inventories = executeResultSet(rs);
        }
        catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            pstmt.close();
            connection.close();
            return inventories;
        }
    }
}


