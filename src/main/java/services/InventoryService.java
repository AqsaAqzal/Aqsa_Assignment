package services;

import domain.Item;
import java.sql.*;
import java.util.ArrayList;
public class InventoryService {

    private Item item = new Item();
    private ArrayList<Item> itemsList = new ArrayList<Item>();

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

    public Item readItemById(int id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/assignmentdb";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * FROM Inventory where id = ?";

        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, uname, pass);
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();


            if(rs.next()) {
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

        /*    while (rs.next()) {
                itemData += rs.getInt("id") + " " + rs.getString("item_name") + " " + rs.getInt("item_quantity") + " " + rs.getInt("category_id") + " " + rs.getInt("location_id");
            }*/
            while (rs.next()) {
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

        /*    while (rs.next()) {
                itemData += rs.getInt("id") + " " + rs.getString("item_name") + " " + rs.getInt("item_quantity") + " " + rs.getInt("category_id") + " " + rs.getInt("location_id");
            }*/
            while (rs.next()) {
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

        /*    while (rs.next()) {
                itemData += rs.getInt("id") + " " + rs.getString("item_name") + " " + rs.getInt("item_quantity") + " " + rs.getInt("category_id") + " " + rs.getInt("location_id");
            }*/
            while (rs.next()) {
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

        /*    while (rs.next()) {
                itemData += rs.getInt("id") + " " + rs.getString("item_name") + " " + rs.getInt("item_quantity") + " " + rs.getInt("category_id") + " " + rs.getInt("location_id");
            }*/
            while (rs.next()) {
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

