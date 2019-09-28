/*

Author: Diego Avena
Name: TicTacToe

Contains the logic to run a tic tac toe game on the same machine.
Players alternate turns using the same console display.

*/


import java.util.Scanner;
import java.io.BufferedReader;

//Basic tic tac toe, does not take care of invalid user input yet...
class TicTacToe {

  protected String[] grid; //the tic tac toe grid

  private String playerOne;
  private String playerTwo;

  protected int numberOfSpotsFilled; //keeps track of how many spots have been used on the grid

  private String winningCharacter; //the winning player

  protected String currentPlayer; //keeps track of who the current player is

  protected boolean gameJustStarted; //a flag for when the game has just game began

  public void setPlayerOne(String playerOne) {

    this.playerOne = new String(playerOne);

  }

  public void setPlayerTwo(String playerTwo) {

    this.playerTwo = new String(playerTwo);

  }

  //Switches players to allow for turns 
  void switchPlayers() {

    if (gameJustStarted == false) {

      if (currentPlayer.equals(playerOne)) {

        currentPlayer = playerTwo;
        System.out.println("Player two's turn:");

      }
      else {

        currentPlayer = playerOne;
        System.out.println("Player one's turn:");

      }

    }

  }

  //Runs the game
  protected void runGame() {

    Scanner reader = new Scanner(System.in);
    gameJustStarted = true;
    System.out.println("Game started, current player is: "+currentPlayer);

    while(CheckIfGameIsOver() == false) {

      switchPlayers();

      int wantedSlot;
      do {

        printBoardToPlayer();
        System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");
        wantedSlot = reader.nextInt();

      } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

      numberOfSpotsFilled++;
      grid[wantedSlot - 1] = currentPlayer;

      gameJustStarted = false;
      System.out.println("----------------------------------");

    }

  }

  //Checks if the slot the player wants to take is not already in use or is in the grid
  protected boolean CheckIfWantedSlotIsAvailableOrExists(int slot) {

    if ((slot > 9) || (slot == 0)) {

      System.out.println("Slot entered does not exist...");
      return false;

    }

    if ((grid[slot - 1].equals("x")) || (grid[slot - 1].equals("o"))) {

      System.out.println("Slot entered is in use...");
      return false;

    }

    return true;

  }

  //Checks if someone won and stores who won if this is the case
  private boolean CheckIfSomeoneOne() {

    if (((grid[0] + grid[1] + grid[2]).equals("xxx")) || ((grid[0] + grid[1] + grid[2]).equals("ooo"))) {

      //Checked row 1, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[3] + grid[4] + grid[5]).equals("xxx")) || ((grid[3] + grid[4] + grid[5]).equals("ooo"))) {

      //checked row 2, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[6] + grid[7] + grid[8]).equals("xxx")) || ((grid[6] + grid[7] + grid[8]).equals("ooo"))) {

      //checked row 3, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[0] + grid[3] + grid[6]).equals("xxx")) || ((grid[0] + grid[3] + grid[6]).equals("ooo"))) {

      //Check column 1, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[1] + grid[4] + grid[7]).equals("xxx")) || ((grid[1] + grid[4] + grid[7]).equals("ooo"))) {

      //Check column 2, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[2] + grid[5] + grid[8]).equals("xxx")) || ((grid[2] + grid[5] + grid[8]).equals("ooo"))) {

      //Check column 3, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[0] + grid[4] + grid[8]).equals("xxx")) || ((grid[0] + grid[4] + grid[8]).equals("ooo"))) {

      //checked left to right diagonal, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[2] + grid[4] + grid[6]).equals("xxx")) || ((grid[2] + grid[4] + grid[6]).equals("ooo"))) {

      //checked right to left diagonal, someone has won
      winningCharacter = currentPlayer;
      return true;

    }

    return false;

  }

  //Checks if game is over if a player won or the game is a tie
  protected boolean CheckIfGameIsOver() {

    boolean someoneWon = false;
    printBoardToPlayer();

    //Check for possible winner:
    if (CheckIfSomeoneOne()) {

      System.out.println("Winning player is: "+winningCharacter);
      return true;

    }
    if (numberOfSpotsFilled > 8) {

      System.out.println("No one won, tie.");
      return true;

    }

    return false;

  }

  //Constructor, initializes things
  public TicTacToe() {

    setPlayerOne("x");
    setPlayerTwo("o");

    numberOfSpotsFilled = 0;

    currentPlayer = playerOne;

    grid = new String[] {"~", "~", "~",
                      "~", "~", "~",
                      "~", "~", "~"};

  }

  //Display the board to the player
  protected void printBoardToPlayer() {

    for (int i = 0; i < 9; i++) {

      System.out.print("|");

      if (((i + 1) % 3) == 0) {

        System.out.print(grid[i] + "|");
        System.out.println();
        System.out.println("-------");

      }
      else {

        System.out.print(grid[i]);

      }

    }

  }

}
