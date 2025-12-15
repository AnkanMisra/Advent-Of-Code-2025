/*
 * Author  : Ankan Misra
 * Created : 2025-12-15
 * AoC Java PrintingDepartment1 (Part 2)
 */

import java.io.*;
import java.util.*;

public class PrintingDepartment2 {

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

        long totalRemoved = 0;
        
        // Note: These arrays represent the 8 directions (offsets) for neighbors:
        // Top-Left, Top, Top-Right, Left, Right, Bottom-Left, Bottom, Bottom-Right.
        int[] dRow = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dCol = {-1,  0,  1, -1, 1, -1, 0, 1};

        // Part 2: We need to keep removing paper until we can't remove any more.
        // This is a simulation loop.
        while (true) {
            // List to store coordinates of paper rolls to remove in *this* round.
            // We need to find them all first before removing them, otherwise removing
            // one might affect the neighbor count of the one next to it instantly.
            List<int[]> toRemove = new ArrayList<>();

            // Iterate through every cell in the grid
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    
                    // We only care about rolls of paper ('@').
                    if (grid[r][c] == '@') {
                        int neighborPaperCount = 0;

                        // Check all 8 neighbors
                        for (int i = 0; i < 8; i++) {
                            int nr = r + dRow[i];
                            int nc = c + dCol[i];

                            // Check bounds
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                if (grid[nr][nc] == '@') {
                                    neighborPaperCount++;
                                }
                            }
                        }

                        // The rule: Forklifts can access (and remove) if FEWER than 4 neighbors are paper.
                        if (neighborPaperCount < 4) {
                            toRemove.add(new int[]{r, c});
                        }
                    }
                }
            }

            // If we found nothing to remove this round, we are done!
            if (toRemove.isEmpty()) {
                break;
            }

            // Otherwise, add to total and actually remove them from the grid
            totalRemoved += toRemove.size();
            for (int[] pos : toRemove) {
                grid[pos[0]][pos[1]] = '.'; // Using '.' to represent empty space/removed paper
            }
        }

        // Print the total number of rolls removed
        out.println(totalRemoved);
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