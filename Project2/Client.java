import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keahi on 04-Nov-16.
 */

public class Client implements Runnable {
    // Instance variables
    private int Port;
    private String IP;
    private int routerPort;
    private String routerAddress;

    private String fileName;

    Socket socket;

    public Client(int port, String ip, String file) {
        routerPort = port;
        routerAddress = ip;

        fileName = file;
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

            out.writeUTF(fileName);

            int fileSize = in.readInt();
            int bufferSize = in.readInt();
            String extension = in.readUTF();

            int numberOfByteArrays = fileSize / bufferSize;
            int remainderBytes = fileSize % bufferSize;

            SimpleDateFormat formatter = new SimpleDateFormat("HH mm ss SSS");
            File file = new File(formatter.format(new Date()) + extension);
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);

            PrintWriter text = new PrintWriter(new File(formatter.format(new Date())));
            long begin, end;

            for (int i = 0; i < numberOfByteArrays; i++)
            {
                begin = System.currentTimeMillis();
                byte[] buffer = new byte[bufferSize];
                in.read(buffer, 0, bufferSize);
                end = System.currentTimeMillis();
                fos.write(buffer);
                text.println(end - begin);
            }

            // Get the remainder
            byte[] buffer = new byte[remainderBytes];
            
            in.read(buffer, 0, remainderBytes);
            fos.write(buffer);

            fos.close();

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
