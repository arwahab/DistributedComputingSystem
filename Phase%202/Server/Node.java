import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Node {

     public static void main(String[] args) throws IOException {
        Socket clientSocket = null; // socket for the thread
        int routerPort = 5555; // port number
        boolean Running = true;
        int ind = 0; // index in the routing table

            //Accepting connections
        ServerSocket serverSocket = null; // server socket for accepting connections
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Node is Listening on port: 5555.");
        }catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(1);
        }

        Scanner scan = new Scanner(System.in);

        // Creating threads with accepted connections
        while (Running == true)
        {
            try {
                clientSocket = serverSocket.accept();
                ServerThread t = new ServerThread(clientSocket); // creates a thread with a random port
                t.run(); // starts the thread
                ind++; // increments the index
                System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
            }catch (IOException e) {
                System.err.println("Client/Server failed to connect.");
                System.exit(1);
            }
            String temp = new String();
            if(scan.hasNext()) {
                ClientThread ct = new ClientThread();
                ct.run();
            }
        }//end while

            //closing connections
            clientSocket.close();
            serverSocket.close();

    }
}

