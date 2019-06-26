package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.commands.ClearSystem;
import b1tec0de.b1teb0t.commands.Setup;
import b1tec0de.b1teb0t.commands.WarnSystem;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        // Get GuildConfig
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(e.getGuild().getId());
        String guildPrefix = guildConfig.getPrefix();

        // GuildPrefix used?
        if (e.getMessage().getContentRaw().startsWith(guildPrefix) && !e.getAuthor().isBot()) {

            // Read Arguments
            String[] tmp = e.getMessage().getContentRaw().split(" ");
            ArrayList<String> args = new ArrayList<>(Arrays.asList(tmp).subList(1, tmp.length));

            // Setup Command
            if (e.getMessage().getContentRaw().startsWith(guildPrefix + "setup")) {
                Setup setupCmd = new Setup();
                setupCmd.setupCommand(args, e.getChannel(), e.getMessage());
            }

            // Clear Command
            if (e.getMessage().getContentRaw().startsWith(guildPrefix + "clear")) {
                ClearSystem cs = new ClearSystem();
                cs.clearCommand(args, e.getChannel(), guildPrefix);
            }

            //WarnSystem
            if (e.getMessage().getContentRaw().startsWith(guildPrefix + "warn")) {
                WarnSystem ws = new WarnSystem();
                ws.warnCommand(args, e.getGuild(), e.getMessage().getMentionedMembers(), e.getAuthor(), e.getChannel(), e.getMessage());
            }
        }
    }
}
