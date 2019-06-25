package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.commands.Setup;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        // Get GuildPrefix
        GuildConfigManager gcm = new GuildConfigManager();
        String guildPrefix = gcm.getPrefixByGuild(e.getGuild().getId());
        // GuildPrefix ?
        if (e.getMessage().getContentRaw().startsWith(guildPrefix)) {
            // Read Arguments
            String[] tmp = e.getMessage().getContentRaw().split(" ");
            ArrayList<String> args = new ArrayList<>(Arrays.asList(tmp).subList(1, tmp.length));
            // Setup Command ?
            if (e.getMessage().getContentRaw().startsWith(guildPrefix + "setup")) {
                Setup setupCmd = new Setup();
                setupCmd.setupCommand(args, e.getChannel());
            }
        }
    }
}
