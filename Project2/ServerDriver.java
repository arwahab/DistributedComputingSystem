import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by keahi on 11/07/16.
 */

public class ServerDriver {
    public static int PORT = 12345;

    public static void main(String[] args) {
        //Server server = new Server(PORT);
        //server.run();

        // And in a new thread:
        //(new Thread(new Server(PORT))).start();
        try {
            ServerSocket socket = new ServerSocket(12345);

            Server server = new Server(socket.accept(), new File("C:\\Users\\keahi\\Documents\\GitHub\\DistributedComputingSystem\\song.mp3"));
            System.out.println("OK");

            (new Thread(server)).start();
        }
        catch (IOException e) {

        }

         }
}
