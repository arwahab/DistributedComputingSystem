import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * Created by n_jun on 11/9/2016.
 */
public class RouterTester {
    private static String routerAddress = "192.168.0.111";
    private static int port=5555;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(routerAddress, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.println("choose a client to run");
            System.out.println("1: register new server ip");
            System.out.println("2: remove server ip");
            System.out.println("3: client");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch(input){
                case "1":
                    out.writeUTF("SERVER");
                    out.writeUTF("192.168.0.111");
                    break;
                case "2":
                    out.writeUTF("SERVER_OFFLINE");
                    out.writeUTF("192.168.0.108");
                    break;
                case "3":
                    out.writeUTF("CLIENT");
                    System.out.println(in.readUTF());
                    break;
            }
        } catch (IOException e) {

        }
    }
}
