package b1tec0de.b1teb0t.utils;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.HashMap;

/**
 * Handles MySQL Database
 *
 * @author Kaufisch
 */

public class Database {

    private MysqlDataSource mysqlDataSource;

    /**
     * Initialize Database Connection
     */
    public Database() {
        ConfigManager cm = new ConfigManager();
        HashMap<String, String> creds = cm.getDatabaseCreds();
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(creds.get("user"));
        mysqlDataSource.setPassword(creds.get("password"));
        mysqlDataSource.setServerName(creds.get("host"));
        mysqlDataSource.setDatabaseName(creds.get("database"));
    }

}
