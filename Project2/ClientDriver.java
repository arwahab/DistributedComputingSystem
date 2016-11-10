/**
 * Created by keahi on 11/07/16.
 */

public class ClientDriver {
    public static int PORT = 5555;
    public static String IP = "192.168.43.56";

    public static void main(String[] args) {
        //Client client = new Client(PORT, IP);
        //client.run();

        // We can also do this:
        (new Thread(new Client(PORT, IP))).start();
    }
}
