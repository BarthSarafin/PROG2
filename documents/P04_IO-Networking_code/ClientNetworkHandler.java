
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientNetworkHandler extends Observable implements Runnable {

    private boolean running = true;  // flag to stop thread
    private String message = "";     // Never modify yourself! 
                                     // use receivedMessage
    private String serverAddress;
    private int serverPort;

    public ClientNetworkHandler(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void run() {
        System.out.println("Client Network Handler started");
        // TODO connect to server
        while (running) {
            // TODO handle chat protocol
        }
        // TODO disconnect from server
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
