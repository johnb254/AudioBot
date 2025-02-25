//Website Libraries
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
//Java Libraries
import java.util.Random;
import java.util.concurrent.TimeUnit;

//const Discord = require("discord.js");

public class Commands extends ListenerAdapter {//main method
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {//onMessageReceived method
        Message msg = event.getMessage();
        String raw = msg.getContentDisplay();

        System.out.println("New Message Received:");
        System.out.println(raw);

        if (raw.contains("!hello")) {//start if statement
            sayHello(msg);
        }//end if statement
        else if (raw.contains("!obama")){//start else-if statement
            obamaAudio(msg);
        }//end else-if statement
        else if (raw.toLowerCase().contains("when i figure out how to win")){
            msg.getChannel().sendMessage("https://i.kym-cdn.com/photos/images/original/001/686/103/937").queue();
        }
        else if (raw.contains("!baby")){
            getBaby(msg);
        }
    }//end onMessageReceived method

    public void sayHello(Message msg){//sayHello method
        msg.getChannel().sendMessage("Hello " + msg.getGuild().getName() + "!").queue();
    }//end sayHello method

    public void obamaAudio(Message msg) {//obamaAudio method
        String content = msg.getContentDisplay().substring(7).trim();
        if (content.length() < 280) {//start if statement
            System.out.println(content);
            try (final WebClient webClient = new WebClient()) {//start try statement
                String mainpageURL = "http://talkobamato.me/synthesize.py";
                HtmlPage page1 = webClient.getPage(mainpageURL);

                HtmlForm inputForm = page1.getHtmlElementById("text_input");
                HtmlTextArea inputText = inputForm.getTextAreaByName("input_text");
                HtmlSubmitInput submitButton = (HtmlSubmitInput) inputForm.getElementsByTagName("input").get(0);

                inputText.setText(content);

                String page2 = submitButton.click().getUrl().toString();

                System.out.println(page2);
                HtmlPage webPage2 = webClient.getPage(page2);
                msg.getChannel().sendMessage("Please wait. Receiving message from Barack Obama himself...").queue();
                TimeUnit.SECONDS.sleep(30);
                webPage2.refresh();
                //String vidUrl = webPage2.getUrl().toString();
                String vidUrl = "http://talkobamato.me/synth/output/" + webPage2.getUrl().toString().substring(47) + "/obama.mp4";
                System.out.println(vidUrl);

                msg.getChannel().sendMessage("Message received: \n " + vidUrl).queue();

            } //end try statement
            catch (Exception e) {//start catch statement
                e.printStackTrace();
                msg.getChannel().sendMessage("An error occurred. Transmission terminated.").queue();
            }//end catch statement
        } //end if statement
        else {//start else statement
            msg.getChannel().sendMessage("280 character limit exceeded.").queue();
        }//end else statement
    }//end obamaAudio method

    public void getBaby(Message msg) {
        RestClient client = new HttpRestClient();

        Submissions retriever = new Submissions(client);

        Submission babyPost;

        try {
            do {
                babyPost = retriever.ofSubreddit("babies", SubmissionSort.HOT, -1, 100, null, null, true).get((new Random().nextInt(98) + 1));
            } while (babyPost.isNSFW());
            msg.getChannel().sendMessage(babyPost.getUrl()).queue();
        } catch (Exception e) {
            e.printStackTrace();
            msg.getChannel().sendMessage("Error occurred").queue();
        }
    }

}//end main method
