package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.utils.Database;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Triggered when the Bot starts.
 * Reads all Guilds to GuildConfig.
 *
 * @author Kaufisch
 */

public class BotStarted extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Database db = new Database();
        db.readToGuildConfig();
    }
}
