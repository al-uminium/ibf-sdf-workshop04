package workshop04.Client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientApp {
  public static void main( String[] args ) {
        String[] inetAddress = args[0].split(":");
        InputStream is = null;
        OutputStream os = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try {
          Socket socket = new Socket(inetAddress[0], Integer.parseInt(inetAddress[1]));
          Console cons = System.console();
          while (true) {
            System.out.println("Sending info to server...");
            String input = cons.readLine("> ");

            try {
              if (input.equals("get-cookie")) {
                os = socket.getOutputStream();
                dos = new DataOutputStream(os);
                // write to server
                dos.writeUTF(input);
                dos.flush();
                // get server response ...?
                is = socket.getInputStream();
                dis = new DataInputStream(is);
                System.out.println(dis.readUTF());
              }
  
              if (input.equals("close")) {
                socket.close();
                break;
              }
            } catch (Exception e) {
              System.out.println("Closing connection");
              socket.close();
              break;
            }

          }

        } catch (Exception e) {
          e.printStackTrace();
        } 
    } 
}
