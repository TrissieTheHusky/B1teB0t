package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.main.Main;
import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

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

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //Add Supporter Role
                e.getGuild().getController().createRole()
                        .setName("Supporter")
                        .setColor(Color.CYAN)
                        .setMentionable(true)
                        .queue(role -> {
                            guildConfig.setSupportRole(role.getId());
                            db.setSupportRole(e.getGuild().getId(), role.getId());
                            Main.guildConfigs.add(guildConfig);
                        });
            }
        }, 5000);

        if (!isAction(e.getGuild())) {
            e.getGuild().getController().createTextChannel("actionlog").queue();
        }

    }

    private boolean isAction(Guild guild) {
        for (TextChannel tc : guild.getTextChannels()) {
            if (tc.getName().equals("actionlog")) {
                return true;
            }
        }
        return false;
    }

}
