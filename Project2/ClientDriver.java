/**
 * Created by keahi on 11/07/16.
 */

public class ClientDriver {
    public static int PORT = 12345;
    public static String IP = "192.168.56.1";

    public static void main(String[] args) {
        //Client client = new Client(PORT, IP);
        //client.run();

        // We can also do this:
        (new Thread(new Client(PORT, IP))).start();
    }
}