package bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

import static rest.RestSquilService.performQueryRequest;

public class Bot extends ListenerAdapter {
    public static final String API_KEY = "ODYwNjEzMTc4NTE2NzAxMTk1.YN9yew.g3ECMWCc__9i1SGU42YE5odMosk";

    public void start() throws LoginException {
        JDABuilder.createDefault(API_KEY).addEventListeners(this).build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // Prevent recursive calls
        if (event.getAuthor().isBot()) {
            return;
        }

        // Parse message and check if it is valid squil command
        String message = event.getMessage().getContentRaw();
        if (message.contains("!squil:")) {
            if (message.split(" ").length > 1) {
                String userID = event.getAuthor().getId();
                performQueryRequest(userID, message.split(":")[1], (res) -> {
                    if(res != null) {
                        event.getChannel().sendMessage("```" + res + "```").queue();
                    } else {
                        event.getChannel().sendMessage("```SQUIL: Something went wrong with the servers```").queue();
                    }
                });
            } else {
                event.getChannel().sendMessage("```SQUIL: Empty command arguments```").queue();
            }
        }
    }
}
