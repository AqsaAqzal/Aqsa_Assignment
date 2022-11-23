package connpooling;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.Constants;

import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.Condition;

public class HikariCP {

    private static DataSource datasource;

    public static DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(Constants.conUrl); // todo create constants
            config.setUsername(Constants.conUsername);
            config.setPassword(Constants.conPassword);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            config.setMaximumPoolSize(5);
            //todo configure connection pool

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }
}

