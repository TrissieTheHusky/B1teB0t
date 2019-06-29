package b1tec0de.b1teb0t.events;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.events.role.update.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

/**
 * Setup Command
 * Adjust different things in the Modules of the bot.
 *
 * @author JavaBasti, Kaufisch
 */


public class ActionLogEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent e) {
        try {
            Member mb = e.getMember();
            VoiceChannel vc = e.getChannelJoined();
            Guild guild = mb.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.GREEN)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **joined voicechannel `" + vc.getName() + "` **")
                    .setTimestamp(Instant.now());

            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent e) {
        try {
            Member mb = e.getMember();
            VoiceChannel vc = e.getChannelLeft();
            Guild guild = mb.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.RED)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **left voicechannel `" + vc.getName() + "` **")
                    .setTimestamp(Instant.now());

            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent e) {
        try {
            Member mb = e.getMember();
            VoiceChannel vc = e.getChannelLeft();
            VoiceChannel vc2 = e.getChannelJoined();
            Guild guild = mb.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.GREEN)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **switched from voicechannel `" + vc.getName() + "` to `" + vc2.getName() + "`**")
                    .setTimestamp(Instant.now());

            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        try {
            Member mb = e.getMember();
            Guild guild = mb.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.GREEN)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **joined the Server.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        try {
            Member mb = e.getMember();
            Guild guild = mb.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.RED)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **left the Server.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildBan(GuildBanEvent e) {
        try {
            User user = e.getUser();
            Guild guild = e.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(user.getName() + "#" + user.getDiscriminator(), null, user.getAvatarUrl())
                    .setColor(Color.RED)
                    .setDescription("<@" + user.getId() + ">" + " **got banned.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent e) {
        try {
            User user = e.getUser();
            Guild guild = e.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(user.getName() + "#" + user.getDiscriminator(), null, user.getAvatarUrl())
                    .setColor(Color.GREEN)
                    .setDescription("<@" + user.getId() + ">" + " **got unbanned.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) {
        try {
            Member mb = e.getMember();
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.CYAN)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **was given the `" + e.getRoles().get(0).getName() + "` role.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) {
        try {
            Member mb = e.getMember();
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                    .setColor(Color.CYAN)
                    .setDescription("<@" + mb.getUser().getId() + ">" + " **was removed from the `" + e.getRoles().get(0).getName() + "` role.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    // ROLE EVENTS

    @Override
    public void onRoleCreate(RoleCreateEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.GREEN)
                    .setDescription("**The `" + e.getRole().getName() + "` role was created.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.RED)
                    .setDescription("**The `" + e.getRole().getName() + "` role got deleted.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The color of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdateName(RoleUpdateNameEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The name of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdateHoisted(RoleUpdateHoistedEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The hoisted state of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The permissions of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdateMentionable(RoleUpdateMentionableEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The mentionable setting of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onRoleUpdatePosition(RoleUpdatePositionEvent e) {
        try {
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**The position of the `" + e.getRole().getName() + "` role got changed.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    // MESSAGE CHANGES
    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
        try {
            User author = e.getAuthor();
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(author.getName() + "#" + author.getDiscriminator(), null, author.getAvatarUrl())
                    .setColor(Color.ORANGE)
                    .setDescription("<@" + author.getId() + ">" + " **edited his message to `" + e.getMessage().getContentDisplay() + "`.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent e) {
        try {
            Guild guild = e.getGuild();
            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setDescription("**A message in channel <#" + e.getChannel().getId() + "> got deleted.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    // CHANNEL CREATION
    @Override
    public void onTextChannelCreate(TextChannelCreateEvent e) {
        try {
            Channel ch = e.getChannel();
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**TextChannel <#" + ch.getId() + "> was created.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent e) {
        try {
            Channel ch = e.getChannel();
            Guild guild = e.getGuild();

            TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor("B1teB0t", null, "https://avatars2.githubusercontent.com/u/52173054?s=200&v=4")
                    .setColor(Color.BLUE)
                    .setDescription("**VoiceChannel `" + ch.getName() + "` was created.**")
                    .setTimestamp(Instant.now());
            tc.sendMessage(embedBuilder.build()).queue();
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public void onGuildMemberNickChange(GuildMemberNickChangeEvent e) {
        try {
            Member mb = e.getMember();
            Guild guild = e.getGuild();
            String oldNick = e.getPrevNick();
            String newNick = e.getNewNick();


            if (e.getNewNick() == null) {

                TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                        .setColor(Color.ORANGE)
                        .setDescription("<@" + mb.getUser().getId() + ">" + " **changed his nickname **")
                        .addField("**Previous nickname**", "`" + oldNick + "`", true)
                        .addField("**New nickname**", "`" + mb.getUser().getName() + "`", true)
                        .setTimestamp(Instant.now());
                tc.sendMessage(embedBuilder.build()).queue();
            } else if (e.getPrevNick() == null) {
                TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                        .setColor(Color.ORANGE)
                        .setDescription("<@" + mb.getUser().getId() + ">" + " **changed his nickname **")
                        .addField("**Previous nickname**", "`" + mb.getUser().getName() + "`", true)
                        .addField("**New nickname**", "`" + newNick + "`", true)
                        .setTimestamp(Instant.now());
                tc.sendMessage(embedBuilder.build()).queue();
            } else {
                TextChannel tc = guild.getTextChannelsByName("actionlog", true).get(0);
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setAuthor(mb.getEffectiveName() + "#" + mb.getUser().getDiscriminator(), null, mb.getUser().getAvatarUrl())
                        .setColor(Color.ORANGE)
                        .setDescription("<@" + mb.getUser().getId() + ">" + " **changed his nickname **")
                        .addField("**Previous nickname**", "`" + oldNick + "`", true)
                        .addField("**New nickname**", "`" + newNick + "`", true)
                        .setTimestamp(Instant.now());
                tc.sendMessage(embedBuilder.build()).queue();
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }
}


