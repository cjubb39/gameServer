package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main Server implementation. Starts the server and allows up to 50
 * connections, each executed on different threads
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class Server {

	public final static PrintStream realStdOut = System.out;
	private ServerSocket serveSocket;
	private Socket client;

	/**
	 * Constructor
	 * 
	 * @param port
	 *           Port on which to start server
	 */
	public Server(int port) {

		try {
			this.serveSocket = new ServerSocket(port);

			// attempts to get IP address where server is running. Defaults to
			// localhost. May or may not return loopback address.
			String ipAddress = "";
			InetAddress ip = InetAddress.getLocalHost();
			if (ip instanceof InetAddress) {
				ipAddress = ip.getHostAddress();
			} else {
				ipAddress = "localhost";
			}

			// print out IP Address and port of server
			Server.realStdOut.println("Game Server started at " + ipAddress + ":"
					+ port + ".  Waiting for connections");

			this.waitForConnect();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			this.closeConnections();
		}
	}

	/**
	 * Method accepts first 50 connections to server. For each connection, a new
	 * thread controlling that connection is started.
	 */
	public void waitForConnect() {
		int connectCount = 0;

		while (connectCount < 50) {
			try {
				this.client = serveSocket.accept();
				connectCount++;

				new Thread(new ServerWorker(this.client)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes socket on method call
	 */
	public void closeConnections() {
		try {
			this.serveSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}