import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keahi on 04-Nov-16.
 */

public class Client implements Runnable {
    // Instance variables
    static int Port;
    static String IP;
    static int routerPort;
    static String routerAddress;

    Socket socket;

    public Client(int port, String ip) {
        routerPort = port;
        routerAddress = ip;
    }

    public Client() {
    }

    public void run() {
        try {
            setAddress(); //retrieving IP address from router
            System.out.println(IP);
            System.out.println(Port);
            socket = new Socket(IP, Port);
            System.out.println(String.format("Connecting to %s:%d", IP, Port));

            // Get the input and output streams
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int fileSize = in.readInt();
            int bufferSize = in.readInt();

            int numberOfByteArrays = (int) fileSize / bufferSize;
            int remainderBytes = (int) fileSize % bufferSize;

            int index = 0;

            SimpleDateFormat formatter = new SimpleDateFormat("HH mm ss SSS");
            File file = new File(formatter.format(new Date()) + ".mp3"); // TODO: Change this
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);

            for (int i = 0; i < numberOfByteArrays; i++)
            {
                byte[] buffer = new byte[bufferSize];
                in.read(buffer, index, bufferSize);
                fos.write(buffer);

                index += bufferSize;
            }

            // Get the remainder
            byte[] buffer = new byte[remainderBytes];
            
            in.read(buffer, index, remainderBytes);
            fos.write(buffer);

            fos.close();

            // Get input from the user (to send to the server)
            /*
            Scanner scanner = new Scanner(System.in);
            String nextLine = scanner.nextLine();

            byte[] nextData;
            */

            // Client loop
            /*
            while (true) {
                nextData = new byte[BUFFER_SIZE];
                in.read(nextData, 0, BUFFER_SIZE);

                System.out.println("Received bytes...");

                //System.out.println(String.format("Sending: \"%s\"", nextLine));

                //out.writeUTF(nextLine);
                //System.out.println(in.readUTF());

                //nextLine = scanner.nextLine();
            }
            */
        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e);
        }
    }

    public void setAddress() {
        try {
            Socket routerSocket = new Socket(this.routerAddress, this.routerPort);
            DataOutputStream out = new DataOutputStream(routerSocket.getOutputStream());
            DataInputStream in = new DataInputStream(routerSocket.getInputStream());

            out.writeUTF("CLIENT");
            IP = in.readUTF();
            Port = Integer.parseInt(in.readUTF());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
