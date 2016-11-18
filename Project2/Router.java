import javax.swing.*;
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
    private static List<IpPort> routingTable = new ArrayList<>();
    private static int routerPort; //hard code port nubmer here
    private static String clientAddress;
    private static int otherPort;
    private static String otherAddress;

    public static class IpPort {
        String ipAddress;
        int portNum;

        IpPort(String ip, int port) {
            ipAddress = ip;
            portNum = port;
        }

        String getIp() {
            return ipAddress;
        }

        int getPort() {
            return portNum;
        }

        @Override
        public String toString() {
            return String.format("IP address: %s\nPort Number: %d", ipAddress, portNum);
        }
    }

    public static void main(String[] args) throws IOException {

        routerPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Router Port"));
        otherAddress = JOptionPane.showInputDialog(null, "Other Address");
        otherPort = Integer.parseInt(JOptionPane.showInputDialog(null, "Other router Port"));
        ServerSocket serverSocket = new ServerSocket(routerPort);
        System.out.println("Router is listening on port: " + routerPort);


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                clientAddress = clientSocket.getInetAddress().getHostAddress();

                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                String inputLine = in.readUTF();
                System.out.println("clientAddress: " + clientAddress);
                System.out.println("request type: " + inputLine);
                String serverIpAddress;
                int serverPortNumber;

                switch (inputLine) {
                    case "SERVER":
                        System.out.println("server online request");
                        serverIpAddress = in.readUTF();
                        serverPortNumber = Integer.parseInt(in.readUTF());
                        addIpAddress(serverIpAddress, serverPortNumber);
                        System.out.println("IP Address: " + serverIpAddress + " was added to routing table");
                        break;
                    case "SERVER_OFFLINE":
                        System.out.println("server offline request");
                        int previousSize = routingTable.size();
                        serverIpAddress = in.readUTF();
                        removeIpAddress(serverIpAddress);
                        int currentSize = routingTable.size();
                        if (previousSize == currentSize) {
                            System.out.println("No IP Address found");
                        }
                        System.out.println("IP Address: " + serverIpAddress + " was removed from routing table");
                        break;
                    case "CLIENT":
                        System.out.println("client request");
                        IpPort ipPort = retrieveIpAddress();
                        if (ipPort == null) {
                            out.writeUTF("");
                            out.writeUTF("");
                        }
                        out.writeUTF(ipPort.getIp());
                        out.writeUTF(String.valueOf(ipPort.getPort()));
                        break;
                    case "SWITCH_ROUTER":
                        System.out.println("client request");
                        IpPort ipPortFromOtherCluster = retrieveForOtherCluster();
                        if (ipPortFromOtherCluster == null) {
                            out.writeUTF("");
                            out.writeUTF("");
                        }
                        out.writeUTF(ipPortFromOtherCluster.getIp());
                        out.writeUTF(String.valueOf(ipPortFromOtherCluster.getPort()));
                        break;

                }
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }

    static void addIpAddress(String serverAddress, int serverPort) {
        routingTable.add(new IpPort(serverAddress, serverPort));

        //prints every contents on the routing table
        System.out.println("Contents of routing table");
        System.out.println("------------------");
        routingTable.forEach(System.out::println);
        System.out.println("------------------");
    }

    static void removeIpAddress(String serverAddress) {
        routingTable = routingTable.stream()
                .filter(i -> !i.getIp().equals(serverAddress))
                .collect(Collectors.toList());

        //prints rounting table after removal
        System.out.println("Contents of routing table");
        System.out.println("------------------");
        routingTable.forEach(System.out::println);
        System.out.println("------------------");
    }

    static IpPort retrieveIpAddress() {
        Random random = new Random();
        int randomInt = random.nextInt(1);

        if (routingTable.size() == 1 || randomInt == 1) {
            return switchRouter();
        }
        if (randomInt == 0) {
            System.out.println("client address: " + clientAddress);
            for (IpPort ipPort : routingTable) {
                if (!ipPort.getIp().equals(clientAddress)) {
                    return ipPort;
                }
            }
        }
        return null;
    }

    static IpPort retrieveForOtherCluster() {
        System.out.println("client address: " + clientAddress);
        for (IpPort ipPort : routingTable) {
            if (!ipPort.getIp().equals(clientAddress)) {
                return ipPort;
            }
        }
        return null;
    }

    static IpPort switchRouter() {
        String ip = "";
        String port = "";
        try {
            Socket socket = new Socket(otherAddress, otherPort);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("SWITCH_ROUTER");
            ip = in.readUTF();
            port = in.readUTF();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new IpPort(ip, Integer.parseInt(port));
    }

}
