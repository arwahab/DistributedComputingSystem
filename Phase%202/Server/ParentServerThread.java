import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by billy on 11/1/2016.
 */
public class ParentServerThread implements Runnable {

    private Socket clientSocket;
    private int index;
    private boolean Running;
    private ServerSocket serverSocket;

    @Override
    public void run() {
        clientSocket = null; // socket for the thread
        index = 0; // index in the routing table
        Running = true;
        serverSocket = null; // server socket for accepting connections
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Node is Listening on port: 5555.");
        }catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(1);
        }

        while (Running == true)
        {
            try {
                clientSocket = serverSocket.accept();
                ServerThread t = new ServerThread(clientSocket); // creates a thread with a random port
                t.run(); // starts the thread
                index++; // increments the index
                System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
                clientSocket = null;
            }catch (IOException e) {
                System.err.println("Client/Server failed to connect.");
                System.exit(1);
            }
        }//end while
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
