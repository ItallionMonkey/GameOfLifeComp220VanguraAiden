import java.util.Arrays;

public class Board {
    private boolean[][] cells;
    private String name;

    // Constructor by size
    public Board(int numberOfRows, int numberOfColumns) {
        cells = new boolean[numberOfRows][numberOfColumns];
        this.name = "Unnamed Board"; // Default name
    }

    public Board(String boardInfo) {
        String[] lines = boardInfo.split("\n");
        this.name = lines[0].trim(); // Extract the name from the first line

        int rows;
        int cols;

        // Parse number of rows and columns
        try {
            rows = Integer.parseInt(lines[1].trim());
            cols = Integer.parseInt(lines[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid row or column count.");
        }

        cells = new boolean[rows][cols];
        int lineIndex = 3; // Start reading from the fourth line
        int currentRow = 0;

        while (currentRow < rows) {
            // Skip empty lines
            while (lineIndex < lines.length && lines[lineIndex].trim().isEmpty()) {
                lineIndex++;
            }

            // If we still have lines left to read
            if (lineIndex < lines.length) {
                String line = lines[lineIndex];

                // Ensure the line has the correct length
                if (line.length() < cols) {
                    // Pad the line with spaces if it has fewer columns than expected
                    line = String.format("%-" + cols + "s", line); // Left pad with spaces
                } else if (line.length() > cols) {
                    throw new IllegalArgumentException("Row " + (currentRow + 1) + " does not match specified column count. Expected: " + cols + ", Found: " + line.length());
                }

                // Populate the cells array
                for (int j = 0; j < cols; j++) {
                    cells[currentRow][j] = line.charAt(j) == 'X'; // 'X' means alive
                }

                currentRow++; // Move to the next row
                lineIndex++; // Move to the next line
            } else {
                // If we run out of lines, fill the remaining rows with dead cells
                for (int j = 0; j < cols; j++) {
                    cells[currentRow][j] = false; // All dead cells
                }
                currentRow++; // Increment the current row
            }
        }
    }

    // Get number of rows
    public int getNumRows() {
        return cells.length;
    }

    // Get number of columns
    public int getNumCols() {
        return cells[0].length;
    }

    // Get the state of a specific cell
    public boolean getCell(int row, int col) {
        if (row < 0 || row >= getNumRows() || col < 0 || col >= getNumCols()) {
            throw new IllegalArgumentException("Row or column out of bounds.");
        }
        return cells[row][col];
    }

    // Generate the next board state
    public Board nextBoard() {
        int rows = getNumRows();
        int cols = getNumCols();
        boolean[][] newCells = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                newCells[i][j] = applyRules(cells[i][j], liveNeighbors);
            }
        }
        return new Board(newCells);
    }

    // Count live neighbors of a cell
    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r >= 0 && r < getNumRows() && c >= 0 && c < getNumCols() && !(r == row && c == col)) {
                    if (cells[r][c]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Apply rules of the game
    private boolean applyRules(boolean isAlive, int liveNeighbors) {
        if (isAlive) {
            return liveNeighbors == 2 || liveNeighbors == 3; // Live cells survive
        } else {
            return liveNeighbors == 3; // Dead cells become alive
        }
    }

    // Check if two boards are the same
    public boolean isSame(final Board other) {
        if (other.getNumRows() != getNumRows() || other.getNumCols() != getNumCols()) {
            return false;
        }

        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumCols(); j++) {
                if (cells[i][j] != other.getCell(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Create a string representation of the board
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "\n");
        sb.append(getNumRows()).append("\n").append(getNumCols()).append("\n");
        for (boolean[] row : cells) {
            for (boolean cell : row) {
                sb.append(cell ? 'X' : ' '); // Use ' ' for dead cells
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Constructor for creating a new Board from a 2D boolean array
    private Board(boolean[][] newCells) {
        this.cells = newCells;
        this.name = "Unnamed Board"; // Default name
    }
}
