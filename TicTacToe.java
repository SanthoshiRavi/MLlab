 import java.util.*;

class Board {
    char board[][];
    int points;
    public Board(char board[][], int points) {
        this.board = board;
        this.points = points;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Board otherBoard = (Board) obj;
        return Arrays.deepEquals(board, otherBoard.board);
    }
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
public class TicTacToe {
    public static void printBoard(Board stateOfBoard) {
        for (int i = 0; i < stateOfBoard.board.length; i++) {
            for (int j = 0; j < stateOfBoard.board[i].length; j++) {
                System.out.print("| " + stateOfBoard.board[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("------------");
        }
    }
    public static Board playerMove(Board stateOfBoard) {
        Scanner s = new Scanner(System.in);
        int row, column;
        do {
            System.out.println("Enter your move row and column : ");
            row = s.nextInt();
            column = s.nextInt();
           
            if (isValidMove(stateOfBoard.board, row, column)) {
                break;
            } else {
                System.out.println("Enter a valid row and column");
            }
        } while (true);
        stateOfBoard.board[row][column] = 'x';
        return stateOfBoard;
    }
    public static boolean isValidMove(char[][] board, int row, int column) {
        return row >= 0 && row < 3 && column >= 0 && column < 3 && board[row][column] == ' ';
    }
    public static void computerMove(Board stateOfBoard) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (stateOfBoard.board[i][j] == ' ') {
                    stateOfBoard.board[i][j] = 'o';
                    int score = minimax(stateOfBoard, false);
                    stateOfBoard.board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        stateOfBoard.board[bestMove[0]][bestMove[1]] = 'o';
        System.out.println("Computer's move: (" + bestMove[0] + ", " + bestMove[1] + ")");
    }
    public static int minimax(Board stateOfBoard, boolean computerTurn) {
        char winner = checkIfGameOver(stateOfBoard);
        if (winner == 'x') {
            return -1;
        } else if (winner == 'o') {
            return 1;
        } else if (isBoardFull(stateOfBoard.board)) {
            return 0;
        }
        if (computerTurn) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (stateOfBoard.board[i][j] == ' ') {
                        stateOfBoard.board[i][j] = 'o';
                        bestScore = Math.max(bestScore, minimax(stateOfBoard, false));
                        stateOfBoard.board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (stateOfBoard.board[i][j] == ' ') {
                        stateOfBoard.board[i][j] = 'x';
                        bestScore = Math.min(bestScore, minimax(stateOfBoard,true));
                        stateOfBoard.board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        }
    }
    public static char checkIfGameOver(Board stateOfBoard) {
        for (int i = 0; i < 3; i++) {
            if (stateOfBoard.board[i][0] == stateOfBoard.board[i][1] && stateOfBoard.board[i][1] == stateOfBoard.board[i][2] && stateOfBoard.board[i][0] != ' ') {
                stateOfBoard.points += 10;
                return stateOfBoard.board[i][0];
            }
        }
        for (int j = 0; j < 3; j++) {
            if (stateOfBoard.board[0][j] == stateOfBoard.board[1][j] && stateOfBoard.board[1][j] == stateOfBoard.board[2][j] && stateOfBoard.board[0][j] != ' ') {
                stateOfBoard.points += 10;
                return stateOfBoard.board[0][j];
            }
        }
        if (stateOfBoard.board[0][0] == stateOfBoard.board[1][1] && stateOfBoard.board[1][1] == stateOfBoard.board[2][2] && stateOfBoard.board[0][0] != ' ') {
            stateOfBoard.points += 10;
            return stateOfBoard.board[0][0];
        }
        if (stateOfBoard.board[0][2] == stateOfBoard.board[1][1] && stateOfBoard.board[1][1] == stateOfBoard.board[2][0] && stateOfBoard.board[0][2] != ' ') {
            stateOfBoard.points += 10;
            return stateOfBoard.board[0][2];
        }
        boolean temp=isBoardFull(stateOfBoard.board);
        if(temp==true)
            return 'f';
        return ' ';
    }
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    public static char[][] copyBoard(char[][] board) {
        char[][] newBoard = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return newBoard;
    }
    public static void main(String[] args) {
        System.out.println("The initial board is :");
        //char initialBoard[][] = {{'x', 'o', 'x'},{'o', 'o', 'x'},{' ', ' ', ' '},};
        char initialBoard[][] = {{' ', ' ', ' '},{' ', ' ', ' '},{' ', ' ', ' '},};
        Board initialState = new Board(initialBoard, 0);
        Set<Board> allMoves = new HashSet<>();
        allMoves.add(initialState);
        printBoard(initialState);
        System.out.println("------------------------------");
        while (true) {
            Board newState = new Board(copyBoard(initialState.board), initialState.points);
            playerMove(newState);
            if (checkIfGameOver(newState) != ' ' && checkIfGameOver(newState) != 'f') {
                printBoard(newState);
                System.out.println("Congratulations! you are the Winner: " + checkIfGameOver(newState));
                break;
            }
            if(checkIfGameOver(newState) == 'f') {
                printBoard(newState);
                System.out.println("Its a TIE!!");
                break;
            }
            computerMove(newState);
            if (checkIfGameOver(newState) != ' ' && checkIfGameOver(newState) != 'f')
            {
                printBoard(newState);
                System.out.println("Game Over! The Winner is : " + checkIfGameOver(newState));
                break;
            }
            if(checkIfGameOver(newState) == 'f') {
                printBoard(newState);
                System.out.println("Its a TIE bruh!!");
                break;
            }
            printBoard(newState);
            if (allMoves.contains(newState)) {
                System.out.println("Game Over! It's a draw.");
                break;
            } else {
                allMoves.add(newState);
            }
            initialState = newState;
        }
    }
}
