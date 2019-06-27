package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Triggered when the Bot joins to a new Guild.
 *
 * @author Kaufisch
 */

public class GuildJoin extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent e) {
        // Insert Guild into Database
        Database db = new Database();
        db.insertGuild(e.getGuild().getId());

        // Insert Guild into GuildConfig ArrayList
        GuildConfig guildConfig = new GuildConfig();
        guildConfig.setGuildId(e.getGuild().getId());
        guildConfig.setPrefix("!");
        Main.guildConfigs.add(guildConfig);

        // Create ActionLog Channel on Join

        // Guild guild = e.getGuild();
        // Channel ch = guild.getController().createTextChannel("ActionLog").complete();
    }
}
