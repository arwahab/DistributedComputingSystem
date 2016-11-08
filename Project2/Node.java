import java.io.*;
import java.util.Scanner;

public class Node {

     public static void main(String[] args) throws IOException {
        int routerPort = 5555; // port number
        boolean Running = true;

         Server server = new Server(routerPort);
         server.run();

        Scanner scan = new Scanner(System.in);

        // Creating threads with accepted connections
        while (Running == true)
        {
            try {
                if(scan.hasNext()) {
                    Client ct = new Client(5656, "10.0.0.1");
                    ct.run();
                }
            }catch (Exception e) {
                System.err.println("Unknown error has occurred.");
                System.exit(1);
            }
        }//end while
    }
}

