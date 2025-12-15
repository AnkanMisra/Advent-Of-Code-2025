
/*
 * Author  : Ankan Misra
 * Created : 2025-12-15
 * AoC Java PrintingDepartment1 
 */

import java.io.*;
import java.util.*;

public class PrintingDepartment1 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        // First, I need to read the whole grid into memory. 
        // Since I don't know how many lines there are beforehand, I'll use a list.
        List<String> rawLines = new ArrayList<>();
        while (true) {
            String line = sc.nextLine();
            // Stop if the line is null (EOF) or empty (end of input block)
            if (line == null || line.isEmpty()) break;
            rawLines.add(line);
        }

        int rows = rawLines.size();
        if (rows == 0) return; // safety check for empty input
        int cols = rawLines.get(0).length();

        // Convert list to a 2D char array for easier coordinate access (grid[r][c])
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = rawLines.get(i).toCharArray();
        }

        int accessibleCount = 0;

        // Note: These arrays represent the 8 directions (offsets) for neighbors:
        // Top-Left, Top, Top-Right, Left, Right, Bottom-Left, Bottom, Bottom-Right.
        // It's way cleaner than writing 8 if-statements manually!
        int[] dRow = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dCol = {-1,  0,  1, -1, 1, -1, 0, 1};

        // Iterate through every cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                
                // We only care about rolls of paper ('@'). Skip empty spots ('.').
                if (grid[r][c] == '@') {
                    int neighborPaperCount = 0;

                    // Check all 8 neighbors
                    for (int i = 0; i < 8; i++) {
                        int nr = r + dRow[i];
                        int nc = c + dCol[i];

                        // Important: Check bounds so we don't get an IndexOutOfBoundsException
                        // at the edges of the map.
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            if (grid[nr][nc] == '@') {
                                neighborPaperCount++;
                            }
                        }
                    }

                    // The rule: Forklifts can access if FEWER than 4 neighbors are paper.
                    if (neighborPaperCount < 4) {
                        accessibleCount++;
                    }
                }
            }
        }

        // Print the final answer
        out.println(accessibleCount);
    }

    public static void main(String[] args) {
        try (FastScanner sc = new FastScanner(); FastOutput out = new FastOutput()) {
            solve(sc, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class FastOutput implements Closeable {
        private final PrintWriter out = new PrintWriter(System.out);
        public void println(Object o) { out.println(o); }
        @Override public void close() { out.flush(); }
    }

    static class FastScanner implements Closeable {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nextLine() throws IOException { return br.readLine(); }
        @Override public void close() throws IOException { br.close(); }
    }
}