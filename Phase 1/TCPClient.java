   import java.io.*;
   import java.net.*;

    public class TCPClient {
       public static void main(String[] args) throws IOException {
    	   
      	
		 // Variables for setting up connection and communication
    	// File f1 = new File("lengthOutput.txt");
    	// File f2 = new File("messageTrans.txt");

         Socket Socket = null; // socket to connect with ServerRouter
         PrintWriter out = null; // for writing to ServerRouter
         BufferedReader in = null; // for reading form ServerRouter
		 InetAddress addr = InetAddress.getLocalHost();
		 String host = addr.getHostAddress(); // Client machine's IP
      	 //String routerName = "j263-08.cse1.spsu.edu"; // ServerRouter host name
		  String routerName = "10.99.12.48";
		 
		 int SockNum = 5555; // port number
			
			// Tries to connect to the ServerRouter
         try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
         } 
             catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
            } 
             catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
            }
				
      	// Variables for message passing	
         Reader reader = new FileReader("latin.txt"); 
         
         // Writing message to file (for recording data) 
         PrintWriter writer = new PrintWriter("lengthOutput.txt");
         // Writing the transmission time 
         PrintWriter writer2 = new PrintWriter("messageTrans.txt");

         
         
         
         
		 BufferedReader fromFile =  new BufferedReader(reader, 50000); // reader for the string file -- overriding default character limit 
         String fromServer; // messages received from ServerRouter
         String fromUser; // messages sent to ServerRouter
			String address ="10.99.15.66"; // destination IP (Server)
			long t0, t1, t;
			
			// Communication process (initial sends/receives
			out.println(address);// initial send (IP of the destination Server)
			fromServer = in.readLine();//initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromServer);
			out.println(host); // Client sends the IP of its machine as initial send
			t0 = System.currentTimeMillis();
      	
			// Communication while loop
         while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
				t1 = System.currentTimeMillis();
            if (fromServer.equals("Bye.")) // exit statement
               break;
				t = t1 - t0;
				System.out.println("Cycle time: " + t);
          
            fromUser = fromFile.readLine(); // reading strings from a file
            if (fromUser != null) {
               System.out.println("Client: " + fromUser);
               out.println(fromUser); // sending the strings to the Server via ServerRouter
               // write the length of the message being sent to output.txt
               writer.println(fromUser.length());
               t0 = System.currentTimeMillis();
               writer2.println(t0);
               
               
					
					
					
					
            }
            
           
            
         }
      	
         writer.close();
         writer2.close();
			// closing connections
         out.close();
         in.close();
         Socket.close();
      }
   }
