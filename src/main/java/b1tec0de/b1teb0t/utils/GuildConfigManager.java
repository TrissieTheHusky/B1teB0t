package b1tec0de.b1teb0t.utils;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;

/**
 * Handles the GuildConfig
 *
 * @author Kaufisch
 */

public class GuildConfigManager {

    public String getPrefixByGuild(String guildId) {
        for (GuildConfig guildConfig : Main.guildConfigs) {
            if (guildConfig.getGuildId().equals(guildId)) {
                return guildConfig.getPrefix();
            }
        }
        return null;
    }

    public void setPrefixByGuild(String guildId, String prefix) {
        for (GuildConfig guildConfig : Main.guildConfigs) {
            if (guildConfig.getGuildId().equals(guildId)) {
                guildConfig.setPrefix(prefix);
            }
        }
    }

}
