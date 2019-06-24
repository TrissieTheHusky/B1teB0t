package b1tec0de.b1teb0t.main;

import b1tec0de.b1teb0t.events.CommandManager;
import b1tec0de.b1teb0t.utils.ConfigManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {

    private static JDA jda;
    private static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public static void main(String[] args) {
        System.out.println("  ____  _ _        ____ ___      _");
        System.out.println(" | __ )/ | |_ ___ / ___/ _ \\  __| | _");
        System.out.println(" |  _ \\| | __/ _ \\ |  | | | |/ _` |/ _ \\");
        System.out.println(" | |_) | | ||  __/ |__| |_| | (_| |  __/");
        System.out.println(" |____/|_|\\__\\___|\\____\\___/ \\__,_|\\___|");
        System.out.println();
        ConfigManager cm = new ConfigManager();
        cm.getAuthToken();
        try {
            String token = cm.getAuthToken();
            if(token.equals("not found")) {
                logger.error("config.json not found!\n");
                System.exit(0);
            }
            else if(token.equals("default values")) {
                logger.error("Please make sure that you fill config.json with values!");
                System.exit(0);
            }
            else {
                jda = new JDABuilder(AccountType.BOT)
                        .setToken(cm.getAuthToken())
                        .setAutoReconnect(true)
                        .addEventListener(new CommandManager())
                        .build();
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
