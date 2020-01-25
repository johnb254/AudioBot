import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Commands extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String raw = msg.getContentDisplay();

        System.out.println("New Message Received:");
        System.out.println(raw);

        if (raw.substring(0, 7).equals("!hello")) {
            sayHello(msg);
        } else if (raw.substring(0, 6).equals("!obama")){
            obamaAudio(msg);
        }
    }

    public void sayHello(Message msg){
        msg.getChannel().sendMessage("Hello " + msg.getGuild().getName() + "!").queue();
    }

    public void obamaAudio(Message msg) {
        String content = msg.getContentDisplay().substring(7).trim();
        if (content.length() < 280) {
            System.out.println(content);
            try (final WebClient webClient = new WebClient()) {
                HtmlPage page1 = webClient.getPage("http://talkobamato.me/synthesize.py");

                HtmlForm inputForm = page1.getHtmlElementById("text_input");
                HtmlTextArea inputText = inputForm.getTextAreaByName("input_text");
                HtmlSubmitInput submitButton = (HtmlSubmitInput) inputForm.getElementsByTagName("input").get(0);

                inputText.setText(content);

                HtmlPage page2 = submitButton.click();
                msg.getChannel().sendMessage("Please wait. Receiving message from Barack Obama himself...").queue();
                TimeUnit.SECONDS.sleep(30);
                page2.refresh();
                String vidUrl = "http://talkobamato.me/synth/output/" + page2.getUrl().toString().substring(47) + "/obama.mp4";
                System.out.println(vidUrl);

                msg.getChannel().sendMessage("Message received: \n " + vidUrl).queue();

            } catch (Exception e) {
                e.printStackTrace();
                msg.getChannel().sendMessage("An error occurred. Transmission terminated.");
            }
        } else {
            msg.getChannel().sendMessage("280 character limit exceeded.").queue();
        }
    }
}
