package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.utils.Database;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Removes Member from Database when they get Unbanned
 *
 * @author Kaufisch
 */

public class Unban extends ListenerAdapter {

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        Database db = new Database();
        db.deleteUser(event.getGuild().getId(), event.getUser().getId());
    }

}
