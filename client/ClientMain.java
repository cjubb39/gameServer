package client;

/**
 * Simple tester for Client
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class ClientMain {

	/**
	 * Main tester method
	 * 
	 * @param args
	 *           First argument is hostname for connection; second is for port
	 *           for connection
	 */
	public static void main(String[] args) {

		Client c = new Client(args[0], Integer.parseInt(args[1]));
		c.init(true, true);
	}
}