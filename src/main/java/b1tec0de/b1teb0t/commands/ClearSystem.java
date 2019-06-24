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

        if(args.length == 1){
            try {
                delMessages(Integer.parseInt(args[0]), channel);
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
            channel.sendMessage("Usage: `"+prefix+"clear <amount>`");
        }
    }

    private void delMessages(int amount, TextChannel channel){
        MessageHistory msgHistory = new MessageHistory(channel);
        List<Message> msgs;

        msgs = msgHistory.retrievePast(amount).complete();
        channel.deleteMessages(msgs).queue();
    }
}

