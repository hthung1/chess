import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Connectable {

	 ServerSocket serverSocket;
	 Socket connection = null;
	 Packet packet;
	 
	 Server() {
		 super();
		 setColor("white");
	 }
	 
	
	public void run() {
		 try{
	        
	         serverSocket = new ServerSocket(2004, 10);
	         
	         System.out.println("Waiting for connection");
	         connection = serverSocket.accept();
	         System.out.println("Connection received from " + connection.getInetAddress().getHostName());
	         
	         out = new ObjectOutputStream(connection.getOutputStream());
	         out.flush();
	         in = new ObjectInputStream(connection.getInputStream());
	         System.out.println("Connection successful");
	         do{
	             try{
	            	 packet = (Packet) in.readObject();
	            	handlePacket(packet);
	             }
	             catch(ClassNotFoundException classnot){
	                 System.err.println("Data received in unknown format");
	             }
	         }while(!packet.isExit());  
	     }
	     catch(IOException ioException){
	        ioException.printStackTrace();
	     }
	     finally{
	         try{
	             closeConnections();
	         }
	         catch(IOException ioException){
	             ioException.printStackTrace();
	         }
	     }
	 }

	
	public void closeConnections() throws IOException {
		in.close();
		out.close();
		serverSocket.close();
	}
	 
	 public static void main(String[] args) {
			Server server = new Server();
			//while(true) {
				server.run();
			//}
	 }
 }
