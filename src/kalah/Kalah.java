package kalah;

import java.util.Arrays;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation. When running
 * this program, players need to give input from 1 to 6 to select a house. The
 * player can input q to quit the game. Have fun!
 */

public class Kalah {
	public Output_game_state print_game_state = new Output_game_state();
	public Run_house run_house = new Run_house();
	public Decide_whether_stop chech_stop = new Decide_whether_stop();
	public Botmove bot_best_move = new Botmove();;
	public IO interface_io;
	public boolean printformat;
	public int[] current_state;

	public static void main(String[] args) {
		new Kalah().play(new MockIO(), false, false);
	}

	public void play(IO io, boolean vertical, boolean bmf) {
		// Initialize the class
		interface_io = io;
		printformat = vertical;
		print_game_state.interface_io = io;

		// The number of the initial house point can be customized.
		Player_state player1 = new Player_state();
		player1.number_of_score_in_each_house(4);
		int[] player1_state = player1.get_current_state();

		Player_state player2 = new Player_state();
		player2.number_of_score_in_each_house(4);
		int[] player2_state = player2.get_current_state();

		connect_state(player1_state, player2_state);
		boolean current_player = true; // Current round player, true = player 1, false = player 2
		boolean game_not_finish = true;

		print_game_state.printout(current_state, printformat);

		while (game_not_finish) {
			int playerinput;

			if (bmf && !current_player) {
				playerinput = bot_best_move.findbestmove(current_state, interface_io);
			} else {
				playerinput = interface_io.readInteger(
						"Player P" + (current_player ? 1 : 2) + "'s turn - Specify house number or 'q' to quit: ", 1, 6,
						-1, "q"); // input q, playerinput = -1
			}

			// Player 2 house needs to be increased by 7 to match house position
			if (playerinput != -1 && !current_player) {
				playerinput += 7;
			}

			if (playerinput != -1 && current_state[playerinput - 1] == 0) {
				interface_io.println("House is empty. Move again.");
				print_game_state.printout(current_state, printformat);
				continue;
			}

			// Determine if the player wants to stop playing (input is q)
			game_not_finish = chech_stop.check_input_stop(interface_io, playerinput, current_state, printformat);

			run_house.game_not_finish = game_not_finish;
			if (game_not_finish) {
				run_house.current_player = current_player;
				current_state = run_house.run_house_game(interface_io, current_state, playerinput - 1, printformat);
				current_player = run_house.current_player;
			}

			game_not_finish = run_house.game_not_finish;
			if (game_not_finish) {
				print_game_state.printout(current_state, vertical);
			}

			player1_state = Arrays.copyOfRange(current_state, 0, 6);
			player2_state = Arrays.copyOfRange(current_state, 7, 13);
		}
	}

	// Connect 2 players state for easier program.
	public void connect_state(int[] state1, int[] state2) {
		int state1_length = state1.length;
		int state2_length = state2.length;
		int[] current_player = new int[state1_length + state2_length];
		System.arraycopy(state1, 0, current_player, 0, state1_length);
		System.arraycopy(state2, 0, current_player, state1_length, state2_length);
		current_state = current_player;
	}
}