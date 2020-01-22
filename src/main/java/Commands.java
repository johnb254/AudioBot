import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String raw = msg.getContentRaw();

        System.out.println("New Message Received:");
        System.out.println(raw);

        if (raw.contains("!hello")) {
            sayHello(msg);
        }
    }

    public boolean sayHello(Message msg){
        msg.getChannel().sendMessage("Hello " + msg.getGuild().getName() + "!").queue();
        return true;
    }
}
