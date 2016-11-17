import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Modified by abdul on 17-Nov-16.
 */

public class ServerParent implements Runnable {
    // Instance variables
    static int Port;
    static int routerPort;
    static String routerAddress;

    ServerSocket serverSocket;
    Socket socket;

    public ServerParent(int port, String routerAddress, int routerPort) {
        this.Port = port;
        this.routerAddress = routerAddress;
        this.routerPort = routerPort;
    }

    public void run() {
        try{
            Socket routerSocket = new Socket(routerAddress, routerPort);
            DataInputStream in = new DataInputStream(routerSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(routerSocket.getOutputStream());
            out.writeUTF("SERVER");
            out.writeUTF(Inet4Address.getLocalHost().getHostAddress());
            out.writeUTF(String.valueOf(Port));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        while (true) {
            try {
                // Create a server socket and start listening
                serverSocket = new ServerSocket(Port);
                System.out.println(String.format("Listening on port %d", Port));

                // Get the connection...
                socket = serverSocket.accept();
                System.out.println(String.format("Connected to %s", socket.getInetAddress()));

                System.out.println("Sending Socket object to new Server thread");
                //(new Thread(new Server(socket))).start();
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("IOException in ServerParent");
                System.out.println(e);
            }
        }
    }
}
