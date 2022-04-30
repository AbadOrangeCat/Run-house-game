package kalah;

import com.qualitascorpus.testsupport.IO;

//This class is responsible for printout the current state of the game
public class Output_game_state {
	public IO interface_io;

	public void printout(int[] game_state, boolean vertical) {
		if (vertical) {
			printvertical(game_state);
		} else {
			printhorizontal(game_state);
		}
	}

	public void printvertical(int[] game_state) {
		interface_io.println("+---------------+");

		interface_io.println("|       | P2 " + this.checkspace(game_state[13]) + " |");

		interface_io.println("+-------+-------+");

		for (int house_number = 0; house_number < 6; house_number++) {
			String player_1_house = this.checkspace(game_state[house_number]);
			String player_2_house = this.checkspace(game_state[12 - house_number]);
			interface_io.println("| " + (house_number + 1) + "[" + player_1_house + "] | " + (6 - house_number) + "["
					+ player_2_house + "] |");
		}

		interface_io.println("+-------+-------+");
		interface_io.println("| P1 " + this.checkspace(game_state[6]) + " |       |");
		interface_io.println("+---------------+");

	}

	public void printhorizontal(int[] game_state) {

		interface_io.println("+----+-------+-------+-------+-------+-------+-------+----+");

		String secondline = "| P2 | ";
		int secondline_count = 12;
		for (int house_number = 6; house_number > 0; house_number--) {
			secondline += house_number + "[" + this.checkspace(game_state[secondline_count]) + "] | ";
			secondline_count -= 1;
		}
		secondline += this.checkspace(game_state[6]) + " |";
		interface_io.println(secondline);

		interface_io.println("|    |-------+-------+-------+-------+-------+-------|    |");

		String forthline = "| " + this.checkspace(game_state[13]);
		for (int house_number = 1; house_number < 7; house_number++) {
			forthline += " | " + house_number + "[" + this.checkspace(game_state[house_number - 1]) + "]";
		}
		forthline += " | P1 |";
		interface_io.println(forthline);

		interface_io.println("+----+-------+-------+-------+-------+-------+-------+----+");

	}

	// Determine value needs to add a space
	public String checkspace(int value) {
		String checkvalue = "" + value;
		if (checkvalue.length() == 1) {
			checkvalue = " " + checkvalue;
		}
		return checkvalue;
	}
}
