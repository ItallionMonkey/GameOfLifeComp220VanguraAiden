import java.util.Scanner;

public class BoardSequenceTester {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a test name: search this file for 'methodName' to see the options");
        String methodName = in.nextLine();

        System.out.println("Testing BoardSequence method: " + methodName);

        // Run tests for each board in boardStrings
        for (int boardIndex=0; boardIndex < BoardTester.boardStrings.length; boardIndex++) {
            Board board = new Board(BoardTester.boardStrings[boardIndex]);
            BoardSequence seq = new BoardSequence(board);

            System.out.println("Board: " + BoardTester.boardNames[boardIndex]);
            if (methodName.equals("ctor")) {
                Board result = seq.boardAt(0);
                System.out.print(result);

                int exceptionCount = 0;
                try {
                    seq.boardAt(1);
                } catch (IllegalArgumentException iae) {
                    exceptionCount++;
                }
                try {
                    seq.boardAt(-1);
                } catch (IllegalArgumentException iae) {
                    exceptionCount++;
                }
                System.out.println("Generated " + exceptionCount + " out of 2 exceptions.");
            }
            else if (methodName.equals("runMoreSteps")) {
                final int totalSteps = BoardTester.boardRunTimes[boardIndex];
                seq.runMoreSteps(2);
                seq.runMoreSteps(totalSteps - 2);
                for (int stepNum=1; stepNum <= totalSteps; stepNum++) {
                    System.out.println("After step " + stepNum + ":");
                    System.out.println(seq.boardAt(stepNum));
                }

                int exceptionCount = 0;
                try {
                    seq.boardAt(totalSteps+1);
                } catch (IllegalArgumentException iae) {
                    exceptionCount++;
                }
                try {
                    seq.boardAt(-1);
                } catch (IllegalArgumentException iae) {
                    exceptionCount++;
                }
                System.out.println("Generated " + exceptionCount + " out of 2 exceptions.");
            }
            else if (methodName.equals("toString")) {
                System.out.println("Sequence with no steps:");
                System.out.println(seq.toString());

                final int totalSteps = BoardTester.boardRunTimes[boardIndex];
                System.out.println("Sequence with " + totalSteps + " steps:");
                seq.runMoreSteps(totalSteps);
                System.out.println(seq.toString());
            }
            else if (methodName.equals("findCycle")) {
                System.out.println(seq.findCycle());

                final int totalSteps = BoardTester.boardRunTimes[boardIndex];
                seq.runMoreSteps(2);
                System.out.println(seq.findCycle());

                seq.runMoreSteps(totalSteps-2);
                System.out.println(seq.findCycle());
            }
            else {
                System.err.println("Unrecognized method name: " + methodName);
            }
        }

        in.close();
    }
}


