package kalah;

import java.util.Arrays;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation.
 * When running this program, players need to give input from 1 to 6 to select a house.
 * The player can input q to quit the game.
 * Have fun!
 */

public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO interface_io) {
		// Initialize the class
		Output_game_state print_game_state = new Output_game_state(); // Print and output current game state
		Decide_whether_stop chech_stop = new Decide_whether_stop(); // Determine if the current game is over
		Run_house run_house = new Run_house(); // Play the game step and update game state

		// Initialize two players, the number of players in the initial house can be customized.
		Player_state player1 = new Player_state();
		player1.number_of_score_in_each_house(4);
		int[] player1_state = player1.get_current_state();

		Player_state player2 = new Player_state();
		player2.number_of_score_in_each_house(4);
		int[] player2_state = player2.get_current_state();
		
		int[] current_state = connect_state(player1_state, player2_state); // Connect 2 players game state
		boolean current_player = true; // Current round player, true = player 1, false = player 2

		// Output initial game state and initialize the game state
		print_game_state.printout(interface_io, current_state);
		boolean game_not_finish = true;

		// Start game
		while (game_not_finish) {
			/*-----------------------------------------------------------------------------
			This section provides input and takes input from the player to complete the game.
			The player will have to re-select the house if it is empty
			If player input is q, end the game.
			-----------------------------------------------------------------------------*/

			// Get player input, input range is 1-6. Input q will get -1.
			int playerinput = interface_io.readInteger(
					"Player P" + (current_player ? 1 : 2) + "'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q");

			// If the current player is 2, the input needs to be increased by 7 to match player 2's house position
			if (playerinput != -1 && !current_player) {
				playerinput += 7;
			}
			// Check whether the input corresponds to the house is 0. If it's 0, jump to next loop.
			if (playerinput != -1 && current_state[playerinput - 1] == 0) {
				interface_io.println("House is empty. Move again.");
				print_game_state.printout(interface_io, current_state);
				continue;
			}
			// Determine if the player wants to stop playing (input is q)
			game_not_finish = chech_stop.check_input_stop(interface_io, playerinput, current_state);

			/*-----------------------------------------------------------------------------
			This section is responsible for running the game and switching players
			-----------------------------------------------------------------------------*/
			run_house.game_not_finish = game_not_finish;
			if (game_not_finish) {
				// Update housing information and game state
				run_house.current_player = current_player;
				current_state = run_house.run_house_game(interface_io, current_state, playerinput - 1);
				current_player = run_house.current_player;
			}
			// Output the updated game state
			game_not_finish = run_house.game_not_finish;
			if (game_not_finish) {
				print_game_state.printout(interface_io, current_state);
			}
			
			// Update player state
			player1_state = Arrays.copyOfRange(current_state,0,6);
			player2_state = Arrays.copyOfRange(current_state,7,13);
		}
	}
	// Connect 2 players state for easier program.
	public int[] connect_state(int[] state1, int[] state2) {
		int state1_length = state1.length;
		int state2_length = state2.length;
		int[] current_player = new int[state1_length + state2_length];
		System.arraycopy(state1, 0, current_player, 0, state1_length);
		System.arraycopy(state2, 0, current_player, state1_length, state2_length);
		return current_player;
	}
}