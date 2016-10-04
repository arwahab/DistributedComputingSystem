import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {

        // Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        InetAddress addr = InetAddress.getLocalHost();
        String host = addr.getHostAddress(); // Server machine's IP
        String routerName = "10.99.27.70"; // ServerRouter host name
        int SockNum = 5555; // port number

        // Tries to connect to the ServerRouter
        try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerName);
            System.exit(1);
        }

        // Variables for message passing
        String fromServer; // messages sent to ServerRouter
        String fromClient; // messages received from ServerRouter
        //String address ="10.5.3.196"; // destination IP (Client)
        //String address = "10.99.28.128";
        String address = "10.99.12.181";

        // Communication process (initial sends/receives)
        out.println(address);// initial send (IP of the destination Client)
        fromClient = in.readLine();// initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromClient);

        // Timing
        long t0, t1, deltaT;

        // Communication while loop
        while ((fromClient = in.readLine()) != null) {
            t0 = System.nanoTime();
            System.out.println("Client said: " + fromClient);
            if (fromClient.equals("Bye.")) // exit statement
            break;
            fromServer = fromClient.toUpperCase(); // converting received message to upper case
            System.out.println("Server said: " + fromServer);
            out.println(fromServer); // sending the converted message back to the Client via ServerRouter
            t1 = System.nanoTime();

            System.out.println("Time: " + (t1 - t0) + " ns");
        }

        // closing connections
        out.close();
        in.close();
        Socket.close();
    }
}
