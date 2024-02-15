package workshop04.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
  public static void main( String[] args ) {
      // Instantiate input and output stream
      InputStream is = null;
      OutputStream os = null;
      DataInputStream dis = null;
      DataOutputStream dos = null;
      ServerSocket serverSocket = null;
      Socket socket = null;
      
      try {
        // Server to accept port number and cookiefile.txt path as args
        String serverPort = args[0];
        String cookieFile = args[1];
        
        // Create a server
        System.out.println("Starting server at port " + serverPort);
        serverSocket = new ServerSocket(Integer.parseInt(serverPort));
        
        // Testing
        while (true) {
          System.out.println("Accepting connections...");
          socket = serverSocket.accept();
  
          // dis to retrieve input from client, dos to pump output to client.
          is = socket.getInputStream();
          os = socket.getOutputStream();
          dis = new DataInputStream(is);
          dos = new DataOutputStream(os);

          // need to store input so that once input has read, the socket doesn't close. 
          while (true) {
            String clientInput = dis.readUTF();

            try {
              if (clientInput.equals("get-cookie")) {
                System.out.println("Client input received");
                dos.writeUTF("You get a cookie");
                dos.flush();
              } 
              if (clientInput.equals("close")) {
                System.out.println("Client requesting to close connection...");
                dos.writeUTF("Closing connection...");
                break;
              } 
            } catch (Exception e) {
              // TODO: handle exception
            }
            
            System.out.println("Finished reading client input... awaiting next input.");
          }
        }   
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
}
