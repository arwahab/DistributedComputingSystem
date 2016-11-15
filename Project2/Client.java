import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by keahi on 04-Nov-16.
 */

public class Client implements Runnable {
    // Instance variables
    static int Port;
    static String IP;

    Socket socket;

    public Client(int port, String ip) {
        this.Port = port;
        this.IP = ip;
    }

    public Client() {
    }

    public void run() {
        try {
            setAddress(); //retrieving IP address from router
            socket = new Socket(IP, Port);
            System.out.println(String.format("Connecting to %s:%d", IP, Port));

            // Get the input and output streams
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Get input from the user (to send to the server)
            Scanner scanner = new Scanner(System.in);
            String nextLine = scanner.nextLine();

            // Client loop
            while (nextLine != null && !nextLine.isEmpty()) {
                System.out.println(String.format("Sending: \"%s\"", nextLine));

                out.writeUTF(nextLine);
                System.out.println(in.readUTF());

                nextLine = scanner.nextLine();
            }
        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e);
        }
    }

    public void setAddress() {
        try {
            Socket routerSocket = new Socket("10.99.9.1", 5555);
            DataOutputStream out = new DataOutputStream(routerSocket.getOutputStream());
            DataInputStream in = new DataInputStream(routerSocket.getInputStream());

            out.writeUTF("CLIENT");
            this.IP = in.readUTF();
            this.Port = Integer.parseInt(in.readUTF());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
