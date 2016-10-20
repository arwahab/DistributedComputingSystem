import java.io.*;
import java.net.*;

/**
 * Created by rmcmichael on 10/19/16.
 */
public class ClientThread extends Thread
{
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private String destination, inputline;

    ClientThread(String serverAddress, int serverPort) throws IOException
    {
        try{

            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch(UnknownHostException e){
            System.err.println("Client did not find: " + serverAddress);
        }catch(IOException e){
            System.err.println("Couldn't get I/O for the connection to: " + serverAddress);
        }
    }

    public void run()
    {
        try{

            destination = in.readLine();
            System.out.println("Connection made to: " + destination);
            out.println("Connected.");

            while((inputline = in.readLine()) != null)
            {
                System.out.println("Server said: " + inputline);
                if(inputline.equals("Bye."))
                    break;
            }
        }catch(IOException e){
            System.err.println("Could not listen to socket.");
        }
    }
}