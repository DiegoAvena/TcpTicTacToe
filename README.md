# TcpTicTacToe

![TcpTicTacToe](https://user-images.githubusercontent.com/43594702/119719304-7ae3a380-be1d-11eb-91d2-2f31706ac4db.png)

[See it in action!](https://www.youtube.com/watch?v=cR52JDZ7pR0)

A tic tac toe game that can be played with 1 other person given their IP address.

__Files__

*Client.java - Runs the client side of the Tic Tac Toe game

*TicTacToeClient.java - Contains the logic for the client side of the tic tac toe game

*TicTacToe.java - Contains the logic for a regular non networed tic tac toe game, running on the same console display

*Host.java - Runs the host side of the Tic Tac Toe game

*TicTacToeHost.java - Contains the logic for the host side of the tic tac toe game

__How to run__
Javac *.java in the directory with all of the source code files

_If playing on 1 machine_: 

1.) Do java Host to run the host side in 1 console display

2.) In a new console display, making sure to keep the console display from step 1 open and running the Host, do java Client.    This should allow you to connect, and now there are 2 console displays, 1 is the client and the other is the host, playing    the same instance of the TicTacToe game.

_If playing on 2 machines_: 

1.) Decide what machine will host, and launch the Host on that machine by opening up and console, going to the source code directory of this game, and typing java Host.

2.) On the other machine, go into the TicTacToeClient class, change the IP address the client socket is opened with to the IP address of the host machine (this is line 47, change clientSocket = new Socket("localHost", 6789) to clientSocket = new Socket("IPADDRESSOFHOSTMACHINE", 6789).

3.) Javac *.java in same directory to compile.

4.) java Client to run the client side of the tic tac toe game, the game will now be running on both machines, and can be played.

__Some ideas for improvements__
1.) Allow players to chat? 

2.) Allow a host to open multiple instances of the tic tac toe game, so that 1 machine can host many games! (Maybe use multithreading here?)

3.) Error handling improvements may also be needed...


