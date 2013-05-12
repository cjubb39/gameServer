package client;

import java.io.InputStream;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * NOT YET IMPLEMENTED IN CODE
 * 
 * Watched given input and sends that input to given output.
 * 
 * @author Chae Jubb
 * @version 1.0
 *
 */
public class OutputStreamWatcher implements Runnable {

	private PrintWriter output;
	// private InputStream input;
	private Scanner scanner;

	/**
	 * Constructor
	 * @param in InputStream to watch
	 * @param out PrintWriter to send data
	 */
	public OutputStreamWatcher(InputStream in, PrintWriter out) {
		// this.input = in;
		this.output = out;
		this.scanner = new Scanner(in);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			this.output.println(this.scanner.next());
		}
	}
}