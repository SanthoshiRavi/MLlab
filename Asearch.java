import java.util.*;

class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] state; //A 2D array representing the puzzle's current state.
    int g; // The cost to reach this state, calculated as the depth of the node.
    int h; // The heuristic value, calculated as the number of misplaced tiles.
    PuzzleNode parent; //A reference to the parent node in the path.

    public PuzzleNode(int[][] state, int g, int h, PuzzleNode parent) {
        this.state = state; //The initial state of the puzzle.
        this.g = g; // The goal state to reach.
        this.h = h;
        this.parent = parent;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.g + this.h, other.g + other.h);
    }
}

public class Asearch {
    static int[][] initial = {{1, 2, 3}, {0, 4, 6}, {7, 5, 8}}; // Initial state
    static int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; // Goal state

    static int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Possible moves: down, up, right, left

    public static void main(String[] args) {
        PriorityQueue<PuzzleNode> openList = new PriorityQueue<>();
        Set<String> closedList = new HashSet<>();

        PuzzleNode initialNode = new PuzzleNode(initial, 0, calculateH(initial), null); 
        openList.offer(initialNode); //An initial PuzzleNode is created and added to the openList.

        while (!openList.isEmpty()) { //The main loop continues until the openList is empty.
            //In each iteration, the node with the lowest g+h is polled from the queue.
            PuzzleNode currentNode = openList.poll();
            closedList.add(Arrays.deepToString(currentNode.state));

            if (Arrays.deepEquals(currentNode.state, goal)) {
                System.out.println("Goal state reached!");  
                printSolutionPath(currentNode); //Prints the path from the initial state to the goal state.
                return;
            }

            int[] blankPos = findBlankPosition(currentNode.state); //Finds the position of the blank tile (0).

            for (int[] move : moves) { //for - each loop: This loop iterates over the moves array, which represents all possible moves that the blank tile can make. In the context of an 8-puzzle, these moves are typically up, down, left, and right.
                int newX = blankPos[0] + move[0];
                int newY = blankPos[1] + move[1];
                //blankPos is an array with two elements, representing the current row 
                //(blankPos[0]) and column (blankPos[1]) of the blank tile in the puzzle.
                //move[0] and move[1] are the row and column changes defined by the current move. 
                //For example, a move up could be represented as {-1, 0} (row -1, column unchanged), 
                //and a move right could be {0, 1} (row unchanged, column +1).

                if (isValidMove(newX, newY)) {
                    int[][] newState = new int[3][3];
                    copyState(currentNode.state, newState); //Copies the puzzle state from one array to another.
                    swap(newState, blankPos[0], blankPos[1], newX, newY); //Swaps two tiles in the puzzle

                    String newStateString = Arrays.deepToString(newState);
                    if (!closedList.contains(newStateString)) {
                        int newG = currentNode.g + 1;
                        int newH = calculateH(newState); //Calculates the heuristic value (h).
                        PuzzleNode successor = new PuzzleNode(newState, newG, newH, currentNode);
                        openList.offer(successor);
                    }
                }
            }
        }

        System.out.println("No solution found!");
    }

    static int[] findBlankPosition(int[][] state) {
        int[] position = new int[2];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    static void copyState(int[][] src, int[][] dest) {
        for (int i = 0; i < src.length; i++) { //The method uses a for loop to iterate through each row of the src array.
            System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
            //src[i]: Specifies the source array (row) to be copied.
            //0: The starting position in the source array (from the beginning of the row).
            //dest[i]: Specifies the destination array (row) where the contents are to be copied.
            //0: The starting position in the destination array (from the beginning of the row).
            //src[i].length: The number of elements to be copied (the entire length of the row).
        }
    }

    static void swap(int[][] state, int x1, int y1, int x2, int y2) { //Swaps two tiles in the puzzle.
        int temp = state[x1][y1];
        state[x1][y1] = state[x2][y2];
        state[x2][y2] = temp;
    }

    static int calculateH(int[][] state) { //The heuristic used here is based on the number of misplaced tiles in the current state compared to the goal state
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                //state[i][j] != goal[i][j]: This checks if the current tile 
                //in the state does not match the corresponding tile in the goal state.
                //state[i][j] != 0: This ensures that the blank tile (represented by 0) 
                //is not counted as a misplaced tile.
                if (state[i][j] != goal[i][j] && state[i][j] != 0) {
                    h++;
                }
            }
        }
        return h;
    }

    static void printSolutionPath(PuzzleNode node) {
        List<PuzzleNode> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);

        for (PuzzleNode n : path) {
            printState(n.state);
            System.out.println();
        }
    }

    static void printState(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}
