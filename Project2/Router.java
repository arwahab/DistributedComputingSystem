import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Router {
    private static List<String> routingTable = new ArrayList<>();
    private static int portNumber = 5555; //hard code port nubmer here

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Router is listening on port: " + portNumber);


        while (true) {
            Socket clientSocket = serverSocket.accept();
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            String inputLine = in.readUTF();
            System.out.println("request type: " + inputLine);
            String serverIpAddress;

            switch (inputLine) {
                case "SERVER":
                    System.out.println("server online request");
                    serverIpAddress = in.readUTF();
                    addIpAddress(serverIpAddress);
                    System.out.println("IP Address: " + serverIpAddress + " was added to routing table");
                    break;
                case "SERVER_OFFLINE":
                    System.out.println("server offline request");
                    serverIpAddress = in.readUTF();
                    removeIpAddress(serverIpAddress);
                    System.out.println("IP Address: " + serverIpAddress + " was removed from routing table");
                    break;
                case "CLIENT":
                    System.out.println("client request");
                    out.writeUTF(retrieveIpAdress());
                    break;
            }
            clientSocket.close();
        }

    }

    static void addIpAddress(String serverAddress) {
        routingTable.add(serverAddress);

        //prints every contents on the routing table
        routingTable.forEach(System.out::println);
    }

    static void removeIpAddress(String serverAddress) {
        routingTable = routingTable.stream()
                .filter(i -> !i.equals(serverAddress))
                .collect(Collectors.toList());

        //prints rounting table after removal
        routingTable.forEach(System.out::println);
    }

    static String retrieveIpAdress() {
        Random randomGenerator = new Random();
        return routingTable.get(randomGenerator.nextInt(routingTable.size()));
    }

}
