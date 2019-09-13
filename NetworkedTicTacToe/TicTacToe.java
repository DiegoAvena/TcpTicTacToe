import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

//Basic tic tac toe, does not take care of invalid user input yet...
class TicTacToe {

  protected String[] grid; //the tic tac toe grid

  private String playerOne;
  private String playerTwo;

  protected int numberOfSpotsFilled;

  private String winningCharacter;

  protected String currentPlayer;

  protected boolean gameJustStarted;
  boolean gameIsOver;

  //boolean thisUserIsTheHost; //determines if this code should run in host mode or client mode

  public void setPlayerOne(String playerOne) {

    this.playerOne = new String(playerOne);

  }

  public void setPlayerTwo(String playerTwo) {

    this.playerTwo = new String(playerTwo);

  }

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

  //IDEA: For networking, just run 2 seperate instances of this class, making the host player 1 and the client player 2,
  //and just share whose turn it is over the TCP connection?
  private void AttemptToConnectToClient() {

    /*try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    while (true) {

      try {

        connectionSocket = welcomeSocket.accept(); //Waits until a client connects to this server on the given port
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); //Get the input the client has sent over
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());

      }
      catch (Exception e) {

      }
      //connectionSocket = welcomeSocket.accept(); //Waits until a client connects to this server on the given port
      //inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); //Get the input the client has sent over
      //outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      break;

    } */



    /*while (true) {

      connectionSocket = welcomeSocket.accept(); //Waits until a client connects to this server on the given port
      inFromClient = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream())); //Get the input the client has sent over
      outToClient = new DataOutputStream(connectionSocket.getOutputStream());

      //clientSentence = inFromClient.readLine();
      //System.out.println(clientSentence);
      //capitalizedSentence = clientSentence.toUpperCase() + '\n';
      //System.out.println(capitalizedSentence);
      //outToClient.writeBytes(capitalizedSentence);

      //connectionSocket.close();
    } */

  }

  //Runs the game
  protected void runGame() {

    //String playerTwoResponse;

    //AttemptToConnectToClient(); //First connect to a client

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

      /*if (currentPlayer.equals(playerOne)) {

        int wantedSlot;
        do {

          printBoardToPlayer();
          System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");
          wantedSlot = reader.nextInt();

        } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

        numberOfSpotsFilled++;
        grid[wantedSlot - 1] = currentPlayer;

      }*/
      /*else {

        try {

          //System.out.println("Telling client it is their turn");
          //outToClient.writeBytes("y");

        }
        catch (Exception e) {

        }

        //UpdateClientWithCurrentGameInfo();

      }*/

      /*int wantedSlot;
      do {

        printBoardToPlayer();
        System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");
        wantedSlot = reader.nextInt();

      } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

      numberOfSpotsFilled++;
      grid[wantedSlot - 1] = currentPlayer;*/



      gameJustStarted = false;
      System.out.println("----------------------------------");

    }

    /*try {

      connectionSocket.close();

    }
    catch (Exception e) {

    } */

  }

  protected boolean CheckIfWantedSlotIsAvailableOrExists(int slot) {

    if ((slot > 9) || (slot == 0)) {

      System.out.println("Slot entered does not exist...");
      return false;

    }

    if ((grid[slot - 1].equals("x")) || (grid[slot - 1].equals("y"))) {

      System.out.println("Slot entered is in use...");
      return false;

    }

    return true;

  }

  private boolean CheckIfSomeoneOne() {

    if (((grid[0] + grid[1] + grid[2]).equals("xxx")) || ((grid[0] + grid[1] + grid[2]).equals("yyy"))) {

      //Checked row 1, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[3] + grid[4] + grid[5]).equals("xxx")) || ((grid[3] + grid[4] + grid[5]).equals("yyy"))) {

      //checked row 2, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[6] + grid[7] + grid[8]).equals("xxx")) || ((grid[6] + grid[7] + grid[8]).equals("yyy"))) {

      //checked row 3, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[0] + grid[3] + grid[6]).equals("xxx")) || ((grid[0] + grid[3] + grid[6]).equals("yyy"))) {

      //Check column 1, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[1] + grid[4] + grid[7]).equals("xxx")) || ((grid[1] + grid[4] + grid[7]).equals("yyy"))) {

      //Check column 2, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[2] + grid[5] + grid[8]).equals("xxx")) || ((grid[2] + grid[5] + grid[8]).equals("yyy"))) {

      //Check column 3, someone won:
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[0] + grid[4] + grid[8]).equals("xxx")) || ((grid[0] + grid[4] + grid[8]).equals("yyy"))) {

      //checked left to right diagonal, someone has won
      winningCharacter = currentPlayer;
      return true;

    }
    else if (((grid[2] + grid[4] + grid[6]).equals("xxx")) || ((grid[2] + grid[4] + grid[6]).equals("yyy"))) {

      //checked right to left diagonal, someone has won
      winningCharacter = currentPlayer;
      return true;

    }

    return false;

  }

  protected boolean CheckIfGameIsOver() {

    boolean someoneWon = false;
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

  public TicTacToe() {

    setPlayerOne("x");
    setPlayerTwo("y");

    numberOfSpotsFilled = 0;

    currentPlayer = playerOne;

    grid = new String[] {" ", " ", " ",
                      " ", " ", " ",
                      " ", " ", " "};

  }

  public void printBoardToPlayer() {

    for (int i = 0; i < 9; i++) {


      if (((i + 1) % 3) == 0) {

        //Start new line:
        //System.out.print(grid[i] + "|");
        //System.out.println("---------");
        System.out.print(grid[i]);
        System.out.println();

      }
      else {

        System.out.print(grid[i]);

      }

    }

  }

}
