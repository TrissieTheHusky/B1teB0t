package b1tec0de.b1teb0t.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Reads the Bot Auth Token from config.json and returns it.
 *
 * @author Kaufisch
 */

public class ConfigManager {

    private Gson gson;

    public ConfigManager() {
        gson = new Gson();
    }

    public static class Config {

        String TOKEN;

        String getToken() {
            return TOKEN;
        }

    }

    public String getAuthToken() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./config.json"));
            Config config = gson.fromJson(br, Config.class);
            return config.getToken();

        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] config.json not found!");
            return null;
        }
    }

}
