/**
 * Created by keahi on 11/07/16.
 */

public class ServerDriver {
    public static int PORT = 12345;

    public static void main(String[] args) {
        //Server server = new Server(PORT);
        //server.run();

        // And in a new thread:
        (new Thread(new Server(PORT))).start();
    }
}
