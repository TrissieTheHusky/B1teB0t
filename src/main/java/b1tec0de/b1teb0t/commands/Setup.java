package b1tec0de.b1teb0t.commands;

import b1tec0de.b1teb0t.utils.Database;
import b1tec0de.b1teb0t.utils.GuildConfigManager;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;

/**
 * Setup Command
 * Adjust different things in the Modules of the bot.
 *
 * @author Kaufisch
 */

public class Setup {

    public void setupCommand(ArrayList<String> args, TextChannel channel) {
        if (args.get(0).equalsIgnoreCase("prefix") && args.size() == 2) {
            if (args.get(1).length() > 10) {
                channel.sendMessage("The maximum Prefix length is 10 Characters!").queue();
            } else {
                // Update Database
                Database db = new Database();
                db.updatePrefix(channel.getGuild().getId(), args.get(1));
                // Update GuildConfig
                GuildConfigManager gcm = new GuildConfigManager();
                gcm.setPrefixByGuild(channel.getGuild().getId(), args.get(1));
                // Send success Message
                channel.sendMessage("You successfully set the prefix to: ``" + args.get(1) + "``").queue();
            }
        }
    }

}