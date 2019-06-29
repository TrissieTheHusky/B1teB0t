package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.commands.ClearSystem;
import b1tec0de.b1teb0t.commands.Setup;
import b1tec0de.b1teb0t.commands.WarnSystem;
import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles the different Commands
 *
 * @author Kaufisch
 */

public class CommandManager extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getAuthor().isBot() && e.getMember().getPermissions().contains(Permission.MANAGE_SERVER)) {
            // Get GuildConfig
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(e.getGuild().getId());
            String guildPrefix = guildConfig.getPrefix();

            // GuildPrefix used?
            if (e.getMessage().getContentRaw().startsWith(guildPrefix) && !e.getAuthor().isBot()) {

                // Read Arguments
                String[] tmp = e.getMessage().getContentRaw().split(" ");
                ArrayList<String> args = new ArrayList<>(Arrays.asList(tmp).subList(1, tmp.length));

                // Setup
                if (e.getMessage().getContentRaw().equalsIgnoreCase(guildPrefix + "help")) {
                    Setup setupCmd = new Setup();
                    setupCmd.setupHelp(e.getChannel(), e.getMessage());
                }
                if (e.getMessage().getContentRaw().startsWith(guildPrefix + "prefix")) {
                    if (args.size() > 0) {
                        Setup setupCmd = new Setup();
                        setupCmd.setupPrefix(args, e.getChannel(), e.getMessage());
                    } else {
                        Setup setupCmd = new Setup();
                        setupCmd.sendMessage(e.getChannel(), e.getMessage(), "Wrong usage! Use ``!help`` for help.");
                    }
                }
                if (e.getMessage().getContentRaw().startsWith(guildPrefix + "warn-system")) {
                    if (args.size() > 0) {
                        Setup setupCmd = new Setup();
                        setupCmd.setupWarnSystem(args, e.getChannel(), e.getMessage());
                    } else {
                        Setup setupCmd = new Setup();
                        setupCmd.sendMessage(e.getChannel(), e.getMessage(), "Wrong usage! Use ``!help`` for help.");
                    }
                }
                if (e.getMessage().getContentRaw().startsWith(guildPrefix + "support-system")) {
                    if (args.size() > 0) {
                        Setup setupCmd = new Setup();
                        setupCmd.setupSupportSystem(args, e.getChannel(), e.getMessage());
                    } else {
                        Setup setupCmd = new Setup();
                        setupCmd.sendMessage(e.getChannel(), e.getMessage(), "Wrong usage! Use ``!help`` for help.");
                    }
                }

                // Clear Command
                if (e.getMessage().getContentRaw().startsWith(guildPrefix + "clear")) {
                    ClearSystem cs = new ClearSystem();
                    cs.clearCommand(args, e.getChannel(), guildPrefix);
                }

                //WarnSystem
                if (e.getMessage().getContentRaw().startsWith(guildPrefix + "warn ")) {
                    WarnSystem ws = new WarnSystem();
                    ws.warnCommand(args, e.getGuild(), e.getMessage().getMentionedMembers(), e.getAuthor(), e.getChannel(), e.getMessage());
                } else if (e.getMessage().getContentRaw().startsWith(guildPrefix + "warnlist")) {
                    Database db = new Database();
                    String reason = db.getReasons(e.getGuild().getId());
                    EmbedBuilder privateEmbedBuilder = new EmbedBuilder()
                            .setAuthor(e.getGuild().getName(), null, e.getGuild().getIconUrl())
                            .setTitle("WarnSystem Reasons:")
                            .setDescription(reason)
                            .setColor(Color.orange);
                    e.getMessage().getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(privateEmbedBuilder.build()).queue());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            e.getMessage().delete().queue();
                        }
                    }, 3000);
                }
            }
        }
    }
}
