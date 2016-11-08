import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by keahi on 04-Nov-16.
 */

public class Server implements Runnable {
    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Get the input and output streams
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Server loop
            while (true) {
                String nextLine = in.readUTF();
                System.out.println(String.format("Received \"%s\" and sending back \"%s\"", nextLine, nextLine.toUpperCase()));

                out.writeUTF(nextLine.toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e);
        }
    }
}
