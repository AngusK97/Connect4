

public class Bot {
    /*
     * BotClass's functions:
     * 1. Automatically choose color once player has chosen one - chooseColor()
     * 2. Automatically analyze the board and find a best position to drop a disc - analyzeBoard()
     * 3. Automatically drop a disc to compete with player - botPlaceCounter()
     *
     * The design of Bot AI:
     * 1. Firstly, analyze the board and find all available positions to drop a disc - gradePositions()
     * 2. Grade all the available positions, according to the length & number of lines a position can form - gradePositions() & gradeTheNumber()
     * 3. Among the graded available positions, find a best position for defence purpose and attack purpose respectively- findBestPosition()
     * 4. Set the defence as priority and reach a final decision
     *    by comparing the scores between best defence position and best attack position - botPlaceCounter()
     */

    private String botColor;
    private String playerColor;
    private String[][] board;
    /*
     * Expatiation of scoreBoard:
     * scoreBoard is used to store the scores of every available position
     * 7 means there are at most 7 available positions on board to place disc
     * 2 means there are 2 types of colors: 0 represent botColor & 1 represent playerColor
     * Consider the position of highest scores of player color, bot gets the best defence position to disrupt a line of player
     * Consider the position of highest scores of bot color, bot gets the best attack position to form a line
     */
    private int[][] scoreBoard = new int[7][2];
    private int count = 0;
    private int score = 0;
    private int bestDefencePosition, bestAttackPosition, bestDefenceScore, bestAttackScore;


    public Bot(String[][] board) {
        this.board = board;
    }


    // a method to automatically choose color for bot once player has chosen one
    public void chooseColor(String player_color) {

        this.playerColor = player_color;
        if(playerColor.equals("r")) {
            botColor = "y";
            System.out.println("Bot chose yellow color, as the second player");
        }
        else if (playerColor.equals("y")) {
            botColor = "r";
            System.out.println("Bot chose red color, as the first player");
        }

    }


    // a platform method to figure out what is the best position to place a disc for bot
    public void analyzeBoard() {

        gradePositions(1, playerColor);  // grade all available positions
        gradePositions(0, botColor);  // grade all available positions
        bestDefencePosition = findBestPosition(1);
        bestAttackPosition = findBestPosition(0);
        initScoreBoard();

    }


    // a method to grade every direction for each available position on scoreBoard
    public void gradePositions(int colorNum, String color) {

        // find available positions on board
        for(int j=0; j<7; j++) {
            for(int i=0; i<6; i++){
                if(
                    ((i<5) && (board[i+1][j].equals(playerColor) || board[i+1][j].equals(botColor)) && (!board[i][j].equals(playerColor) && !board[i][j].equals(botColor)))
                    || ((i==5) && (!board[i][j].equals(playerColor) && !board[i][j].equals(botColor)))
                    ){

                    // count the number of the continuous color discs around each available position
                    // then transfer the number into scores and store them into scoreBoard

                    // analyze horizontal - right
                    for(int n=j; n<6; n++) {
                        if(!board[i][n+1].equals(color)) {
                            break;
                        }
                        else if(board[i][n+1].equals(color)) {
                            count += 1;
                        }
                    }
                    // analyze horizontal - left
                    for(int n=j; n>0; n--) {
                        if(!board[i][n-1].equals(color)) {
                            break;
                        }
                        else if(board[i][n-1].equals(color)) {
                            count += 1;
                        }
                    }
                    scoreBoard[j][colorNum] += gradeCount();
                    count = 0;

                    // analyze vertical - down
                    for(int n=i; n<5; n++) {
                        if(!board[n+1][j].equals(color)) {
                            break;
                        }
                        else if(board[n+1][j].equals(color)) {
                            count += 1;
                        }
                    }
                    scoreBoard[j][colorNum] += gradeCount();
                    count = 0;

                    // analyze main diagonal - left up
                    int m=j;
                    for(int n=i; n>0; n--) {
                        if(m > 0) {
                            if(!board[n-1][m-1].equals(color)) {
                                break;
                            }
                            else if(board[n-1][m-1].equals(color)){
                                count += 1;
                                m -= 1;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    // analyze main diagonal - right down
                    m=j;
                    for(int n=i; n<5; n++) {
                        if(m < 6) {
                            if(!board[n+1][m+1].equals(color)) {
                                break;
                            }
                            else if(board[n+1][m+1].equals(color)){
                                count += 1;
                                m += 1;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    scoreBoard[j][colorNum] += gradeCount();
                    count = 0;

                    // analyze back diagonal - right up
                    m=j;
                    for(int n=i; n>0; n--) {
                        if(m < 6) {
                            if(!board[n-1][m+1].equals(color)) {
                                break;
                            }
                            else if(board[n-1][m+1].equals(color)){
                                count += 1;
                                m += 1;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    // analyze back diagonal - left down
                    m=j;
                    for(int n=i; n<5; n++) {
                        if(m>0) {
                            if(!board[n+1][m-1].equals(color)) {
                                break;
                            }
                            else if(board[n+1][m-1].equals(color)){
                                count += 1;
                                m -= 1;
                            }
                        }
                    }
                    scoreBoard[j][colorNum] += gradeCount();
                    count = 0;

                }
            }
        }
    }


    // a method to grade the count
    public int gradeCount() {
        if(count >= 3) {
            score = 1000;
        }
        else if(count == 2) {
            score = 100;
        }
        else if(count == 1) {
            score = 10;
        }
        else if(count == 0) {
            score = 0;
        }
        return score;
    }


    // a method to sum all scores of all directions of a position
    // this method returns the position with highest score
    public int findBestPosition(int colorNum) {

        int bestPosition = 0;
        int maxScore = scoreBoard[0][colorNum];

        for(int j=0; j<7; j++) {

            if(scoreBoard[j][colorNum] > maxScore) {
                maxScore = scoreBoard[j][colorNum];
                bestPosition = j;
            }
        }

        if(colorNum == 1) {
            bestDefenceScore = maxScore;
        }
        else if(colorNum == 0) {
            bestAttackScore = maxScore;
        }
        return bestPosition;

    }


    // a method to initialize scoreBoard
    // every time Bot reach a final decision and place the disc, scoreBoard should be initialized for the next analysis
    public void initScoreBoard() {
        for(int i=0; i<7; i++) {
            for(int j=0; j<2; j++) {
                scoreBoard[i][j] = 0;
            }
        }
    }


    // a method for bot to place disc on board according to best attack position and best defence position
    // the defence is always the priority
    public void botPlaceCounter() {

        int finalDecision;
        boolean hasInput = false;
        int displayNum;

        // set the priority is defence
        if(bestDefenceScore >= bestAttackScore) {
            finalDecision = bestDefencePosition;
        }
        else {
            finalDecision = bestAttackPosition;
        }

        // broadcast the final decision of bot
        displayNum = finalDecision + 1;
        System.out.print("The bot goes ");
        System.out.print(displayNum+"\n");

        // place bot's disc into board
        while(!hasInput) {
            for (int i = 0; i < 6; i++) {
                if (i == 0 && !board[i][finalDecision].equals("0")) {}
                else if (!board[i][finalDecision].equals("0") && board[i - 1][finalDecision].equals("0")) {
                    board[i - 1][finalDecision] = botColor;
                    hasInput = true;
                } else if(i==5 && board[5][finalDecision].equals("0")){
                    board[5][finalDecision] = botColor;
                    hasInput = true;
                }
            }
        }
    }

}
