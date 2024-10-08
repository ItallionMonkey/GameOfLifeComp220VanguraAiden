import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameOfLife {
    private Board board;
    private BoardSequence boardSequence;

    // Main method to start the game
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        game.start();
    }

    // Start the game
    private void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename of the board (e.g., loaf.txt): ");
        String filename = scanner.nextLine();

        try {
            String fileContents = readFileContents(filename);
            board = new Board(fileContents);
            boardSequence = new BoardSequence(board);
            runGame(scanner);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating board: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Read the contents of the file
    public static String readFileContents(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    // Run the game loop
    private void runGame(Scanner scanner) {
        while (true) {
            System.out.println(board);
            System.out.print("Enter the number of steps to run (or 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int steps = Integer.parseInt(input);
                boardSequence.runMoreSteps(steps);
                System.out.println("Updated Board after " + steps + " steps:");
                System.out.println(boardSequence.toString());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}