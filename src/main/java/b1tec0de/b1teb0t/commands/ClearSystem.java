
package b1tec0de.b1teb0t.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ClearSystem
 *
 * @author JavaBasti
 */

public class ClearSystem {

    public void clearCommand(ArrayList<String> args, TextChannel channel, String prefix) {
        if (args.size() < 1) {
            channel.sendMessage("Usage: `" + prefix + "clear <amount>`").queue(message -> new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    message.delete().queue();
                }
            }, 3000));
        } else {
            int amount = Integer.parseInt(args.get(0));
            if (args.size() == 1 && amount > 1 && amount <= 100) {
                try {
                    delMessages(amount + 1, channel);
                    channel.sendMessage("You deleted **" + args.get(0) + "** messages.").queue(message -> new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            message.delete().queue();
                        }
                    }, 3000));

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            } else {
                channel.sendMessage("Please enter a number between 2 and 100.").queue(message -> new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        message.delete().queue();
                    }
                }, 3000));
            }
        }
    }

    private void delMessages(int amount, TextChannel channel) {
        MessageHistory msgHistory = new MessageHistory(channel);
        List<Message> msgs;

        msgs = msgHistory.retrievePast(amount).complete();
        channel.deleteMessages(msgs).queue();
    }
}
