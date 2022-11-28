package util;
import connpooling.HikariCP;
import domain.User;
import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<User> usersList = new ArrayList<User>();

    public static ArrayList<User> getUsersList() {
        return usersList;
    }

    public ApplicationClass() {
        try {
            loadUsersFromDb();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public  ArrayList<User> loadUsersFromDb() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String query = "select * from user";

        try {
            DataSource dataSource = HikariCP.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                usersList.add(user);
            }

        }finally {
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
        return usersList;
    }
}
