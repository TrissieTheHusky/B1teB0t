package b1tec0de.b1teb0t.commands;

import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WarnSystem {

    public void warnCommand(ArrayList<String> args, Guild guild, List<Member> members, User mod, TextChannel textChannel, Message cmd) {
        // Initialize GuildConfig
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(guild.getId());

        // Right size?
        if (members.size() == 1 && args.size() == 2) {
            Member member = members.get(0);
            // Setup WarnSystem?
            if (guildConfig.isWarnSystem()) {
                // Initialize Database, tmpWarnCounter, maxWarns
                Database db = new Database();
                int amountOfWarns = db.getAmountOfWarns(guild.getId(), member.getUser().getId());
                int maxWarns = guildConfig.getWarnSystemMaxWarns();
                String reason = db.getReason(guild.getId(), args.get(1));
                // Warn or Ban?
                if (amountOfWarns < maxWarns) {
                    //Timeout
                    int timeout = guildConfig.getWarnSystemTimeoutLength(); // In minutes
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.ORANGE)
                            .setTitle(guild.getName())
                            .setDescription("You was timeouted for " + guildConfig.getWarnSystemTimeoutLength() + " minutes.")
                            .addField("Reason", reason, true)
                            .setFooter("~ B1teB0t made with ❤️ by B1teC0de Team", null);

                    members.get(0).getUser().openPrivateChannel().queue((channel) -> channel.sendMessage(embedBuilder.build()).queue());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            cmd.delete().queue();
                        }
                    }, 3000);
                    db.addToWarnedUsers(guild.getId(), member.getUser().getId(), Integer.parseInt(args.get(1)));
                    setChannelPerms(guild, member, timeout);
                } else {
                    if (reason == null) {
                        // Wrong usage
                        textChannel.sendMessage("Usage: `" + guildConfig.getPrefix() + "warn <@User> <Reason Id>`").queue(message -> new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                cmd.delete().queue();
                                message.delete().queue();
                            }
                        }, 3000));
                    } else {
                        //Perm ban
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle(guild.getName())
                                .setDescription("You was baned from " + guild.getName())
                                .addField("Reason", reason, true)
                                .setFooter("~ B1teB0t made with ❤️ by B1teC0de Team", null);

                        members.get(0).getUser().openPrivateChannel().queue((channel) -> channel.sendMessage(embedBuilder.build()).queue(msg -> {
                            guild.getController().ban(member, 1, "Reason: " + reason + "; Author: " + mod.getName()).queue();
                        }));
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                cmd.delete().queue();
                            }
                        }, 3000);
                        db.addToWarnedUsers(guild.getId(), member.getUser().getId(), Integer.parseInt(args.get(1)));
                    }
                }
            } else {
                textChannel.sendMessage("To use `" + guildConfig.getPrefix() + "warn` you first have to setup it!").queue(message -> new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        cmd.delete().queue();
                        message.delete().queue();
                    }
                }, 3000));
            }
        } else {
            textChannel.sendMessage("Usage: `" + guildConfig.getPrefix() + "warn <@User> <Reason Id>`").queue(message -> new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    cmd.delete().queue();
                    message.delete().queue();
                }
            }, 3000));
        }
    }

    private void setChannelPerms(Guild guild, Member member, int timeout) {
        // add member to channel perms
        for (TextChannel tc : guild.getTextChannels()) {
            try {
                tc.createPermissionOverride(member).setDeny(Permission.MESSAGE_WRITE).queue();
            } catch (InsufficientPermissionException ignored) {
            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // remove member from channel perms
                for (TextChannel tc : guild.getTextChannels()) {
                    try {
                        for (PermissionOverride permissionOverride : tc.getMemberPermissionOverrides()) {
                            if (permissionOverride.isMemberOverride() && permissionOverride.getMember().equals(member)) {
                                permissionOverride.delete().queue();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }, timeout * 60 * 1000);
    }

}
