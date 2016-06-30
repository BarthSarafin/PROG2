package lab04;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class Client {
    /*
     * Program initialization and start
     * gets the server address and port number as command line parameters
     */
    public static void main(String[] args) {
        // parse arguments   
        if (args.length != 2) { // Test for correct # of args
            throw new IllegalArgumentException("Parameters: <ServerPackage> <Port>");
        }
        String serverAddress = args[0];             // ServerPackage address
        int serverPort = Integer.parseInt(args[1]); // ServerPackage port no.
        // init & start Network Handler Thread and open connection
        ClientNetworkHandler networkHandler = 
                new ClientNetworkHandler(serverAddress, serverPort);
        new Thread(networkHandler).start();
        // init GUI
        new ClientView(networkHandler);

    }
}


@SuppressWarnings("serial")
class ClientView extends JFrame implements Observer {

    private ClientNetworkHandler networkHandler;
    private JTextPane textPane;
    private Document doc;
    private JTextArea textArea;

    public ClientView(ClientNetworkHandler networkHandler) {
        super("Chat Client");
        this.networkHandler = networkHandler;
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        textPane = new JTextPane();
        textPane.setBorder(new TitledBorder("Messages"));
        textPane.setEditable(false);
        doc = textPane.getDocument();
        try {
            doc.insertString(doc.getLength(), "Waiting for Messages\n", null);
        } catch (BadLocationException e) {;}
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        pane.add(scrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setBorder(new TitledBorder("Your Message"));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    // Enter/Return Key pressed-> send message
                    ClientView.this.networkHandler.sendMessage(
                            ClientView.this.textArea.getText());
                    ClientView.this.textArea.setText("");  // clear TextField
                }
            }
        });
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        pane.add(scrollPane, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ClientView.this.networkHandler.stop();
                ClientView.this.dispose();
                System.exit(0);
            }
        });
        pack();
        setVisible(true);
        networkHandler.addObserver(this);
        update(networkHandler, null);
    }



    /*
     * This method is called when notified by the Observable
     * and will get the message from NetworkHandler and append it to 
     * the messages area. 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable model, Object arg) {
        final String message = networkHandler.getMessage();
        SwingUtilities.invokeLater(() -> {
			try {
				ClientView.this.doc.insertString(
						ClientView.this.doc.getLength(),
						message,
						null);
			} catch (BadLocationException e) {;}
		});
    }
}