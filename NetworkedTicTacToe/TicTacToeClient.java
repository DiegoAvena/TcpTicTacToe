import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/*
*  TCPTicTacToeClient
*  Runs the client side of TCPTicTacToe
*  Opens TCP socket connection to a host listening on port 6789
*  Upon connection, the tic toe game begins using the methods of the TicTacToe parent class.
*
*  Currently, client will be player two, and is the o character
*  When game ends, the connection closes
*
*  @author: Diego Avena
*  Email:  avena@chapman.edu
*  Date:  9/21/2019
*  @version: 3.0
*/
class TicTacToeClient extends TicTacToe {

  BufferedReader inFromUser = null;
  String sentencesToSend;
  DataOutputStream outToServer = null;
  BufferedReader inFromServer = null;

  Socket clientSocket = null;
  String serverMessages;

  public TicTacToeClient() {

    //Launch client side of TCPTicTacToe
    runClient();

  }

  //Attempts to make a TCP connection with the host
  private void AttemptToConnectToHost() {

    System.out.println("Attempting to connect to host.");

    //Try to open the clients socket:
    try {

      inFromUser = new BufferedReader(new InputStreamReader(System.in));
      clientSocket = new Socket("localHost", 6789); //If want to play with another machine that is running the host, be sure to change localHost to the IP address of that machine 

    } catch(Exception e) {

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

    }
    catch (Exception e) {

    }

  }

  //Overriden, runs the TicTacToe game for the client
  protected void runGame() {

    Scanner reader = new Scanner(System.in);
    gameJustStarted = true;
    System.out.println("Game started, current player is: "+currentPlayer);
    int wantedSlot = 0;

    while(CheckIfGameIsOver() == false) {

      switchPlayers();

      System.out.println("----------------------------------");
      printBoardToPlayer();

      if (currentPlayer.equals("o")) {

        //It is this player's turn:
        do {

          System.out.println("Enter slot number to fill (1 is slot 1, 2 is slot 2, etc): ");

          try {

            //Store the wanted slot number
            wantedSlot = reader.nextInt();

          } catch (Exception e) {


            //Player did not enter an integer
            reader.nextLine();

          }

        } while (CheckIfWantedSlotIsAvailableOrExists(wantedSlot) == false);

        numberOfSpotsFilled++; //Keep track of how many slots have been filled
        grid[wantedSlot - 1] = currentPlayer; //Fill the grid with the chosen slot

        try {

          outToServer.writeBytes(""+wantedSlot+'\n'); //Send the slot that needs to be filled to keep remote player grid in sync

        } catch (Exception e) {

          System.out.println("Failed to sync with player 2. Ending game.");
          System.exit(0);

        }

      }
      else {

        //It is remote player's turn:
        try {

          System.out.println("Player one is making a move...");
          String moveSecondPlayerMade = inFromServer.readLine();
          wantedSlot = Integer.parseInt(moveSecondPlayerMade); //Store the wanted slot number that the remote player wants

        }
        catch (Exception e){

          //Player 1 did not enter a valid slot number (this should never occur because the Host side handles this, but I left this to get rid of warnings)
          System.out.println("Player 1 entered invalid input.");
          System.exit(0);

        }

        numberOfSpotsFilled++; //Keep track of how many slots have been filled
        grid[wantedSlot - 1] = currentPlayer; //Fill the grid with the chosen slot

      }

      gameJustStarted = false;

    }

  }

  //Launches the TCPClient side of Tic tac toe
  private void runClient() {

    AttemptToConnectToHost(); //First make sure user is connected
    runGame(); //Run the game now

  }

}
