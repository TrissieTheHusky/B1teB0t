package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.commands.Setup;
import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import b1tec0de.b1teb0t.utils.objects.GuildConfig;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class SupportSystem extends ListenerAdapter {

    private static HashMap<Member, String> inSupport = new HashMap<>();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        if (Setup.tmpSupportSupSetup.get(event.getGuild()) != null) {
            // Database
            Database db = new Database();
            db.addSupportSupChannel(event.getGuild().getId(), event.getChannelJoined().getId());
            // GuildConfig
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            ArrayList<String> tmpArray = guildConfig.getSupportChannel();
            tmpArray.add(event.getChannelJoined().getId());
            guildConfig.setSupportChannel(tmpArray);
            //TextChannel
            Setup.tmpSupportUser.get(event.getMember().getUser()).sendMessage("Support Channel was added successfully!").queue();
            Setup.tmpSupportUser.remove(event.getMember().getUser());

        } else if (Setup.tmpSupportWaitSetup.get(event.getGuild()) != null) {
            // Database
            Database db = new Database();
            db.setSupportWaitChannel(event.getGuild().getId(), event.getChannelJoined().getId());
            // GuildConfig
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            guildConfig.setSupportChannelWaitingRoom(event.getChannelJoined().getId());
            //TextChannel
            Setup.tmpSupportUser.get(event.getMember().getUser()).sendMessage("Support-Waiting-Room was setup successfully!").queue();
            Setup.tmpSupportUser.remove(event.getMember().getUser());
        } else {
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            if (event.getChannelJoined().getId().equalsIgnoreCase(guildConfig.getSupportChannelWaitingRoom()))
                support(event.getMember(), event.getGuild().getTextChannelById(guildConfig.getSupportNews()), event.getGuild().getRoleById(guildConfig.getSupportRole()));
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        if (Setup.tmpSupportSupSetup.get(event.getGuild()) != null) {
            // Database
            Database db = new Database();
            db.addSupportSupChannel(event.getGuild().getId(), event.getChannelJoined().getId());
            // GuildConfig
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            ArrayList<String> tmpArray = guildConfig.getSupportChannel();
            tmpArray.add(event.getChannelJoined().getId());
            guildConfig.setSupportChannel(tmpArray);
            //TextChannel
            Setup.tmpSupportUser.get(event.getMember().getUser()).sendMessage("Support Channel was added successfully!").queue();
            Setup.tmpSupportUser.remove(event.getMember().getUser());
        } else if (Setup.tmpSupportWaitSetup.get(event.getGuild()) != null) {
            // Database
            Database db = new Database();
            db.setSupportWaitChannel(event.getGuild().getId(), event.getChannelJoined().getId());
            // GuildConfig
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            guildConfig.setSupportChannelWaitingRoom(event.getChannelJoined().getId());
            //TextChannel
            Setup.tmpSupportUser.get(event.getMember().getUser()).sendMessage("Support-Waiting-Room was setup successfully!").queue();
            Setup.tmpSupportUser.remove(event.getMember().getUser());
        } else {
            GuildConfigManager gcm = new GuildConfigManager();
            GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
            if (guildConfig.getSupportChannel() != null && guildConfig.getSupportChannelWaitingRoom() != null) {
                /*
                 * When user moves from any channel to Support Waiting Room
                 * then execute the function support()
                 */
                if (event.getChannelJoined().getId().equalsIgnoreCase(guildConfig.getSupportChannelWaitingRoom())) {
                    support(event.getMember(), event.getGuild().getTextChannelById(guildConfig.getSupportNews()), event.getGuild().getRoleById(guildConfig.getSupportRole()));
                }
                /*
                 * When user moves from Support Waiting Room to another channel which isn't one of the specified Support Channel
                 * then add x reaction to message
                 * and remove the Member from inSupport Hashmap
                 */
                else if (!guildConfig.getSupportChannel().contains(event.getChannelJoined().getId()) && guildConfig.getSupportChannelWaitingRoom().equalsIgnoreCase(event.getChannelLeft().getId())) {
                    //Add x to msg
                    String msgId = inSupport.get(event.getMember());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            event.getGuild().getTextChannelById(guildConfig.getSupportNews()).addReactionById(msgId, "\u274C").queue();
                            inSupport.remove(event.getMember());
                        }
                    }, 3000);
                }
                /*
                 * When user moves from Support Waiting Room to one of the specified Support Channel
                 * then add check mark reaction
                 * and remove the Member from inSupport Hashmap
                 */
                else if (guildConfig.getSupportChannel().contains(event.getChannelJoined().getId()) && guildConfig.getSupportChannelWaitingRoom().equalsIgnoreCase(event.getChannelLeft().getId())) {
                    //Add CheckMark to msg
                    String msgId = inSupport.get(event.getMember());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            event.getGuild().getTextChannelById(guildConfig.getSupportNews()).addReactionById(msgId, "\u2705").queue();
                            inSupport.remove(event.getMember());
                        }
                    }, 3000);
                    /*
                     * When user left one of the specified Support Channel remove the Member from inSupport Hashmap
                     */
                } else if (guildConfig.getSupportChannel().contains(event.getChannelLeft().getId())) {
                    inSupport.remove(event.getMember());
                }
            }
        }
    }


    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        GuildConfigManager gcm = new GuildConfigManager();
        GuildConfig guildConfig = gcm.getGuildConfigById(event.getGuild().getId());
        if (guildConfig.getSupportChannel() != null && guildConfig.getSupportChannelWaitingRoom() != null) {
            if (guildConfig.getSupportChannel().contains(event.getChannelLeft().getId())) {
                inSupport.remove(event.getMember());
            } else if (guildConfig.getSupportChannelWaitingRoom().equalsIgnoreCase(event.getChannelLeft().getId())) {
                String msgId = inSupport.get(event.getMember());
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        event.getGuild().getTextChannelById(guildConfig.getSupportNews()).addReactionById(msgId, "\u274C").queue();
                        inSupport.remove(event.getMember());
                    }
                }, 3000);
            }
        }
    }

    private void support(Member member, TextChannel channel, Role role) {
        EmbedBuilder privateEmbedBuilder = new EmbedBuilder()
                .setAuthor(channel.getGuild().getName(), null, channel.getGuild().getIconUrl())
                .setDescription("Please have some patience.\nA Staff member will help you soon.")
                .setColor(Color.CYAN);
        member.getUser().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(privateEmbedBuilder.build()).queue());
        RestAction<Message> restAction = channel.sendMessage(role.getAsMention());
        Consumer<Message> callback = message -> {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setAuthor(member.getEffectiveName() + "#" + member.getUser().getDiscriminator(), null, member.getUser().getAvatarUrl())
                    .setColor(Color.CYAN)
                    .setDescription("<@" + member.getUser().getId() + ">" + " **joined the Support Waiting Room**")
                    .setTimestamp(Instant.now());
            message.editMessage(embedBuilder.build()).queue();
            inSupport.put(member, message.getId());
        };
        restAction.queue(callback);
    }

}
