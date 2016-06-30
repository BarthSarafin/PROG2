package lab04;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Observable;

public class ClientNetworkHandler extends Observable implements Runnable {

    private boolean running = true;  // flag to stop thread
    private String message = "";     // Never modify yourself! 
                                     // use receivedMessage
    private String serverAddress;
    private int serverPort;
    private Socket soc;
    private InputStream in;
    private OutputStream out;

    public ClientNetworkHandler(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void run() {
        System.out.println("Client Network Handler started");
        // TODO connect to server
        soc = new Socket();
        //create a client socket
        InetSocketAddress endpoint;
/*        byte[] receivedMessage = new byte[0];
        boolean receiving = false;
        boolean messageStarted = false;
        boolean firstFlag= false;
        int messageLength = 0;
        String length = "";*/
        byte[] header = new byte[4];
        try {
            endpoint = new InetSocketAddress(serverAddress, serverPort);
            soc.connect (endpoint);
            //connect to connection endpoint (server)
            System.out.println("Connected to server...");
            out = soc.getOutputStream();
            in = soc.getInputStream();

        while (running) {
            // TODO handle chat protocol

                if(in.available() >= 4) {
                        in.read(header, 0, 4);
                        //System.out.println("Received header: "+new String(header));

                        int length = Byte.toUnsignedInt(header[1]);
                        //System.out.println("Length: "+length);
                        byte[] message = new byte[length];
                        in.read(message,0,length);
                        //System.out.println("Received Message: " + new String(message));
                        receivedMessage(new String(message));
                }
        }
        } catch ( IOException e) {
            System.err.println("Exception during connection setup");
        }
        // TODO disconnect from server
        try {
            soc.close();
            //Close the socket and its streams
            System.out.println ("Disconnected from server...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client Network Handler stopped");
    }

    public void stop() {
        running = false;
    }

    /*
     * This message is called by the GUI to send a message
     */
    public synchronized void sendMessage(String message) {
        // TODO Send message to server, if connection is open.
        int basicHeaderLength = 4;
        byte length = (byte)message.length();
        byte[] header = new byte[4];
        header[0] = 'M';
        header[1] = length;
        header[2] = '@';
        header[3] = '@';
        byte[] byteMessage = message.getBytes();

        byte[] data = mergeArray(header,byteMessage);


        try {

            out.write(data);
            System.out.println("Sent: "+new String(data));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] mergeArray(byte[] one, byte[] two) {
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0         ,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }

    /* 
     * Call this to send a received message to the GUI
     */
    private synchronized void receivedMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers();
    }

    /* 
     * This method is called by the GUI-Thread
     */
    public synchronized String getMessage() {
        return message;
    }
}
