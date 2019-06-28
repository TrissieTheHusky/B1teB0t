package b1tec0de.b1teb0t.utils;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.dv8tion.jda.core.entities.TextChannel;

import java.sql.*;
import java.util.ArrayList;
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
                if (rs.getInt(3) == 1) {
                    guildConfig.setWarnSystem(true);
                } else {
                    guildConfig.setWarnSystem(false);
                }
                guildConfig.setWarnSystemMaxWarns(rs.getInt(4));
                guildConfig.setWarnSystemTimeoutLength(rs.getInt(5));
                guildConfig.setSupportRole(rs.getString(6));
                guildConfig.setSupportChannelWaitingRoom(rs.getString(7));
                guildConfig.setSupportNews(rs.getString(8));
                Main.guildConfigs.add(guildConfig);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (GuildConfig guildConfig : Main.guildConfigs) {
            guildConfig.setSupportChannel(getGuildSupportChannel(guildConfig.getGuildId()));
        }
    }

    private ArrayList<String> getGuildSupportChannel(String guildId) {
        try {
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT support_channel_id AS id FROM support_system WHERE guildId='" + guildId + "';");
            ArrayList<String> channels = new ArrayList<>();
            while (rs.next()) {
                channels.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();
            return channels;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSupportRole(String guildId, String roleId) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET support_system_role=? WHERE guildId=?;");
            stmt.setString(1, roleId);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
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
        } catch (SQLException ignored) {
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

    public void setSupportNews(String guildId, String channel) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET support_system_news=? WHERE guildId=?;");
            stmt.setString(1, channel);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getAmountOfWarns(String guildId, String userId) {
        try {
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS amount FROM warn_system_users WHERE user_id='" + userId + "' AND guildId='" + guildId + "';");
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getReason(String guildId, String id) {
        try {
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT reason FROM warn_system_reasons WHERE id='" + id + "' AND guildId='" + guildId + "';");
            String reason = "";
            while (rs.next()) {
                reason = rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
            return reason;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addToWarnedUsers(String guildId, String userId, int reasonId) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO warn_system_users (timestamp, guildId, user_id, reason) VALUES (DEFAULT, ?, ?, ?);");
            stmt.setString(1, guildId);
            stmt.setString(2, userId);
            stmt.setInt(3, reasonId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String guildId, String userId) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            // Execute Query and Close Connection
            stmt.executeUpdate("DELETE FROM warn_system_users WHERE guildId='" + guildId + "' AND user_id='" + userId + "';");
            stmt.close();
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    public void setMaxWarns(String guildId, String amount) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET warn_system_maxwarns=? WHERE guildId=?;");
            stmt.setString(1, amount);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTimeoutLength(String guildId, String amount) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET warn_system_maxwarns=? WHERE guildId=?;");
            stmt.setString(1, amount);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addWarnReason(TextChannel channel, String guildId, int reasonId, String reason) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO warn_system_reasons (guildId, id, reason) VALUES (?, ?, ?);");
            stmt.setString(1, guildId);
            stmt.setInt(2, reasonId);
            stmt.setString(3, reason);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void removeWarnReason(String guildId, int reasonId) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            Statement stmt = conn.createStatement();
            // Execute Query and Close Connection
            stmt.executeUpdate("DELETE FROM warn_system_reasons WHERE guildId='" + guildId + "' AND id='" + reasonId + "';");
            stmt.close();
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    public void setSupportWaitChannel(String guildId, String channel) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("UPDATE guild_config SET support_system_waiting_room=? WHERE guildId=?;");
            stmt.setString(1, channel);
            stmt.setString(2, guildId);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    public void addSupportSupChannel(String guildId, String channel) {
        try {
            // Connect to Database
            Connection conn = mysqlDataSource.getConnection();
            // Set Query
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO support_system (guildId, support_channel_id) VALUES (?, ?);");
            stmt.setString(1, guildId);
            stmt.setString(2, channel);
            // Execute Query and Close Connection
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ignored) {
        }
    }

}
