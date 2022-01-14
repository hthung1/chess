
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Connectable {
	Packet packet;
    Socket requestSocket;
    
    Client(){
    	super();
    	setColor("black");
    }
    
   
    void run()
    {
        try{
           
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");
            
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            
            do{
                try{
                	packet = (Packet) in.readObject();
    				handlePacket(packet);
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!packet.isExit());  
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
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
		requestSocket.close();
	}
    
    public static void main(String args[])
    {
        Client client = new Client();
        client.run();
    }
}
