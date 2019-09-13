import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TicTacToeHost extends TicTacToe {

  private ServerSocket welcomeSocket;
  private Socket connectionSocket;
  private BufferedReader inFromClient;
  private DataOutputStream  outToClient;
  private String clientSentence;

  byte itIsThisPlayersTurnFlag; //0 means it is not this players turn, 1 means it is this players turn
  int gridSecondPlayerWishesToOccupy;

  public TicTacToeHost() {

    runHost();

  }
  private void AttemptToConnectToClient() {

    //Open up servers socket
    try {
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

        System.out.println("Connected!");

        clientSentence = inFromClient.readLine();
        System.out.println(clientSentence);

        outToClient.writeBytes("Player one says hello!" + '\n');

      }
      catch (Exception e) {

      }

      break;

    }
  }

  protected void runGame() {

    Scanner reader = new Scanner(System.in);
    gameJustStarted = true;
    System.out.println("Game started, current player is: "+currentPlayer);
    int wantedSlot = 0;

    while(CheckIfGameIsOver() == false) {

      switchPlayers();

      System.out.println("----------------------------------");
      printBoardToPlayer();

      if (currentPlayer.equals("x")) {

        //It is this player's turn:
        do {

          //printBoardToPlayer();
          System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");
          wantedSlot = reader.nextInt();

        } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

        numberOfSpotsFilled++;
        grid[wantedSlot - 1] = currentPlayer;

        try {

          outToClient.writeBytes(""+wantedSlot+'\n'); //Send the slot that needs to be filled to keep remote player grid in sync

        }
        catch (Exception e) {

          System.out.println("Failed to sync with player 2. Ending game.");
          System.exit(0);

        }

      }
      else {

        //It is remote player's turn:
        try {

          System.out.println("Player two is making a move...");
          String moveSecondPlayerMade = inFromClient.readLine();
          wantedSlot = Integer.parseInt(moveSecondPlayerMade);

        }
        catch (Exception e){

          System.out.println("Player 2 entered invalid input.");
          System.exit(0);

        }

        numberOfSpotsFilled++;
        grid[wantedSlot - 1] = currentPlayer;

      }

      gameJustStarted = false;

    }

  }

  private void runHost() {

    AttemptToConnectToClient();
    runGame();

  }


}
