package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sharedResources.GameType;

/**
 * Controller of each connection with server.
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class ServerWorker implements Runnable {

	private Socket client;
	private PrintWriter out;
	private Scanner in;

	/**
	 * Constructor. Creates necessary Socket, PrintWriter, Scanner.
	 * 
	 * @param client
	 *           Client of connection
	 */
	public ServerWorker(Socket client) {
		this.client = client;
		System.out.println("Client Connected.");

		try {
			this.out = new PrintWriter(this.client.getOutputStream(), true);
			this.in = new Scanner(this.client.getInputStream());
			this.in.useDelimiter(System.getProperty("line.separator"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		GameType gameChoice = this.chooseGame();

		this.playGame(gameChoice);

		this.out.println("GAMEPLAYOVERNOW");

		this.closeConnections();
	}

	/**
	 * Chooses Game via interaction with client
	 * 
	 * @return chosen GameType
	 */
	private GameType chooseGame() {
		// tells client to choose game
		this.out.println("CHOOSEGAMENOW");

		// waits for response from client and then continues
		while (!this.in.hasNext("GAMECHOICE:(.*)")) {
			this.in.next();
		}
		String gameChoice = this.in.next();

		Matcher choiceMatcher = Pattern.compile("\\s*GAMECHOICE:(.*)\\s*")
				.matcher(gameChoice);

		String choice = null;
		if (choiceMatcher.find()) {
			choice = choiceMatcher.group(1);
		}

		// "converts" game response from client into GameType
		GameType gt = null;
		for (GameType s : GameType.values()) {
			if (s.toString().equals(choice)) {
				gt = s;
				break;
			}
		}
		System.out.println("GAMETYPE: " + gt.toString());
		return gt;
	}

	/**
	 * Controller to initialize and play chosen game
	 * 
	 * @param gameChoice
	 *           GameType to be played
	 */
	private void playGame(GameType gameChoice) {
		Game g = null;

		// sets up appropriate game
		switch (gameChoice) {

		case OTHELLO:
			g = new games.othello.Othello(this.in, this.out);
			break;

		case BLACKJACK:
			g = new games.blackjack.Game(this.in, this.out);
			break;

		case POKER:
			g = new games.poker.Game(this.in, this.out);
			break;

		case FAKE:
			g = new games.fake.Tester(this.in, this.out);
			break;

		// shouldn't ever happen because of use of enum
		default:
			this.out.println("INVALID GAME CHOICE. EXITING");
			this.closeConnections();
			break;
		}

		// play!
		g.play();

	}

	/**
	 * Close PrintWriter, Scanner, and Socket
	 */
	private void closeConnections() {
		try {
			this.out.close();
			this.in.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}