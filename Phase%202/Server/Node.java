import java.io.*;
import java.util.Scanner;

public class Node {

     public static void main(String[] args) throws IOException {
        boolean Running = true;
        Scanner scan = new Scanner(System.in);

         ParentServerThread pst = new ParentServerThread();
         pst.run();

        // Creating threads requested
        while (Running == true)
        {
            if(scan.hasNext()) {
                ClientThread ct = new ClientThread();
                ct.run();
            }
        }//end while
    }
}

