package server;

/**
 * Interface for games that server can play. Ideally (but not necesarily), the
 * constructor would take an argument for a Scanner and PrintWriter, which are
 * used to read and send messages to or from the server.
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public interface Game {

	/**
	 * This method starts the game. After the constructor is called, this is the
	 * only method called, and so must contain all code necessary for playing the
	 * game
	 */
	public void play();

}