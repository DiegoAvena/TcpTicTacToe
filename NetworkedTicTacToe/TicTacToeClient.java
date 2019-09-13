import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

class TicTacToeClient extends TicTacToe {

  BufferedReader inFromUser = null;
  String sentencesToSend;
  DataOutputStream outToServer = null;
  BufferedReader inFromServer = null;

  Socket clientSocket = null;
  String serverMessages;

  public TicTacToeClient() {

    //System.out.println("Client initialized!");
    runClient();

  }

  private void AttemptToConnectToHost() {

    System.out.println("Attempting to connect to host.");

    //Try to open the clients socket:
    try {

      inFromUser = new BufferedReader(new InputStreamReader(System.in));
      clientSocket = new Socket("localHost", 6789);

    }catch(Exception e) {

      System.out.println("Failed to open socket connection");
      System.exit(0);

    }

    //Set up input and output streams for this clients socket
    try {

      outToServer = new DataOutputStream(clientSocket.getOutputStream());
      outToServer.writeBytes("Player two says Hello!" + '\n');
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      serverMessages = inFromServer.readLine();
      System.out.println(serverMessages);

      /*while (true) {

        serverMessages = inFromServer.readLine();
        System.out.println(serverMessages);
        break;

      }*/

    }
    catch (Exception e) {

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

      if (currentPlayer.equals("y")) {

        //It is this player's turn:
        do {

          System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");
          wantedSlot = reader.nextInt();

        } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

        numberOfSpotsFilled++;
        grid[wantedSlot - 1] = currentPlayer;

        try {

          outToServer.writeBytes(""+wantedSlot+'\n'); //Send the slot that needs to be filled to keep remote player grid in sync

        }
        catch (Exception e) {

          System.out.println("Failed to sync with player 2. Ending game.");
          System.exit(0);

        }

      }
      else {

        //It is remote player's turn:
        try {

          System.out.println("Player one is making a move...");
          String moveSecondPlayerMade = inFromServer.readLine();
          wantedSlot = Integer.parseInt(moveSecondPlayerMade);

        }
        catch (Exception e){

          System.out.println("Player 1 entered invalid input.");
          System.exit(0);

        }

        numberOfSpotsFilled++;
        grid[wantedSlot - 1] = currentPlayer;

      }

      gameJustStarted = false;

    }

  }

  private void runClient() {

    AttemptToConnectToHost();
    runGame();

  }

}
