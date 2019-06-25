package b1tec0de.b1teb0t.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClearSystem {

    private String prefix = "!";

    public void clearCommand(String[] args, TextChannel channel) {

        int amount  = Integer.parseInt(args[0]);

        if(args.length < 1){
            channel.sendMessage("Usage: `"+prefix+"clear <amount>`");
        }else if (args.length == 1 && amount > 1 && amount <= 100) {
            try {
                delMessages(amount + 1, channel);
                Message success = channel.sendMessage("You deleted **" + args[0] + "** messages.").complete();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        success.delete().queue();
                    }
                }, 3000);
                return;

            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }else{
            Message error = channel.sendMessage("Please enter a number between 2 and 100.").complete();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    error.delete().queue();
                }
            }, 3000);
            return;
        }
    }

    private void delMessages(int amount, TextChannel channel){
        MessageHistory msgHistory = new MessageHistory(channel);
        List<Message> msgs;

        msgs = msgHistory.retrievePast(amount).complete();
        channel.deleteMessages(msgs).queue();
    }
}


