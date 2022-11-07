package services;

import domain.Item;
import java.sql.*;
import java.util.ArrayList;
public class InventoryService {

    private ArrayList<Item> itemsList = new ArrayList<Item>();


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

        Statement st = null;
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            st = con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(1, item.getItemId());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getItemQuantity());
            preparedStatement.setInt(4, item.getItemCategoryId());
            preparedStatement.setInt(5, item.getItemLocationId());
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            st.close();
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

        Statement st = null;
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            st = con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            st.close();
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

        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        Item item = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();


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
            ps.close();
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                 Item item = new Item();
                item.setItemId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemQuantity(rs.getInt("item_quantity"));
                item.setItemCategoryId(rs.getInt("category_id"));
                item.setItemLocationId(rs.getInt("location_id"));
                itemsList.add(item);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            ps.close();
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


        Statement st = null;
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            st = con.createStatement();

            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setInt(2, item.getItemId());
            preparedStatement.setString(1, item.getItemName());
           // preparedStatement.setInt(3, item.getItemQuantity());
           // preparedStatement.setInt(4, item.getItemCategoryId());
           // preparedStatement.setInt(5, item.getItemLocationId());
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            st.close();
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            ps.setInt(1, category);
            rs = ps.executeQuery();

            while (rs.next()) {
                 Item item = new Item();
                item.setItemId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemQuantity(rs.getInt("item_quantity"));
                item.setItemCategoryId(rs.getInt("category_id"));
                item.setItemLocationId(rs.getInt("location_id"));
                System.out.println(item.getItemName());
                itemsList.add(item);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            ps.close();
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            ps.setInt(1, location);
            rs = ps.executeQuery();

            while (rs.next()) {
                 Item item = new Item();
                item.setItemId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemQuantity(rs.getInt("item_quantity"));
                item.setItemCategoryId(rs.getInt("category_id"));
                item.setItemLocationId(rs.getInt("location_id"));
                System.out.println(item.getItemName());
                itemsList.add(item);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            ps.close();
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
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            ps.setInt(1, location);
            ps.setInt(2, category);
            rs = ps.executeQuery();

            while (rs.next()) {
                 Item item = new Item();
                item.setItemId(rs.getInt("id"));
                item.setItemName(rs.getString("item_name"));
                item.setItemQuantity(rs.getInt("item_quantity"));
                item.setItemCategoryId(rs.getInt("category_id"));
                item.setItemLocationId(rs.getInt("location_id"));
                System.out.println(item.getItemName());
                itemsList.add(item);
            }

        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            ps.close();
            con.close();
            return itemsList;
        }
    }
}

