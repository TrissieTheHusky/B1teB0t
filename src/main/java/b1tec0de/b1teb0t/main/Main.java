package b1tec0de.b1teb0t.main;

import b1tec0de.b1teb0t.events.CommandManager;
import b1tec0de.b1teb0t.utils.ConfigManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    static JDA jda;

    public static void main(String[] args) {
        System.out.println("  ____  _ _        ____ ___      _");
        System.out.println(" | __ )/ | |_ ___ / ___/ _ \\  __| | _");
        System.out.println(" |  _ \\| | __/ _ \\ |  | | | |/ _` |/ _ \\");
        System.out.println(" | |_) | | ||  __/ |__| |_| | (_| |  __/");
        System.out.println(" |____/|_|\\__\\___|\\____\\___/ \\__,_|\\___|");
        System.out.println();
        ConfigManager cm = new ConfigManager();
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(cm.getAuthToken())
                    .setAutoReconnect(true)
                    .addEventListener(new CommandManager())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
