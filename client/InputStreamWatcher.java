package client;

import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JTextArea;

/**
 * Watches an InputStream and prints data from that stream to JTextArea
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class InputStreamWatcher implements Runnable {

	private Scanner scanner;
	private Object playing;
	private JTextArea area;
	private boolean indicator;

	/**
	 * Constructor
	 * 
	 * @param s
	 *           InputStream to read
	 * @param area
	 *           JTextArea to be written to
	 * @param playing
	 *           Object used as notifier for when Game Over signal is sent
	 */
	public InputStreamWatcher(InputStream s, JTextArea area, Object playing) {
		this.scanner = new Scanner(s);
		this.scanner.useDelimiter(System.getProperty("line.separator"));
		this.playing = playing;
		this.area = area;
		this.indicator = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String message = null;
		String newline = System.getProperty("line.separator");

		// methods ends when indicator set to false
		while (indicator) {
			if (this.scanner.hasNext()) {
				message = this.scanner.next();

				// check contents of message
				if (message.equals("GAMEPLAYOVERNOW")) {

					// send Game Over signal
					synchronized (this.playing) {
						this.playing.notifyAll();
					}
					indicator = false;

				} else if (message.equals("CHOOSEGAMENOW")) {
					// do not print this message to screen
				} else {
					this.area.append(message + newline);
				}
			}
		}
	}
}