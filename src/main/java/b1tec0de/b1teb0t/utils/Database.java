package b1tec0de.b1teb0t.utils;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
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
        try {
            ConfigManager cm = new ConfigManager();
            HashMap<String, String> creds = cm.getDatabaseCreds();
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUser(creds.get("user"));
            mysqlDataSource.setPassword(creds.get("password"));
            mysqlDataSource.setServerName(creds.get("host"));
            mysqlDataSource.setDatabaseName(creds.get("database"));
            mysqlDataSource.setServerTimezone("UTC");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void readToGuildConfig() {
        try {
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guild_config;");
            while (rs.next()) {
                GuildConfig guildConfig = new GuildConfig();
                guildConfig.setGuildId(rs.getString(1));
                guildConfig.setPrefix(rs.getString(2));
                Main.guildConfigs.add(guildConfig);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public void insertGuild(String guildId) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO guild_config (guildId) VALUES (?);");
            stmt.setString(1, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public void updatePrefix(String guildId, String prefix) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET prefix=? WHERE guildId=?;");
            stmt.setString(1, prefix);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
