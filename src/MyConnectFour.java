/*
 * New functions of this game are:
 * 1. You can choose different color and corresponding order to place a counter at the start of each game
 * 2. You can compete with a competitive bot
 * 3. You can press 'q' to quit the game any time you like
 * 4. You can press 'r' to restart the game at the end of each game
 * 5. This game can not only have a winner but also can reach a draw
 * 6. Plenty of printed useful hints about game rules and the movement of bot and etc.
 */


public class MyConnectFour {
    /*
     * MyConnectFourClass's functions:
     * 1. Display necessary information about the game at the very start - init()
     * 2. Initialize the board object and bot object - init()
     * 3. Help player select a color and order to play - init()
     * 4. Set up a game loop and arrange the order of the game when colors have been chosen - playGame()
     */

    private static Board boardObject;
    private static Bot botObject;
    private static String playerColor;


    public static void main(String[] args) {
        init();
        playGame();
    }


    // a method to display necessary information about this game and set colors for player and bot
    public static void init() {
        boardObject = new Board();
        botObject = new Bot(boardObject.board);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Welcome to Connect 4");
        System.out.println("There are 2 players red and yellow");
        System.out.println("Red goes first, yellow goes later");
        System.out.println("To play the game type in the number of the column you want to drop your counter in");
        System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
        System.out.println("You can exit the game whenever you enter 'q'");
        System.out.println("-----------------------------------------------------------------------------------------");
        playerColor = boardObject.chooseColor();
        botObject.chooseColor(playerColor);
    }


    // a method to choose a order of the game and start the game loop
    public static void playGame() {

        boolean isRestart;

        // game loop - Player first
        while(playerColor.equals("r")) {
            boardObject.printBoard();  // display board
            boardObject.playerPlaceCounter();  // player places a disc
            isRestart = boardObject.checkEnd();  // check winner or draw
            if(isRestart) {
                init();
            }
            botObject.analyzeBoard();  // bot finds a position to place a disc
            botObject.botPlaceCounter();  // bot places a disc
            isRestart = boardObject.checkEnd();  // check winner or draw
            if(isRestart) {
                init();
            }
        }

        // game loop - Bot first
        while(playerColor.equals("y")) {
            botObject.analyzeBoard();
            botObject.botPlaceCounter();
            isRestart = boardObject.checkEnd();
            if(isRestart) {
                init();
            }
            boardObject.printBoard();
            boardObject.playerPlaceCounter();
            isRestart = boardObject.checkEnd();
            if(isRestart) {
                init();
            }
        }
    }

}
