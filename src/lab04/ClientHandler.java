package lab04;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
	private Socket clntSock; // Socket connect to client
	private Logger logger; // ServerPackage logger

	public ClientHandler(Socket clntSock, Logger logger) {
		this.clntSock = clntSock;
		this.logger = logger;
	}

	public void handleClient(Socket clntSock, Logger logger) {
		try {
			int totalBytesEchoed = 0; // Bytes received from client
			// Get the input and output I/O streams from socket
			totalBytesEchoed = EchoProtocol(clntSock);
			logger.info("Client " + clntSock.getRemoteSocketAddress()
					+ ", echoed " + totalBytesEchoed + " bytes.");

		} catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		} finally {
			try {
				logger.log(Level.INFO, "Closing client socket");
				clntSock.close();
			} catch (IOException e) {
			}
		}
	}

	private int EchoProtocol(Socket clntSock) throws IOException {
		InputStream in = clntSock.getInputStream();
		OutputStream out = clntSock.getOutputStream();
		// clntSock.setTcpNoDelay(true);

		int totalBytesEchoed = 0; // Bytes received from client
		int recvMsgSize = 0; // Size of received message
		byte[] echoBuffer = new byte[32]; // Receive Buffer

		// Non-blocking version: Receive until there are no bytes remaining in the receive buffer, indicated by -1
//		 do{
//		   if(in.available()>=1) { //don't block if there is nothing to read
//		     if((recvMsgSize = in.read(echoBuffer)) != -1){
//		       out.write(echoBuffer, 0, recvMsgSize); //return (echo) what was received
//		       totalBytesEchoed += recvMsgSize;
//		       logger.info(""+recvMsgSize);
//		     }
//		   }
//		   else {
//			   //do a call to read() after some timeout (handled manually) in order to find out if the stream may be closed (because the stream's/socket's methods won't tell you)
//		   }
//		 }while(recvMsgSize!=-1);

		// Blocking version: Receive until there are no bytes remaining in the
		// receive buffer, indicated by -1
		while ((recvMsgSize = in.read(echoBuffer)) != -1) {
			out.write(echoBuffer, 0, recvMsgSize); // return (echo) what was
													// received
			totalBytesEchoed += recvMsgSize;
		}
		return totalBytesEchoed;
	}

	public void run() {
		handleClient(clntSock, logger);
	}
}