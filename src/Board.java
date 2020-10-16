
import java.util.Scanner;

public class Board {
    /*
     * BoardClass's functions:
     * 1. Display a board - printBoard()
     * 2. Help player to choose a color - chooseColor()
     * 3. Help player to drop a disc - playerPlaceCounter()
     * 4. Check a draw or a winner - checkEnd()
     * 5. Help player to restart or quit the game at the end of each round - restartOrExit()
     */

    Scanner scan = new Scanner(System.in);
    private String player_color;
    // initialize the game board
    public String[][] board =
            {{"0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0"},
            {"0","0","0","0","0","0","0"}};


    public Board() {

    }


    // a method to help player choose a color and order
    public String chooseColor() {

        System.out.println("Please enter 'r' or 'y' to choose your color...");
        player_color = scan.nextLine();

        if(player_color.equals("q")) {
            System.out.println("You have exited the game.");
            System.exit(0);
        }

        while(!player_color.equals("r") && !player_color.equals("y")) {
            System.out.println("Your enter is invalid. Please enter 'r' or 'y' to choose your color...");
            player_color = scan.nextLine();
        }

        if(player_color.equals("r")) {
            System.out.println("You have successfully chosen red, so you are the first player.");
            return player_color;
        }
        else {
            System.out.println("You have successfully chosen yellow, so you are the second player.");
            return player_color;
        }

    }


    // a method to display the game board
    public void printBoard() {
        System.out.println("_____________________________");
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                if(board[i][j].equals("r")) {
                    System.out.print("| R ");
                 }
                else if(board[i][j].equals("y")) {
                    System.out.print("| Y ");
                 }
                else if(board[i][j].equals("0")){
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
        System.out.println("￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
        System.out.println("  1   2   3   4   5   6   7");

    }


    // a method to help player place a disc on the board
    public void playerPlaceCounter(){

        String column_input;
        int column_num;
        boolean hasInput = false;

        System.out.println("Please input a column number from 1 to 7 to drop a disc...");
        column_input = scan.next();

        if(column_input.equals("q")) {
            System.out.println("You have exited the game.");
            System.exit(0);
        }

        while(!column_input.equals("1") && !column_input.equals("2") && !column_input.equals("3") && !column_input.equals("4") && !column_input.equals("5") && !column_input.equals("6") && !column_input.equals("7")) {
            System.out.println("Your enter is invalid. Please input a column number from 1 to 7 to drop a disc...");
            column_input = scan.next();
        }

        column_num = Integer.parseInt(column_input) - 1;
        // judge whether a column is full
        while(!board[0][column_num].equals("0")) {
            System.out.println("This line is full, please choose another column...");
            column_input = scan.next();
            column_num = Integer.parseInt(column_input) - 1;
        }
        // place player's disc
        while(!hasInput) {
            for(int i=0; i<6; i++) {
                if(i==0 && !board[i][column_num].equals("0")) {
                    System.out.println("This line is full");
                }
                else if(!board[i][column_num].equals("0") && board[i-1][column_num].equals("0")) {
                    board[i-1][column_num] = player_color;
                    hasInput = true;
                }
                else if(i==5 && board[5][column_num].equals("0")){
                    board[5][column_num] = player_color;
                    hasInput = true;
                }
            }
        }

    }


    // a method to check the board whether there are a sequence of four same-color discs
    public boolean checkEnd() {

        // check draw
        int fullColumn=0;
        for(int j=0; j<7; j++) {
            if(!board[0][j].equals("0")) {
                fullColumn ++;
            }
        }
        if(fullColumn == 7) {
            System.out.println("----This game reaches a draw!----");
            System.out.println("---------\\(^o^)/\\(^o^)/---------");
            return restartOrExit();
        }


        // check winner - check horizontal
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<=board[i].length-4; j++) {
                if(board[i][j].equals("r")) {
                    if(board[i][j].equals(board[i][j + 1]) && board[i][j + 1].equals(board[i][j + 2]) && board[i][j + 2].equals(board[i][j + 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color RED!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
                else if(board[i][j].equals("y")) {
                    if(board[i][j].equals(board[i][j + 1]) && board[i][j + 1].equals(board[i][j + 2]) && board[i][j + 2].equals(board[i][j + 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color YELLOW!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
            }
        }

        // check winner - vertical
        for(int j=0; j<board[0].length; j++) {
            for(int i=0; i<=board.length-4; i++) {
                if(board[i][j].equals("r")) {
                    if(board[i][j].equals(board[i + 1][j]) && board[i + 1][j].equals(board[i + 2][j]) && board[i + 2][j].equals(board[i + 3][j])) {
                        this.printBoard();
                        System.out.println("----The winner is color RED!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
                else if(board[i][j].equals("y")) {
                    if(board[i][j].equals(board[i + 1][j]) && board[i + 1][j].equals(board[i + 2][j]) && board[i + 2][j].equals(board[i + 3][j])) {
                        this.printBoard();
                        System.out.println("----The winner is color YELLOW!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
            }
        }

        // check winner - main diagonal
        for(int i=0; i<=board.length-4; i++){
            for(int j=0; j<=board[i].length-4; j++){
                if(board[i][j].equals("r")) {
                    if(board[i][j].equals(board[i + 1][j + 1]) && board[i + 1][j + 1].equals(board[i + 2][j + 2]) && board[i + 2][j + 2].equals(board[i + 3][j + 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color RED!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
                else if(board[i][j].equals("y")) {
                    if(board[i][j].equals(board[i + 1][j + 1]) && board[i + 1][j + 1].equals(board[i + 2][j + 2]) && board[i + 2][j + 2].equals(board[i + 3][j + 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color YELLOW!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
            }
        }

        // check winner - back diagonal
        for(int i=0; i<=board.length-4; i++){
            for(int j=3; j<board[i].length; j++){
                if(board[i][j].equals("r")) {
                    if(board[i][j].equals(board[i + 1][j - 1]) && board[i + 1][j - 1].equals(board[i + 2][j - 2]) && board[i + 2][j - 2].equals(board[i + 3][j - 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color RED!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
                else if(board[i][j].equals("y")) {
                    if(board[i][j].equals(board[i + 1][j - 1]) && board[i + 1][j - 1].equals(board[i + 2][j - 2]) && board[i + 2][j - 2].equals(board[i + 3][j - 3])) {
                        this.printBoard();
                        System.out.println("----The winner is color YELLOW!----");
                        System.out.println("---------\\(^o^)/\\(^o^)/---------");
                        return restartOrExit();
                    }
                }
            }
        }

        return false;

    }


    // a method to help player to restart or quit the game at the end
    public boolean restartOrExit(){
        String restart;

        System.out.println("\nThis game is over. Now you can enter 'r' to restart or 'q' to exit...");
        restart = scan.next();
        while(!restart.equals("r") && !restart.equals("q") ) {
            System.out.println("Your enter is invalid. This round is over. Now you can enter 'r' to restart or 'q' to exit...");
            restart = scan.next();
        }
        if(restart.equals("q")) {
            System.out.println("You have exited the game.");
            System.exit(0);
        }

        return true;

    }



}