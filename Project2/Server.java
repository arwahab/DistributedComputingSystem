import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by keahi on 04-Nov-16.
 */

public class Server implements Runnable {
    static final int BUFFER_SIZE = 8192;

    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Get the input and output streams
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String filePathToSend = in.readUTF(); // Read a string formatted like "file.ext", located in resources/file.ext
            File fileToSend = new File("resources/" + filePathToSend);

            if (!fileToSend.exists())
            {
                throw new FileNotFoundException(String.format("Couldn't find %s", fileToSend.getAbsolutePath()));
            }

            // Server loop
            int fileIndex = 0; // Track our location within the file
            int fileSize = (int)fileToSend.length();

            int numberOfByteArrays = (int) fileSize / BUFFER_SIZE; // Number of FULL byte arrays to send
            int remainderBytes = (int) fileSize % BUFFER_SIZE; // The remaining bytes, if it doesn't divide nicely

            String fileExtension;
            String filePath = fileToSend.getPath();

            int extIndex = filePath.length() - 2;
            while (!filePath.substring(extIndex, extIndex + 1).equals("."))
            {
                extIndex--;
            }

            fileExtension = filePath.substring(extIndex);

            FileInputStream fis = new FileInputStream(fileToSend);

            // Send the file's statistics (total bytes, buffer size).
            // From this, the client can calculate everything we need to know.
            out.writeInt(fileSize);
            out.writeInt(BUFFER_SIZE);
            out.writeUTF(fileExtension);

            for (int i = 0; i < numberOfByteArrays; i++)
            {
                byte[] fileBuffer = new byte[BUFFER_SIZE];
                fis.read(fileBuffer, 0, BUFFER_SIZE);

                out.write(fileBuffer);
                fileIndex += BUFFER_SIZE;

                System.out.println("Sent bytes " + fileIndex);
            }

            // Send the remaining bytes
            if (remainderBytes > 0)
            {
                byte[] remainder = new byte[remainderBytes];
                fis.read(remainder, 0, remainderBytes);

                out.write(remainder);
            }

            fis.close();

/*
            while (true) {
                fis.read(fileBuffer, fileIndex, BUFFER_SIZE);

                //String nextLine = in.readUTF();
                //System.out.println(String.format("Received \"%s\" and sending back \"%s\"", nextLine, nextLine.toUpperCase()));

                out.write(fileBuffer, 0, BUFFER_SIZE);
                System.out.println(String.format("Wrote %i bytes to client", (fileIndex + BUFFER_SIZE)));
            }
*/
        } catch (IOException e) {
            System.out.println("IOException");
            System.out.println(e);
        }
    }
}
