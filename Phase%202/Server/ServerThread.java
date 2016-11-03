import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by keahi on 20-Oct-16.
 */

public class ServerThread implements Runnable {
    private static final int TIMEOUT = 60000; // Socket timeout in milliseconds.

    private Socket socket;

    private BufferedWriter out;
    private BufferedReader in;

    private InetAddress address;

    private String hostIP; // Server IP
    private String routerIP; // Router IP

    private int port = 5555;

    public ServerThread(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException: " + e.getMessage());
            return;
        }

        try {
            // Creating a Socket to connect to the Router.
            //socket = new Socket();

            //SocketAddress address = new InetSocketAddress(routerIP, port);
            //socket.connect(address);

            // Get the I/O streams.
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Couldn't connect to " + address);
            System.out.println("IOException: " + e.getMessage());
            return;
        }

        try {
            // Send our address to the Router (this step could be eliminated - the Router already knows our IP).
            // This step could be used to send the identifier of the client we want to connect to.
            //out.write(address.toString());

            // Get the client's IP address from the Router.
            //String clientAddress = in.readLine();

            // *************************
            // Drop connection to Router
            // *************************
            /*
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error disconnecting from Router: " + e.getMessage());
                System.out.println("ServerThread will continue...");
            }*/

            // Create a Socket for the Client based on the previous information from the Router.
            // Wait for a connection
            //Socket clientSocket = new Socket(clientAddress, port); // TODO: Change 'port' to something dynamic?
            Socket clientSocket = socket;


            // Server thread loop

            //BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //BufferedWriter clientOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader clientIn = in;
            BufferedWriter clientOut = out;

            String fromClient;

            while ((fromClient = clientIn.readLine()) != null)
            {
                System.out.println("Client says: " + fromClient);

                if (fromClient.equals("Bye."))
                    break;

                String fromServer = fromClient.toUpperCase();
                System.out.println("Server says: " + fromServer);

                clientOut.write(fromServer);
            }

            // Disconnect from ClientThread
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            return;
        }
    }
}
