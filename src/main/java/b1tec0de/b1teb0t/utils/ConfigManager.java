package b1tec0de.b1teb0t.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Reads the Bot Auth Token from config.json and returns it.
 *
 * @author Kaufisch
 */

public class ConfigManager {

    private Gson gson;
    private BufferedReader br;


    public ConfigManager() {
        gson = new Gson();
    }

    public String getAuthToken() {
        try {
            br = new BufferedReader(new FileReader("./config.json"));
            JsonObject config = gson.fromJson(br, JsonObject.class);
            String TOKEN = config.getAsJsonObject("Discord").get("TOKEN").getAsString();
            if(TOKEN.equals("YourBotToken")) {
                return "default values";
            }
            else {
                return TOKEN;
            }
        } catch (FileNotFoundException e) {
            return "not found";
        }
    }

    public HashMap getDatabaseCreds() {
        JsonObject config = gson.fromJson(br, JsonObject.class);
        HashMap<String, String> creds = new HashMap<>();
        creds.put("user", config.getAsJsonObject("Database").get("user").getAsString());
        creds.put("password", config.getAsJsonObject("Database").get("password").getAsString());
        creds.put("database", config.getAsJsonObject("Database").get("database").getAsString());
        return creds;
    }

}
