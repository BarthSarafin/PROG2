package lab04;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Fabian on 13.05.15.
 */
public class ChatServer {



    public static void main(String[]args)throws IOException {
        if(args.length!=1)
            // Test for correct # of  args
            throw new IllegalArgumentException("Parameter(s): <Port>");
        int servPort=Integer.parseInt(args[0]); //server port



        int recvMsgSize ;
// Size of received message
        byte[] receiveBuf = new byte[30];
// Receive buffer
// Create a server socket to accept client connection requests
        ServerSocket servSoc = new ServerSocket ( servPort );
        while (true) { // Run forever, accepting and servicing connections
            Socket clntSock = servSoc.accept ();
// Get client connection
            InputStream in = clntSock.getInputStream ();
            OutputStream out = clntSock.getOutputStream ();
            while (( recvMsgSize = in.read(receiveBuf) ) != -1) {
                System.out.println("Received "+new String(receiveBuf));
                out.write ( receiveBuf , 0, recvMsgSize );
            }
            clntSock.close ();
// Server - side socket close
        }
    }
}
