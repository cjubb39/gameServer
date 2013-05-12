package server;

/**
 * Simple tester for launching server on localhost:4444
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class ServerMain {

	/**
	 * Creates server at port 4444
	 * 
	 * @param args
	 *           Not used
	 */
	public static void main(String[] args) {
		new Server(4444);
	}
}