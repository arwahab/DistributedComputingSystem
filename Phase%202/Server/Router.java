import java.net.*;
import java.io.*;

public class Router
{
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    private static Object[][] routingTable = new Object[10][2];
    //In routingTable, 1st IP address, 2nd socket number
    private static int index = 0;//index for routing table
    private static int serverSocketNum = 5555;
    private static boolean running = true;
    
    private static Object requestedIP = null;
    private static Object requestedSocket = null;
    
    public static void main(String[] args) throws IOException
    {
        acceptConnections();
        
        clientSocket.close();
        serverSocket.close();
    }
    
    public static void acceptConnections() throws IOException
    {
        try
        {
            serverSocket = new ServerSocket(serverSocketNum);
            System.out.println("System is listening on port: " + serverSocketNum);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error in accepting connections");
            System.exit(1);
        }
    }
    
    //delete later?
    public static void createThreads()
    {
        try
        {
            clientSocket = serverSocket.accept();
            //create thread
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Error in creating threads");
        }
    }
    
    public static void acceptIP()
    {
        routingTable[index][0] = clientSocket.getInetAddress().getHostAddress();
        routingTable[index][1] = clientSocket;
    }
    
    public static void lookUpIP(int ind)
    {
        requestedIP = sendIP(ind);
        requestedSocket = sendSocket(ind);
    }
    
    public static Object sendIP(int ind)
    {
        Object resultIP;
        
        resultIP = routingTable[ind][0];
        
        return resultIP;
    }
    
    public static Object sendSocket(int ind)
    {
        Object resultSocket;
        
        resultSocket = routingTable[ind][1];
        
        return resultSocket;
    }
}