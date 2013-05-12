package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

import sharedResources.GameType;
import sharedResources.GameTypeWrap;

/**
 * Main Client controller class that interfaces with user. Allows user to choose
 * game, play game, and choose to play again.
 * 
 * @author Chae Jubb
 * @version 1.0
 */
public class Client {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private GameType gt = null;
	private String hostname;
	private int port;

	/**
	 * Constructor
	 * 
	 * @param hostname
	 *           String representing hostname of Server
	 * @param port
	 *           Integer representing port to use for connection with Server
	 */
	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * Primary method to begin a session of gaming
	 * 
	 * @param firstTime
	 *           If false, will attempt to close open connections according to
	 *           this.closeConnections(false)
	 * @param chooseAgain
	 *           If true, will prompt user for game selection choice. Otherwise,
	 *           uses already selected choice.
	 */
	public void init(boolean firstTime, boolean chooseAgain) {
		// if playing a second time, reset connections
		if (!firstTime) {
			this.closeConnections(false);
		}

		try {
			this.socket = new Socket(this.hostname, this.port);

			System.out.println("Connected to the server");
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new Scanner(this.socket.getInputStream());
			this.in.useDelimiter(System.getProperty("line.separator"));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			this.closeConnections(true);

		} catch (IOException e) {
			e.printStackTrace();
			this.closeConnections(true);
		}

		if (chooseAgain) {
			this.chooseGame();
		} else {
			this.out.println("GAMECHOICE:" + this.gt.toString());
		}

		this.playGame();

		this.closeConnections(true);
	}

	/**
	 * Controller for allowing user to choose game which to play. Game must be in
	 * sharedResources.GameType enum to be able to be selected.
	 */
	private void chooseGame() {
		while (!this.in.hasNext("CHOOSEGAMENOW")) {
			if (this.in.hasNext()) {
				this.in.next();
			}
		}

		System.out.println(this.in.next());
		System.out.println("MARK");

		GameTypeWrap choice = new GameTypeWrap(null);
		Object indicator = false;

		new GameChooser(choice, indicator);

		// waits until GameChooser has completed to continue
		synchronized (indicator) {
			try {
				indicator.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// sets choice as chosen from GameChooser
		this.gt = choice.getValue();

		// sends choice to server
		if (!choice.getValue().equals(null)) {
			this.out.println("GAMECHOICE:" + choice.getValue().toString());
		} else {
			this.closeConnections(true);
		}

	}

	/**
	 * Controller for playing game. Provides interface for Server IO.
	 */
	public void playGame() {
		GamePlayer gameGUI = new GamePlayer(this.out);
		JTextArea serverResponse = gameGUI.getServerResponseArea();

		System.out.println("Starting playing");

		// starts thread for listening for server output
		Object playing = false;
		Thread inputWatcherT;
		try {
			inputWatcherT = new Thread(new InputStreamWatcher(
					this.socket.getInputStream(), serverResponse, playing));
			inputWatcherT.start();
			// new Thread(new OutputStreamWatcher(toServer, this.out)).start();
		} catch (IOException e) {
			e.printStackTrace();
			this.closeConnections(true);
		}

		// waits until Game Over signal given inside inputWatcherT thread
		synchronized (playing) {
			try {
				playing.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// choose game again indicator
		GamePlayAgain gpa = new GamePlayAgain(this.gt);
		gpa.init();
		int indicator = gpa.action();

		// makes next action based on return from Play Again dialogue
		if (indicator == 1) {
			this.init(false, false);
		} else if (indicator == 2) {
			this.init(false, true);
		} else if (indicator == 0) {
			this.closeConnections(true);
		}

		gameGUI.dispose();

	}

	/**
	 * Closes Scanner, PrintWriter, and connections with server
	 * 
	 * @param quit
	 *           If true, program exits
	 */
	private void closeConnections(boolean quit) {

		try {
			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connection closed");

		if (quit) {
			System.exit(0);
		}
	}
}