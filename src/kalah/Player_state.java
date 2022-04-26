package kalah;

//This class is responsible for setting the initial state of the game and recording the state in the game.
public class Player_state {
	// The current state of the game.
	int[] current_state;

	// List position 0 to 5, is player house, position 6 is player score pool.
	public void number_of_score_in_each_house(int number) {
		this.current_state = new int[] { number, number, number, number, number, number, 0 };
	}

	public int[] get_current_state() {
		return this.current_state;
	}
}
