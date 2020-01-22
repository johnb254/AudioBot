import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main extends ListenerAdapter implements EventListener {
    public static void main(String[] args) throws LoginException {
        JDA jda = new JDABuilder().addEventListeners(new Main()).setActivity(Activity.watching("you")).build();
        jda.addEventListener(new Commands());
    }

    public static String getToken() {
        String token = "";

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            token = prop.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Enter the bot token: ");
            Scanner in = new Scanner(System.in);
            token = in.nextLine();
            in.close();
        }

        return token;
    }
}
