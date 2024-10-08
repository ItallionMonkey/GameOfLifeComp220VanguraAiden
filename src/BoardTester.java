import java.util.Scanner;

/**
 * Code for testing the Board class in the Game of Life COMP 220 project
 * @author Britton Wolfe
 */
public class BoardTester {
    public static final int[] boardRunTimes = { 3, 3, 16, 16, 30 };

    public static final String[] boardNames = {"Loaf", "Toad", "Pentadecathlon", "Glider", "Diehard"};

    public static final String[] boardStrings = {
            "Loaf\n" +
                    "6\n" +
                    "7\n" +
                    "       \n" +
                    "  XX   \n" +
                    " X  X  \n" +
                    "  X X  \n" +
                    "   X   \n" +
                    "       \n",

            "Toad_period_2\n" +
                    "6\n" +
                    "6\n" +
                    "      \n" +
                    "      \n" +
                    "  XXX \n" +
                    " XXX  \n" +
                    "      \n" +
                    "      \n",

            "Penta_period_15\n" +
                    "11\n" +
                    "18\n" +
                    "                  \n" +
                    "                  \n" +
                    "                  \n" +
                    "                  \n" +
                    "      X    X      \n" +
                    "    XX XXXX XX    \n" +
                    "      X    X      \n" +
                    "                  \n" +
                    "                  \n" +
                    "                  \n" +
                    "                  \n",

            "Glider\n" +
                    "7\n" +
                    "8\n" +
                    "        \n" +
                    "  X     \n" +
                    "   X    \n" +
                    " XXX    \n" +
                    "        \n" +
                    "        \n" +
                    "        \n",

            "Diehard_dies_130\n" +
                    "15\n" +
                    "20\n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "            X       \n" +
                    "      XX            \n" +
                    "       X   XXX      \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n" +
                    "                    \n"
    };



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a test name: search this file for 'methodName' to see the options");
        String methodName = in.nextLine();

        System.out.println("Testing Board method: " + methodName);

        if (methodName.equals("size_ctor")) {
            // This is the only test where we don't use board strings
            boolean failedCase = false;
            for (int numRows = 1; numRows < 5; numRows++) {
                for (int numCols=1; numCols < 5; numCols++) {
                    Board b = new Board(numRows, numCols);
                    if (b.getNumRows() != numRows || b.getNumCols() != numCols) {
                        System.out.println("Failed for numRows = " + numRows + ", numCols = " + numCols);
                        failedCase = true;
                    }
                }
            }
            if (!failedCase) {
                System.out.println("Passed");
            }
        }
        else{
            // Run tests for each board in boardStrings
            for (int boardIndex=0; boardIndex<boardStrings.length; boardIndex++) {
                Board board = new Board(boardStrings[boardIndex]);

                System.out.println("Board: " + boardNames[boardIndex]);
                if (methodName.equals("string_ctor")) {
                    final int nRows = board.getNumRows();
                    final int nCols = board.getNumCols();
                    System.out.println("Rows: " + nRows);
                    System.out.println("Columns: " + nCols);
                    // Print out each of the cells, based on getCell
                    for (int r=0; r<nRows; r++) {
                        for (int c=0; c<nCols; c++) {
                            if (board.getCell(r,c)) {
                                System.out.print('T');
                            }
                            else {
                                System.out.print('`');
                            }
                        }
                        System.out.print('\n');
                    }

                    // Test failure conditions: out of bounds
                    int exceptionCount = 0;
                    try {
                        board.getCell(-1, 0);
                    } catch (IllegalArgumentException iae) {
                        exceptionCount++;
                    }
                    try {
                        board.getCell(0, -1);
                    } catch (IllegalArgumentException iae) {
                        exceptionCount++;
                    }
                    try {
                        board.getCell(nRows, 0);
                    } catch (IllegalArgumentException iae) {
                        exceptionCount++;
                    }
                    try {
                        board.getCell(0, nCols);
                    } catch (IllegalArgumentException iae) {
                        exceptionCount++;
                    }
                    System.out.println("Generated " + exceptionCount + " out of 4 exceptions.");
                }
                else if (methodName.equals("toString")) {
                    // Print out the board using toString
                    System.out.print(board.toString());
                }
                else if (methodName.equals("nextBoard")) {
                    // Print out several successive boards
                    System.out.println("Starting board:");
                    System.out.print(board.toString());
                    Board current = board;
                    for (int stepNum=1; stepNum <= Math.min(boardRunTimes[boardIndex], 6); stepNum++) {
                        current = current.nextBoard();
                        System.out.println("After " + stepNum + " steps:");
                        System.out.print(current.toString());
                    }
                }
                else if (methodName.equals("isSame")) {
                    Board differentBoard;
                    if(boardIndex == 0){
                        differentBoard = new Board("NotLoaf\n" +
                                "6\n" +
                                "7\n" +
                                "       \n" +
                                "  XX   \n" +
                                " X  X  \n" +
                                "  X X  \n" +
                                "   X X \n" +
                                "       \n");
                    }
                    else {
                        differentBoard = new Board(boardStrings[boardIndex-1]);
                    }

                    System.out.println("Should be false...");
                    System.out.println(differentBoard.isSame(board));
                    System.out.println(board.isSame(differentBoard));

                    Board copy = new Board(boardStrings[boardIndex]);
                    System.out.println("Should be true...");
                    System.out.println(board.isSame(board));
                    System.out.println(board.isSame(copy));
                    System.out.println(copy.isSame(board));

                    Board next = board.nextBoard();
                    Board nextNext = next.nextBoard();
                    System.out.println("Results depend on the particular board...");
                    System.out.println(board.isSame(next));
                    System.out.println(next.isSame(board));
                    System.out.println(board.isSame(nextNext));
                    System.out.println(nextNext.isSame(board));

                }
                else {
                    System.err.println("Unrecognized method to test: " + methodName);
                }

                System.out.print('\n');
            }
        }

        in.close();
    }
}
