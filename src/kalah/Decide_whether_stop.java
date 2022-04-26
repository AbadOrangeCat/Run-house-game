package kalah;

import com.qualitascorpus.testsupport.IO;

// This class is check whether the game need to stop
public class Decide_whether_stop {

	public Output_game_state print_game_state = new Output_game_state();

	// This function checks if the the player input q to end game
	public boolean check_input_stop(IO interface_io, int playserinput, int[] current_state) {
		if (playserinput == -1) {
			interface_io.println("Game over");
			print_game_state.printout(interface_io, current_state);
			return false;
		}
		return true;
	}

	// This function checks if the game has been won or lost
	public boolean stop_or_not(IO interface_io, int[] current_state, boolean current_player) {

		// Count all the point in houses of the player
		int player1_remaining = 0;
		for (int house_number = 0; house_number < 6; house_number++) {
			player1_remaining += current_state[house_number];
		}
		int player2_remaining = 0;
		for (int house_number = 7; house_number < 13; house_number++) {
			player2_remaining += current_state[house_number];
		}

		// Check to see if the game is end and check win or lost.
		if ((player1_remaining == 0 && current_player) || (player2_remaining == 0 && !current_player)) {

			print_game_state.printout(interface_io, current_state);
			interface_io.println("Game over");
			print_game_state.printout(interface_io, current_state);

			// Collect the score of the player
			int player1_score = current_state[6] + player1_remaining;
			int player2_score = current_state[13] + player2_remaining;

			// Print out player score and game result.
			interface_io.println("	player 1:" + player1_score);
			interface_io.println("	player 2:" + player2_score);
			if (player1_score == player2_score) {
				interface_io.println("A tie!");
			} else {
				interface_io.println("Player " + (current_state[6] > current_state[13] ? 1 : 2) + " wins!");
			}
			return false;
		}
		return true;
	}
}