package services;

import domain.Item;

import java.sql.*;

public class InventoryService {

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
}
