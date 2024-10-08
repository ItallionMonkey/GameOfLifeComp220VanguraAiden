import java.util.ArrayList;
import java.util.List;

public class BoardSequence {
    private List<Board> boards;
    private Board currentBoard;

    // Constructor
    public BoardSequence(Board initialBoard) {
        boards = new ArrayList<>();
        boards.add(initialBoard);
        currentBoard = initialBoard;
    }

    // Get the board at a specific step
    public Board boardAt(int index) {
        if (index < 0 || index >= boards.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return boards.get(index);
    }

    // Run more steps and add the resulting boards to the sequence
    public void runMoreSteps(int steps) {
        for (int i = 0; i < steps; i++) {
            currentBoard = currentBoard.nextBoard();
            boards.add(currentBoard);
        }
    }

    // Convert the sequence to a string representation
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Board Sequence:\n");
        for (int i = 0; i < boards.size(); i++) {
            sb.append("Step ").append(i).append(":\n").append(boards.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    // Find cycles in the board sequence
    public String findCycle() {
        for (int i = 0; i < boards.size(); i++) {
            for (int j = i + 1; j < boards.size(); j++) {
                if (boards.get(i).isSame(boards.get(j))) {
                    return "Cycle detected between steps " + i + " and " + j;
                }
            }
        }
        return "No cycle detected.";
    }
    public int size() {
        return boards.size();
    }

}
