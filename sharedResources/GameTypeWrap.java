package sharedResources;

/**
 * Simple Wrapper for GameType enum
 * 
 * @author Chae Jubb
 * @version 1.0
 * 
 */
public class GameTypeWrap {

	private GameType value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *           Initial value
	 */
	public GameTypeWrap(GameType value) {
		this.value = value;
	}

	/**
	 * Getter for value being wrapped
	 * 
	 * @return GameType value being wrapped
	 */
	public GameType getValue() {
		return this.value;
	}

	/**
	 * Setter method for value being wrapped
	 * 
	 * @param value
	 *           GameType value being set
	 */
	public void setValue(GameType value) {
		this.value = value;
	}
}