package kalah;

import com.qualitascorpus.testsupport.IO;

//This class is responsible for printout the current state of the game
public class Output_game_state {
	public void printout(IO interface_io, int[] game_state) {
		// This section outputs the first line of the current state of the game
		interface_io.println("+----+-------+-------+-------+-------+-------+-------+----+");

		// This section outputs the second line of the current state of the game
		String secondline = "| P2 | ";
		int secondline_count = 12;
		for (int house_number = 6; house_number > 0; house_number--) {
			secondline += house_number + "[" + this.checkspace(game_state[secondline_count]) + "] | ";
			secondline_count -= 1;
		}
		secondline += this.checkspace(game_state[6]) + " |";
		interface_io.println(secondline);

		// This section outputs the third line of the current state of the game
		interface_io.println("|    |-------+-------+-------+-------+-------+-------|    |");

		// This section outputs the forth line of the current state of the game
		String forthline = "| " + this.checkspace(game_state[13]);
		for (int house_number = 1; house_number < 7; house_number++) {
			forthline += " | " + house_number + "[" + this.checkspace(game_state[house_number - 1]) + "]";
		}
		forthline += " | P1 |";
		interface_io.println(forthline);

		// This section outputs the fifth line of the current state of the game
		interface_io.println("+----+-------+-------+-------+-------+-------+-------+----+");
	}

	//Determine whether the current value needs to add a space to keep the print format
	public String checkspace(int value) {
		String checkvalue = "" + value;
		if (checkvalue.length() == 1) {
			checkvalue = " " + checkvalue;
		}
		return checkvalue;
	}
}
