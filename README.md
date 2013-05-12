#README for Game Server
Chae Jubb // ecj2122

##A Brief Description
This software is used to allow users to remote play games located on a central server.  This idea is implemented in a way that allows multiple users to play simultaneously.  An interface was written to standardize the gameplay in terms of interaction with the server.  Finally, a light GUI system was used for user interface with the server output.

##To use this software:
**Server**: ./$java server.ServerMain

**Client**: ./$java client.ClientMain \<server IP\> \<server Port\>

Note: Default port is 4444

First choose game from list in Pop-up.  Then play game according to instructions in large text area.

The rules and specific instructions for each game are included in the README file in the games/\<name of game\> folder.

##Notes on Client
*Required Packages*: client, sharedResources
The text from the server appears in the large text box at the top of the interface.
The user sends responses to the server by typing the in the small text box at the bottom of the screen and pressing the submit button or the enter key.

##Notes on Server
*Required Packages*: server, sharedResources, games.*
The server is hardcoded to quit after 50 connections.  A connection is counted as every time 

1. The client successfully connects to the server at start-up; or
2. Each time time a game is subsequently chosen from the Java pop-up

All computations are done on the server.  Only the text output is sent to the client.

##Notes on COMS1007 Techniques Used
1. Interfaces:  A Game Interface was created in order to standardize the server's interaction with the game.  The interface requires a void play method that takes no arguments.
Additionally, the constructor would ideally take arguments of a Scanner and PrintWriter, which are used to recieve and send information from the server, respectively (though this could not be specified by the interface).

2. Design Pattenrs: I implemented the Facade pattern in my design of the UX.  All three of the GUI classes interact with the user through the Client class, which also,on a broad level, controls all the interaction with the Server.

3. Java Graphics: I have, on the client side, multiple implementations of a GUI.  One GUI exists to allow the user to choose which game he or she would like to play.  The second facilitates the playing of the game.  The third allows the user options in playing subsequent games.  These implementations, though, are light.

4. Networking: Clearly, with the Client-Server design, this project uses Java Networking.  The client is merely a remote interface through which the user is able to play games, which are computed on the server.

5. Multithreading:  This aspect has been used on both the server and client sides of the project.  On the server side, each connection is implemented as a separate thread.  This allows multiple simultaneous connections.  The client side uses a dedicated thread to read the InputStream from the server and print it to the text area.  Another main thread is used for all other client-side execution.