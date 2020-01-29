//Website Libraries
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
//Java Libraries
import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main extends ListenerAdapter implements EventListener {//main method
    public static void main(String[] args) throws LoginException {//start string
        JDA jda = new JDABuilder(getToken()).addEventListeners(new Main()).build();
        jda.addEventListener(new Commands());
    }//end string

    public static String getToken() {//getToken method
        String token = "";

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {//start try statement
            Properties prop = new Properties();
            prop.load(input);
            token = prop.getProperty("token");
        }//end try statement
        catch (IOException e) {//start catch statement
            e.printStackTrace();
            System.out.println("Enter the bot token: ");
            Scanner in = new Scanner(System.in);
            token = in.nextLine();
            in.close();
        }//end catch statement

        return token;
    }//end getToken method
}//end main method
