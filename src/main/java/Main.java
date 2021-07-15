import bot.Bot;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot();

        try {
            bot.start();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
