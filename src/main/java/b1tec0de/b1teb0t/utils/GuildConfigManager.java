package b1tec0de.b1teb0t.utils;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;

/**
 * Handles the GuildConfig
 *
 * @author Kaufisch
 */

public class GuildConfigManager {

    public GuildConfig getGuildConfigById(String guildId) {
        for (GuildConfig guildConfig : Main.guildConfigs) {
            if (guildConfig.getGuildId().equals(guildId)) {
                return guildConfig;
            }
        }
        return null;
    }

}
