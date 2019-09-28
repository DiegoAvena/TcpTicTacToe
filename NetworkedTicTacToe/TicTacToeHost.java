/*

Implements the host side of the TCPTicTacToe
Creates a server socket that listens for incomming connection requests on port 6789
Opens connection socket when connection request detected.
Starts TicTacToe game with this remote player, with this player going first
This player's character is the x
When game ends, connection socket closes

@author: Diego Avena
Email:  avena@chapman.edu
Date:  9/21/2019
@version: 3.0

*/

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

  //Listen for incomming client connection request
  private void AttemptToConnectToClient() {

    //Open up server socket
    try {
      welcomeSocket = new ServerSocket(6789);
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }

    //listen for connection requests
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

  //Overrides run from base class to run the networked version of the tic tac toe game
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

          try {

            wantedSlot = reader.nextInt();

          } catch (Exception e) {

            //System.out.println("Sorry, you must enter a number of the slot you wish to occupy");
            reader.nextLine();
            continue;

          }

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

  //Runs the host side of the networked tic tac toe game
  private void runHost() {

    AttemptToConnectToClient();
    runGame();

  }

}
