import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Map;

public class TestNoDebug implements Listener {

    /* nazwa hosta serwera */
    private static final String SERVER_HOST = "127.0.0.1";

    /* port serwera */
    private static final int SERVER_PORT = 61613;

    /* username */
    private static final String SERVER_USERNAME = "admin";

    /* password */
    private static final String SERVER_PASSWORD = "password";

    /* in queue */
    private static final String IN_QUEUE = "/queue/in";

    /* out queue */
    private static final String OUT_QUEUE = "/queue/out";

    private static Client client;

    @Override
    public void message(Map arg0, String messageBody) {
        System.err.println("0;" + System.currentTimeMillis() + ";" + messageBody.length());
        client.send(IN_QUEUE, messageBody);
    }

    public static void main(String args[]) {
        try {
            client = new Client(SERVER_HOST, SERVER_PORT, SERVER_USERNAME, SERVER_PASSWORD);
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestNoDebug t = new TestNoDebug();
        client.subscribe(OUT_QUEUE, t);
    }

}
