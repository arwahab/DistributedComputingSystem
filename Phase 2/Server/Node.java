import java.net.Socket;

public class Node {

	//{IP address, Socket, Alias, Port, ServerFlag}
	
	String IP;
	Socket socket;
	String alias;
	int port;	
	Boolean flag;
	
	Node(){}
	
	Node(String IP, Socket socket, String alias, int port, Boolean flag){
		this.IP = IP;
		this.socket = socket;
		this.alias = alias;
		this.port = port;
		this.flag = flag;
	}
	
}
