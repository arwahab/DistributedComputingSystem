import java.io.*;
import java.util.Scanner;

public class Node {

     public static void main(String[] args) throws IOException {
        int routerPort = 5555; // port number
        boolean Running = true;

         ServerParent server = new ServerParent(routerPort);
         (new Thread(server)).start();

        Scanner scan = new Scanner(System.in);

        // Creating threads with accepted connections
        while (Running)
        {
            try {
                if(scan.hasNext()) {
                    Client ct = new Client(5555, "10.99.4.47");
                    (new Thread(ct)).start();
                }
            }catch (Exception e) {
                System.err.println("Unknown error has occurred.");
                System.exit(1);
            }
        }//end while
    }
}

