package kalah;

import com.qualitascorpus.testsupport.IO;

// Let the robot find best move
public class Botmove {
	public int[] current_state;
	public IO interface_io;
	
	public int findbestmove(int[] state, IO io) {
		current_state = state;
		interface_io = io;
		int choise = 0;
		choise = checkextra();
		if (choise == 0) {
			choise = checkcapture();
		}
		if (choise == 0) {
			choise = checklegal();
		}
		return choise;
	}

	public int checkextra() {
		int result = 0;
		for (int house_num = 1; house_num < 7; house_num++) {
			int correct_house = house_num + 6;
			if (current_state[correct_house] == 7 - house_num) {
				result = house_num;
				interface_io.println(
						"Player P2 (Robot) chooses house #" + house_num + " because it leads to an extra move");
				break;
			}
		}
		return result;
	}

	public int checkcapture() {
		int result = 0;
		for (int house_num = 1; house_num < 7; house_num++) {
			int correct_house = house_num + 6;
			if (check_last_position_zero(correct_house) && current_state[correct_house] != 0) {
				result = house_num;
				interface_io
						.println("Player P2 (Robot) chooses house #" + house_num + " because it leads to a capture");
				break;
			}
		}
		return result;
	}

	public int checklegal() {
		int result = 0;
		for (int house_num = 1; house_num < 7; house_num++) {
			int correct_house = house_num + 6;
			if (current_state[correct_house] != 0) {
				result = house_num;
				interface_io.println(
						"Player P2 (Robot) chooses house #" + house_num + " because it is the first legal move");
				break;
			}
		}
		return result;
	}

	public boolean check_last_position_zero(int correct_house) {
		int save_correct_house = correct_house;
		int run_house_time = current_state[correct_house];
		Run_house run_house = new Run_house();;
		int[] result = run_house.passed_house_addone(run_house_time, correct_house, false);
		int current_run_position = result[0];
		correct_house = result[1];
		
		int last_position = correct_house + current_run_position - 1;
		if (current_state[last_position] == 0 && last_position > 6 && run_house_time < 13) {
			if ((save_correct_house + run_house_time - 13) > 6) {
				return true;
			}
			if (current_state[12 - last_position] != 0) {
				return true;
			}
		}
		return false;
	}
}
