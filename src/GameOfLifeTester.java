import java.io.IOException;
import java.util.Scanner;

public class GameOfLifeTester {
    public static final String[] boardFiles = {"loaf.txt", "toad.txt", "pentadecathlon.txt", "glider.txt", "diehard.txt"};

    public static void main(String[] args) throws IOException {
        System.out.println("Enter a test name: either 'main' or 'readFileContents'");
        Scanner in = new Scanner(System.in);
        String methodName = in.nextLine();

        System.out.println("Testing GameOfLife method: " + methodName);

        if (methodName.equals("main")) {
            GameOfLife.main(args);
        }
        else if (methodName.equals("readFileContents")) {
            // Run tests for each board in boardFiles
            for (int boardIndex=0; boardIndex < boardFiles.length; boardIndex++) {
                String s = GameOfLife.readFileContents(boardFiles[boardIndex]);
                System.out.println("File: " + boardFiles[boardIndex]);
                System.out.println("File length: " + s.length());
                System.out.print(s);
            }
        }
        else {
            System.err.println("Unrecognized method name: " + methodName);
        }

        in.close();
    }
}
