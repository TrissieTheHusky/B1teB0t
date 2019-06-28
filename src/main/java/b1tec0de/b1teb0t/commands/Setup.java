package b1tec0de.b1teb0t.commands;

import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Setup Command
 * Adjust different things in the Modules of the bot.
 *
 * @author Kaufisch
 */

//TODO: load pre-reasons cmd & log channel & cmd for listing all reasons

public class Setup {

    public static HashMap<Guild, User> tmpSupportWaitSetup = new HashMap<>();
    public static HashMap<Guild, User> tmpSupportSupSetup = new HashMap<>();
    public static HashMap<User, TextChannel> tmpSupportUser = new HashMap<>();

    public void setupCommand(ArrayList<String> args, TextChannel channel, Message msg) {
        // Prefix
        if (args.get(0).equalsIgnoreCase("prefix") && args.size() == 2) {
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
            if (args.get(1).length() > 10) {
                channel.sendMessage("The maximum Prefix length is 10 Characters!").queue();
            } else {
                // Update Database
                Database db = new Database();
                db.updatePrefix(channel.getGuild().getId(), args.get(1));
                // Update GuildConfig
                guildConfig.setPrefix(args.get(1));
                // Send success Message
                sendMessage(channel, msg, "You successfully set the prefix to: ``" + args.get(1) + "``");
            }
        }
        // SupportSystem
        if (args.get(0).equalsIgnoreCase("supportSystem") || args.get(0).equalsIgnoreCase("support-system") || args.get(0).equalsIgnoreCase("support_system")) {
            if (args.get(1).equalsIgnoreCase("add")) {
                if (args.get(2).equalsIgnoreCase("support")) {
                    channel.sendMessage(msg.getAuthor().getAsMention() + " Please join now into the Support-Room").queue();
                    tmpSupportSupSetup.put(msg.getGuild(), msg.getAuthor());
                    tmpSupportUser.put(msg.getAuthor(), msg.getTextChannel());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            tmpSupportUser.remove(msg.getAuthor());
                            tmpSupportSupSetup.remove(msg.getGuild());
                        }
                    }, 2 * 60 * 1000);
                }
            } else if (args.get(1).equalsIgnoreCase("set")) {
                if (args.get(2).equalsIgnoreCase("wait")) {
                    channel.sendMessage(msg.getAuthor().getAsMention() + " Please join now into the Support-Waiting-Room").queue();
                    tmpSupportWaitSetup.put(msg.getGuild(), msg.getAuthor());
                    tmpSupportUser.put(msg.getAuthor(), msg.getTextChannel());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            tmpSupportUser.remove(msg.getAuthor());
                            tmpSupportWaitSetup.remove(msg.getGuild());
                        }
                    }, 2 * 60 * 1000);
                } else if (args.get(2).equalsIgnoreCase("log")) {
                    // Database
                    Database db = new Database();
                    db.setSupportNews(msg.getGuild().getId(), msg.getMentionedChannels().get(0).getId());
                    // GuildConfig
                    GuildConfigManager gcm = new GuildConfigManager();
                    GuildConfig guildConfig = gcm.getGuildConfigById(msg.getGuild().getId());
                    guildConfig.setSupportNews(msg.getMentionedChannels().get(0).getId());
                    sendMessage(channel, msg, "You successfully set the Support-System Log Channel to " + msg.getMentionedChannels().get(0).getAsMention());
                }
            }
        }
        // WarnSystem
        if (args.get(0).equalsIgnoreCase("warn-system") || args.get(0).equalsIgnoreCase("warn_system") || args.get(0).equalsIgnoreCase("warnSystem")) {
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
            if (args.size() > 2) {
                Database db = new Database();
                if (args.get(1).equalsIgnoreCase("maxwarns") && guildConfig.isWarnSystem() && args.size() == 3) {
                    db.setMaxWarns(channel.getGuild().getId(), args.get(2));
                    sendMessage(channel, msg, "You successfully set the maximal warns until a user gets banned to " + args.get(2));
                } else if (args.get(1).equalsIgnoreCase("timeoutlength") && guildConfig.isWarnSystem() && args.size() == 3) {
                    db.setTimeoutLength(channel.getGuild().getId(), args.get(2));
                    sendMessage(channel, msg, "You successfully set the timeout length to " + args.get(2) + " minutes");
                } else if (args.get(1).equalsIgnoreCase("add") && args.size() == 4) {
                    try {
                        boolean success = db.addWarnReason(channel, channel.getGuild().getId(), Integer.parseInt(args.get(2)), args.get(3));
                        if (success) {
                            sendMessage(channel, msg, "You successfully added a new warn reason");
                        } else {
                            sendMessage(channel, msg, "This reason id already exists.");
                        }
                    } catch (Exception e) {
                        sendMessage(channel, msg, "Wrong usage! Use ``!setup warn-system help`` when you need help for this Command.");
                    }
                } else if (args.get(1).equalsIgnoreCase("remove") && args.size() == 3) {
                    try {
                        db.removeWarnReason(channel.getGuild().getId(), Integer.parseInt(args.get(2)));
                        sendMessage(channel, msg, "You successfully removed a warn reason");
                    } catch (NumberFormatException e) {
                        sendMessage(channel, msg, "Wrong usage! Use ``!setup warn-system help`` when you need help for this Command.");
                    }
                } else {
                    sendMessage(channel, msg, "Wrong usage! Use ``!setup warn-system help`` when you need help for this Command.");
                }
            } else {
                sendMessage(channel, msg, "Wrong usage! Use ``!setup warn-system help`` when you need help for this Command.");
            }
        }
    }

    private void sendMessage(TextChannel channel, Message msgOfMod, String message) {
        channel.sendMessage(message).queue(msg -> new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                msgOfMod.delete().queue();
                msg.delete().queue();
            }
        }, 5000));
    }

}