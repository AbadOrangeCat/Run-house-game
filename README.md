# Run-house-game
A small, two-player **Kalah** game you can play in the terminal (a text window where you type commands).
It is written in **Java** and prints the game board after every move.

Kalah is a classic “stone moving” board game from the Mancala family.  
Each player chooses a house, spreads the stones one by one, and tries to collect the most stones in their store.

---

## What this project gives you
This repository is meant to be easy to run and easy to review.

It includes:
- A complete, playable Kalah game (human vs human on one computer)
- A clear text board display (so you always see the current state)
- A simple build script (`Makefile`) so you can compile, play, and run tests with short commands
- Test resources used by the provided course test runner (`resources/`)

Now that you know what is here, the next section shows the fastest way to run the game.

---

## Quick start
If you only want to play once, follow these steps. They are written for beginners.

### 1) Install Java
Java is the language this game is written in.  
To run Java programs, you need the **JDK** (Java Development Kit). That is the standard “toolbox” for Java.

After installing, you should be able to run this command in your terminal:
```bash
java -version
```

### 2) Download this project
Download the repository to your computer (as a ZIP) and unzip it, or clone it with Git if you already use Git.

You should end up with a folder that contains `src/`, `resources/`, and `Makefile`.

### 3) Start the game
Open a terminal in the project folder and run:
```bash
make play
```

If everything is set up, you will see the board printed and a prompt asking Player P1 to pick a house.

> If you see “make: command not found”, it just means your computer does not have `make`.  
> Jump to the “Troubleshooting” section for alternatives.

---

## How to play
The game runs fully in text. You do not need a mouse.

Before the rules, it helps to learn the words the game uses.

### Game words you will see
- **House**: a small pit that holds stones. You choose a house by its number (1 to 6).
- **Store**: the bigger “scoring pit”. Stones in your store are your points.
- **Stone**: the pieces being moved. On screen, they are just numbers.

With these words in mind, the next part explains the board you see on screen.

### Reading the board on screen
A typical starting board looks like this:
```text
+----+-------+-------+-------+-------+-------+-------+----+
| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] | 0 |
|    |-------+-------+-------+-------+-------+-------|    |
| 0  | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |
+----+-------+-------+-------+-------+-------+-------+----+
```

How to read each house:
- The number **before** the brackets is the house number.
- The number **inside** the brackets is how many stones are in that house.
  - Example: `3[ 4]` means **house 3 has 4 stones**.

Where the stores are:
- The **top-right** number is **Player P1’s store**.
- The **bottom-left** number is **Player P2’s store**.

This layout is just a way to draw the board in text.  
When it is your turn, you still pick houses **on your own row** (P1 bottom row, P2 top row).

### What you type
On your turn the program asks:
```text
Player P1's turn - Specify house number or 'q' to quit:
```

You can type:
- `1` to `6` to choose a house
- `q` to quit immediately

If you choose an empty house, the game will tell you and ask again:
```text
House is empty.
Move again.
```

### What happens in a move
This implementation follows the standard Kalah style rules.

In simple words:
1. You pick one of your houses (1 to 6).
2. The game takes all stones from that house.
3. It drops them **one by one** into the next houses as it moves around the board.
4. If the move passes your opponent’s store, it **skips** it.

Two special rules can also happen:
- **Extra turn**: if your last stone lands in your own store, you play again immediately.
- **Capture**: if your last stone lands in an empty house on your side, and the opposite house has stones, those stones are captured into your store.

### When the game ends
The game ends in two situations:
- A player types `q` to quit, or
- A player starts a turn and has **no stones in any of their houses**

At the end, the game prints “Game over”, shows the final board, and prints the scores.

---

## Project layout
If you are reviewing the code, this section explains where the important parts live.

### Source code
All Java source files are under `src/kalah/`:

- `Kalah.java`  
  The entry point. It runs the main game loop:
  - prints the board
  - asks for input
  - rejects empty-house moves
  - calls the move engine to apply a turn

- `Run_house.java`  
  The move engine. It updates the board state after a player picks a house.
  This is where “spreading stones”, wrapping around the board, skipping the opponent store,
  capture checks, and turn switching happen.

- `Output_game_state.java`  
  The board printer. It formats the current state into the ASCII board you see.

- `Decide_whether_stop.java`  
  The end-of-game checker. It handles quitting and also detects when the game is finished.

- `Player_state.java`  
  Creates the starting state (how many stones each house begins with).

This separation keeps the game easier to read: printing, move rules, and stopping rules are not mixed together.

### Resources and test materials
The `resources/` folder contains files used for testing:

- `junit-3.8.2.jar`  
  JUnit is a popular Java testing tool. It runs automated checks.

- `kalah20200717.jar`  
  A course-provided support library. It includes the `IO` interface and helpers such as `MockIO`,
  so tests can feed input and capture output in a controlled way.

- `test_specifications/`  
  Example input/output scripts used by the test runner. They show expected board changes for cases like:
  - wrapping around the board
  - extra turns
  - capture situations
  - quitting

### Automation
There is a GitHub Actions workflow in `.github/workflows/testkalah.yml`.

It is set to run the tests when you push to the **`submission`** branch.  
In plain words: GitHub can automatically compile and test your code on a clean machine.

---

## Commands you can use
These commands come from the `Makefile`.

### Build (compile)
```bash
make compile
```

### Play
```bash
make play
```

### Run automated tests
```bash
make tests
```

---

## Troubleshooting
A few common problems and what they mean.

### “java: command not found”
Java is not installed, or it is not on your PATH.  
Install the JDK, then reopen your terminal and try `java -version` again.

### “make: command not found”
`make` is a small tool that runs the commands inside `Makefile`.

Options:
- On macOS: install Xcode Command Line Tools
- On Windows: use WSL (Windows Subsystem for Linux) or Git Bash
- Or run Java commands directly (see below)

### Run without `make`
If you prefer not to use `make`, you can compile and run using Java directly:

```bash
mkdir -p bin
javac -d bin -cp resources/junit-3.8.2.jar:resources/kalah20200717.jar:bin:src src/kalah/Kalah.java
java -cp resources/junit-3.8.2.jar:resources/kalah20200717.jar:bin kalah.Kalah
```

> Note for Windows users: Java classpaths usually use `;` instead of `:`.

---

## Notes
- This game is text-based on purpose. It is designed for easy testing and clear output.
- There is no computer AI player. It is meant for two humans taking turns.

