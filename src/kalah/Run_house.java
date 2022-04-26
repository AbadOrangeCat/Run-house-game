package kalah;

import com.qualitascorpus.testsupport.IO;

//This class is responsible for updating the results of the game state under the current player input
public class Run_house {
	public boolean current_player; // Current round player, true = player 1, false = player 2
	public boolean game_not_finish;
	
	public int[] run_house_game(IO interface_io, int[] current_state, int playerinput) {
		// Save the position of the last house of the current step to determine whether to switch players
		int current_input_final_state = (playerinput+1) + current_state[playerinput];
		
		// Gets the number of points in the player's chosen house and clean the house
		int run_house_time = current_state[playerinput];
		current_state[playerinput] = 0;

		// Update game status
		int current_run_position = 1;
		for (int time = 0; time < run_house_time; time++) {
			// Skip the opposing player's score pool
			current_run_position += jump_opponent_score_pool(playerinput + current_run_position);

			// When the current running position exceeds the maximum position, the position returns to 0
			if (playerinput + current_run_position > 13) {
				playerinput = 0;
				current_run_position = 0;
			}
			// Updates the current location of the house
			current_state[playerinput + current_run_position] += 1;
			current_run_position += 1;
		}
		// Check if the current position was ever 0, and if so, score the position and the opponent's position
		current_state = check_last_zero_get_all(current_state, playerinput + current_run_position - 1);
		
		// Use the position of the last house to determine whether to switch players, and update players state.
		current_player = this.playerupdate(current_input_final_state, current_player);
		
		// Check to see if the game is won or lost
		Decide_whether_stop chech_stop = new Decide_whether_stop();
		game_not_finish = chech_stop.stop_or_not(interface_io, current_state, current_player);
		
		return current_state;
	}

	// Check if the current position was ever 0, and if so, score the position and the opponent's position
	public int[] check_last_zero_get_all(int[] current_state, int current_postion) {
		if (current_postion != 6 && current_postion != 13 && current_state[current_postion] == 1) {
			int new_score = current_state[12 - current_postion];
			if (current_player && current_postion < 6 && current_state[12 - current_postion] != 0) {
				current_state[6] += new_score + 1;
				current_state[12 - current_postion] = 0;
				current_state[current_postion] = 0;
			}
			if (!current_player && current_postion > 7 && current_state[12 - current_postion] != 0) {
				current_state[13] += new_score + 1;
				current_state[12 - current_postion] = 0;
				current_state[current_postion] = 0;
			}
		}
		return current_state;
	}

	// Skip the opposing player's score pool
	public int jump_opponent_score_pool(int current_postion) {
		if (current_player && current_postion == 13) {
			return 1;
		}
		if (!current_player && current_postion == 6) {
			return 1;
		}
		return 0;
	}
	
	// Use the position of the last house to determine whether to switch players
	public boolean playerupdate(int current_input_final_state, boolean current_player) {
		if (!current_player && current_input_final_state != 14) {
			return !current_player;
		}
		if (current_player && current_input_final_state != 7) {
			return !current_player;
		}
		return current_player;
	}
}
