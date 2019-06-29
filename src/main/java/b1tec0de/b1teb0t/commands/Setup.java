package b1tec0de.b1teb0t.commands;

import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
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

public class Setup {

    public static HashMap<Guild, User> tmpSupportWaitSetup = new HashMap<>();
    public static HashMap<Guild, User> tmpSupportSupSetup = new HashMap<>();
    public static HashMap<User, TextChannel> tmpSupportUser = new HashMap<>();

    public void setupHelp(TextChannel channel, Message msg) {
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
        EmbedBuilder privateEmbedBuilder = new EmbedBuilder()
                .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                .setTitle("Setup Help")
                .setDescription("Following parts of B1teB0t can/must be adjusted:")
                .addField(guildConfig.getPrefix() + "prefix <prefix>", "Set the Bot's prefix by replacing <prefix> with e.g. !", false)
                .addField(guildConfig.getPrefix() + "support-system help", "Get help for setting up the support-system", false)
                .addField(guildConfig.getPrefix() + "warn-system help", "Get help for setting up the support-system", false)
                .setColor(Color.MAGENTA)
                .setFooter("~ B1teB0t made with ❤️ by B1teC0de Team", null);
        msg.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(privateEmbedBuilder.build()).queue());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                msg.delete().queue();
            }
        }, 3000);
    }

    public void setupPrefix(ArrayList<String> args, TextChannel channel, Message msg) {
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
        if (args.get(0).length() > 10) {
            sendMessage(channel, msg, "The maximum Prefix length is 10 Characters!");
        } else {
            if (args.size() == 1) {
                // Update Database
                Database db = new Database();
                db.updatePrefix(channel.getGuild().getId(), args.get(0));
                // Update GuildConfig
                guildConfig.setPrefix(args.get(0));
                // Send success Message
                sendMessage(channel, msg, "You successfully set the prefix to: ``" + args.get(0) + "``");
            } else {
                sendMessage(channel, msg, "Wrong usage! Use ``!help`` when you need help for this Command.");
            }
        }
    }

    public void setupSupportSystem(ArrayList<String> args, TextChannel channel, Message msg) {
        if (args.get(1).equalsIgnoreCase("help")) {
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
            EmbedBuilder privateEmbedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setTitle("SupportSystem Setup Help")
                    .setDescription("By setting up SupportSystem you get notified when someone needs help.\nFollowing must be done to use the SupportSystem:")
                    .addField(guildConfig.getPrefix() + "support-system add", "Specify multiple Support-Channel.", false)
                    .addField(guildConfig.getPrefix() + "support-system set wait", "Specify the Support-Waiting-Room.", false)
                    .addField(guildConfig.getPrefix() + "support-system set log <#text-channel>", "Specify in which channel the message gets sent when someone joins the Channel.", false)
                    .setColor(Color.MAGENTA)
                    .setFooter("~ B1teB0t made with ❤️ by B1teC0de Team", null);
            msg.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(privateEmbedBuilder.build()).queue());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    msg.delete().queue();
                }
            }, 3000);
        } else if (args.get(0).equalsIgnoreCase("add")) {
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
        } else if (args.get(0).equalsIgnoreCase("set")) {
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
            } else if (args.get(1).equalsIgnoreCase("log") && args.size() == 3) {
                // Database
                Database db = new Database();
                db.setSupportNews(msg.getGuild().getId(), msg.getMentionedChannels().get(0).getId());
                // GuildConfig
                GuildConfigManager gcm = new GuildConfigManager();
                GuildConfig guildConfig = gcm.getGuildConfigById(msg.getGuild().getId());
                guildConfig.setSupportNews(msg.getMentionedChannels().get(0).getId());
                sendMessage(channel, msg, "You successfully set the Support-System Log Channel to " + msg.getMentionedChannels().get(0).getAsMention());
            }
        } else {
            sendMessage(channel, msg, "Wrong usage! Use ``!support-system help`` for help.");
        }
    }

    public void setupWarnSystem(ArrayList<String> args, TextChannel channel, Message msg) {
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(channel.getGuild().getId());
        if (args.get(0).equalsIgnoreCase("help")) {
            EmbedBuilder privateEmbedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setTitle("WarnSystem Setup Help")
                    .setDescription("By setting up WarnSystem you can add new warn reasons or remove them and a so on.\nFollowing can/must be setup to use the WarnSystem:")
                    .addField(guildConfig.getPrefix() + "warn-system maxwarns <amount>", "Indicates how many chances a users has before they get banned.", false)
                    .addField(guildConfig.getPrefix() + "warn-system length <amount>", "Specify how long a user gets timeouted (they can't write in any channel) if they have got warned.\n<amount> in minutes.", false)
                    .addField(guildConfig.getPrefix() + "warn-system add <id> <reason>", "Add new warn-reason.", false)
                    .addField(guildConfig.getPrefix() + "warn-system del <id>", "Delete warn-reason.", false)
                    .addField(guildConfig.getPrefix() + "warn-system load", "Load preset warn reasons.", false)
                    .addField(guildConfig.getPrefix() + "warn-system log <#text-channel>", "Set log channel for WarnSystem.", false)
                    .setColor(Color.MAGENTA)
                    .setFooter("~ B1teB0t made with ❤️ by B1teC0de Team", null);
            msg.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(privateEmbedBuilder.build()).queue());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    msg.delete().queue();
                }
            }, 3000);
        } else {
            args.size();
            Database db = new Database();
            if (args.get(0).equalsIgnoreCase("maxwarns") && guildConfig.isWarnSystem() && args.size() == 2) {
                try {
                    db.setMaxWarns(channel.getGuild().getId(), args.get(1));
                    guildConfig.setWarnSystemMaxWarns(Integer.parseInt(args.get(1)));
                    sendMessage(channel, msg, "You successfully set the maximal warns until a user gets banned to " + args.get(1));
                } catch (Exception e) {
                    sendMessage(channel, msg, "Wrong usage! Use ``!warn-system help`` when you need help for this Command.");
                }
            } else if (args.get(0).equalsIgnoreCase("length") && guildConfig.isWarnSystem() && args.size() == 2) {
                try {
                    db.setTimeoutLength(channel.getGuild().getId(), args.get(1));
                    guildConfig.setWarnSystemTimeoutLength(Integer.parseInt(args.get(1)));
                    sendMessage(channel, msg, "You successfully set the timeout length to " + args.get(1) + " minute(s)");
                } catch (Exception e) {
                    sendMessage(channel, msg, "Wrong usage! Use ``!warn-system help`` when you need help for this Command.");
                }
            } else if (args.get(0).equalsIgnoreCase("add")) {
                try {
                    StringBuilder args2 = new StringBuilder();
                    for (int i = 2; i < args.size(); i++) {
                        args2.append(args.get(i)).append(" ");
                    }
                    args2 = new StringBuilder(args2.substring(0, args2.length() - 1));
                    boolean success = db.addWarnReason(channel.getGuild().getId(), Integer.parseInt(args.get(1)), args2.toString());
                    if (success) {
                        sendMessage(channel, msg, "You successfully added a new warn reason");
                    } else {
                        sendMessage(channel, msg, "This reason id already exists.");
                    }
                } catch (Exception e) {
                    sendMessage(channel, msg, "Wrong usage! Use ``!warn-system help`` when you need help for this Command.");
                }
            } else if (args.get(0).equalsIgnoreCase("del") && args.size() == 2) {
                try {
                    db.removeWarnReason(channel.getGuild().getId(), Integer.parseInt(args.get(1)));
                    sendMessage(channel, msg, "You successfully removed a warn reason");
                } catch (NumberFormatException e) {
                    sendMessage(channel, msg, "Wrong usage! Use ``!warn-system help`` when you need help for this Command.");
                }
            } else if (args.get(0).equalsIgnoreCase("log") && msg.getMentionedChannels().size() == 1) {
                db.setWarnLog(msg.getGuild().getId(), msg.getMentionedChannels().get(0).getId());
                guildConfig.setWarnSystemLog(msg.getMentionedChannels().get(0).getId());
                sendMessage(channel, msg, "You have successfully set the log channel to " + msg.getMentionedChannels().get(0).getAsMention());
            } else if (args.get(0).equalsIgnoreCase("load")) {
                db.clearReasons(msg.getGuild().getId());
                HashMap<Integer, String> reasons = initReasons();
                reasons.forEach((k, v) -> db.addWarnReason(msg.getGuild().getId(), k, v));
                sendMessage(channel, msg, "You have successfully loaded the preset of reasons.");
            } else {
                sendMessage(channel, msg, "Wrong usage! Use ``!warn-system help`` when you need help for this Command.");
            }
        }
    }

    public void sendMessage(TextChannel channel, Message msgOfMod, String message) {
        channel.sendMessage(message).queue(msg -> new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                msgOfMod.delete().queue();
                msg.delete().queue();
            }
        }, 5000));
    }

    private HashMap<Integer, String> initReasons() {
        HashMap<Integer, String> reasons = new HashMap<>();
        reasons.put(1, "Harassment");
        reasons.put(2, "Spam");
        reasons.put(3, "Advertisement");
        reasons.put(4, "Racism");
        reasons.put(5, "Ban bypass");
        reasons.put(6, "Scam");
        reasons.put(7, "NSFW Content");
        reasons.put(8, "Channel Hopping");
        return reasons;
    }

}