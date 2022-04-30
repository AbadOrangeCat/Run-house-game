package kalah;

import com.qualitascorpus.testsupport.IO;

//This class is responsible for updating the results of the game state under the current player input
public class Run_house {
	public boolean current_player; // Current round player, true = player 1, false = player 2
	public boolean game_not_finish;
	public int[] current_state;

	public int[] run_house_game(IO interface_io, int[] state, int playerinput, boolean printformat) {
		current_state = state;

		int current_input_final_state = (playerinput + 1) + current_state[playerinput];

		int run_house_time = current_state[playerinput];
		current_state[playerinput] = 0;

		int[] result = passed_house_addone(run_house_time, playerinput, true);
		int current_run_position = result[0];
		playerinput = result[1];

		current_state = check_last_zero_get_all(playerinput + current_run_position - 1);

		current_player = this.playerupdate(current_input_final_state);

		Decide_whether_stop chech_stop = new Decide_whether_stop();
		game_not_finish = chech_stop.stop_or_not(interface_io, current_state, current_player, printformat);

		return current_state;
	}

	public int[] passed_house_addone(int run_house_time, int playerinput, boolean changestate) {
		int[] result = new int[] { 0, 0 };

		int current_run_position = 1;
		for (int time = 0; time < run_house_time; time++) {

			current_run_position += jump_opponent_score_pool(playerinput + current_run_position);
			// position 13 is the last value in current_state
			if (playerinput + current_run_position > 13) {
				playerinput = 0;
				current_run_position = 0;
			}
			if (changestate) {
				current_state[playerinput + current_run_position] += 1;
			}
			current_run_position += 1;
		}
		result[0] = current_run_position;
		result[1] = playerinput;

		return result;
	}

	public int[] check_last_zero_get_all(int current_postion) {

		if (current_postion != 6 && current_postion != 13 && current_state[current_postion] == 1) {
			// Position 13 stories player 2 score, position 6 stores player1 score
			int new_score = current_state[12 - current_postion];

			if (current_player && current_postion < 6 && current_state[12 - current_postion] != 0) {
				current_state[6] += new_score + 1;
				current_state[12 - current_postion] = 0;
				current_state[current_postion] = 0;
			}
			if (!current_player && current_postion >= 7 && current_state[12 - current_postion] != 0) {
				current_state[13] += new_score + 1;
				current_state[12 - current_postion] = 0;
				current_state[current_postion] = 0;
			}
		}
		return current_state;
	}

	public int jump_opponent_score_pool(int current_postion) {
		if (current_player && current_postion == 13) {
			return 1;
		}
		if (!current_player && current_postion == 6) {
			return 1;
		}
		return 0;
	}

	public boolean playerupdate(int current_input_final_state) {
		if (!current_player && current_input_final_state != 14) {
			return !current_player;
		}
		if (current_player && current_input_final_state != 7) {
			return !current_player;
		}
		return current_player;
	}
}
