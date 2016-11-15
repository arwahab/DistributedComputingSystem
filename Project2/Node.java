import javax.swing.*;
import java.awt.*;

public class Node {

    private static String routerAddress;
    private static int routerPort;

    public static void main(String[] args){
        routerAddress = JOptionPane.showInputDialog(null, "What is the address of the router?");
        routerPort = Integer.parseInt(JOptionPane.showInputDialog(null, "What port should you be accessed on?")); // port number

        ServerParent server = new ServerParent(routerPort);
        (new Thread(server)).start();

        Node n = new Node();
    }

    public Node(){
        JFrame frame = new JFrame("Node");
        frame.setSize(300, 225);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JButton go = new JButton("GO");
        go.setBounds(100, 125, 100, 40);
        go.setFont(new Font("Arial", Font.BOLD, 18));
        go.addActionListener(e -> {
            Client ct = new Client(5555, routerAddress);
            (new Thread(ct)).start();
        });
        JComboBox list = new JComboBox();
        list.setBounds(50, 75, 200, 30);
        list.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel heading = new JLabel("Pick your file:", SwingConstants.CENTER);
        heading.setBounds(50, 25, 200, 50);
        heading.setFont(new Font("Arial", Font.BOLD, 24));

        frame.add(go);
        frame.add(list);
        frame.add(heading);

        frame.setVisible(true);
    }
}

