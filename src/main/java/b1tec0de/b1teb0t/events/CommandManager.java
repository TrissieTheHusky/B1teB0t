package b1tec0de.b1teb0t.events;

import b1tec0de.b1teb0t.commands.ClearSystem;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

    private String prefix = "!";

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().getContentRaw().startsWith(prefix)) {
            if (e.getMessage().getContentRaw().startsWith(prefix + "clear")) {
                ClearSystem cs = new ClearSystem();
                cs.clearCommand(new String[]{e.getMessage().getContentRaw().replace(prefix + "clear ", "")}, e.getChannel());
            }
        }
    }
}
