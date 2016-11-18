import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.Socket;

public class Node {

    private static String routerAddress;
    private static int routerPort;
    private static int serverPort;

    public static void main(String[] args){
        routerAddress = JOptionPane.showInputDialog(null, "What is the address of the router?");
        routerPort = Integer.parseInt(JOptionPane.showInputDialog(null, "What port is the router listening on?"));
        serverPort = Integer.parseInt(JOptionPane.showInputDialog(null, "What port should you be accessed on?")); // port number

        ServerParent server = new ServerParent(serverPort, routerAddress, routerPort);
        (new Thread(server)).start();

        Node n = new Node();

    }

    public Node(){
        JFrame frame = new JFrame("Node");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JButton go = new JButton("GO");
        go.setBounds(100, 125, 100, 40);
        go.setFont(new Font("Arial", Font.BOLD, 18));
        JButton remove = new JButton("Remove");
        remove.setBounds(50, 200, 200, 40);
        remove.setFont(new Font("Arial", Font.BOLD, 18));
        JComboBox list = new JComboBox();
        list.addItem("song.mp3");
        list.addItem("song2");
        list.addItem("song3");
        list.addItem("song4");
        list.addItem("song5");
        list.addItem("song6");
        list.addItem("song7");
        list.addItem("song8");
        list.setBounds(50, 75, 200, 30);
        list.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel heading = new JLabel("Pick your file:", SwingConstants.CENTER);
        heading.setBounds(50, 25, 200, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        frame.add(go);
        frame.add(list);
        frame.add(heading);
        frame.add(remove);

        go.addActionListener(e -> {
            Client ct = new Client(routerPort, routerAddress, list.getSelectedItem().toString());
            (new Thread(ct)).start();
        });
        remove.addActionListener(e -> {
            try {
                Socket routerSocket = new Socket(routerAddress, routerPort);
                DataOutputStream out = new DataOutputStream(routerSocket.getOutputStream());
                out.writeUTF("SERVER_OFFLINE");
                out.writeUTF(Inet4Address.getLocalHost().getHostAddress());
                System.exit(0);
            }catch(Exception err){
                err.printStackTrace();
            }
        });
        frame.setVisible(true);
    }
}

