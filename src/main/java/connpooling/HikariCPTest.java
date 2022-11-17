package connpooling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPTest {

    private static DataSource datasource;

    public static DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(System.getenv("CON_URL"));
            config.setUsername(System.getenv("CON_USERNAME"));
            config.setPassword(System.getenv("CON_PASSWORD"));
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

                /*
                config.setMaximumPoolSize(10);
                config.setAutoCommit(false);
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");=
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                */

            datasource = new HikariDataSource(config);
            System.out.println(datasource);

        }
        return datasource;
    }

}

