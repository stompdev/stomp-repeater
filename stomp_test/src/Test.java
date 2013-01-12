import java.io.IOException;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;

public class Test implements Listener {

    /* nazwa hosta serwera */
    private static final String SERVER_HOST = "ibm.suder.net.pl";

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

    /* Loger */
    private static Logger logger;

    static {
        logger = Logger.getLogger(Test.class);
        PropertyConfigurator.configure("log4j.properties");
    }

    @Override
    public void message(Map arg0, String messageBody) {
        logger.info("Received new message: " + messageBody);

        client.send(OUT_QUEUE, messageBody);

        logger.info("Sent message: " + messageBody);
    }

    public static void main(String args[]) {
        try {
            client = new Client(SERVER_HOST, SERVER_PORT, SERVER_USERNAME, SERVER_PASSWORD);
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Test t = new Test();
        client.subscribe(IN_QUEUE, t);
    }

}
