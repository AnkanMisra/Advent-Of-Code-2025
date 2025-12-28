/*
 * Author  : Ankan Misra
 * Created : 2025-12-28
 * AoC 2025 Day 6 Part 1 - Trash Compactor
 * 

 * The Problem:
 * ------------
 * We have a weird math worksheet where problems are arranged VERTICALLY!
 * Each column of numbers is one problem, and the operator (+/*) is at the bottom.
 * Problems are separated by columns that are ALL spaces.
 * 
 * Example:
 *   123 328  51 64 
 *    45 64  387 23 
 *     6 98  215 314
 *   *   +   *   +  
 * 
 * This gives us: 123*45*6=33210, 328+64+98=490, 51*387*215=4243455, 64+23+314=401
 * Grand total = 33210 + 490 + 4243455 + 401 = 4277556
 * 
 * My Strategy:
 * ------------
 * 1. Read all lines into memory (it's just 5-6 lines, no big deal)
 * 
 * 2. The LAST line has operators (+/*), everything above has numbers
 * 
 * 3. The KEY INSIGHT: Problems are separated by FULL columns of spaces!
 *    So I scan column by column:
 *    - If ALL rows have a space at column i -> it's a separator
 *    - Otherwise -> it's part of a problem
 * 
 * 4. For each problem (group of consecutive non-separator columns):
 *    - Extract the number from each row (trim whitespace, parse as long)
 *    - Get the operator from the last row
 *    - Apply the operation: + means sum all, * means multiply all
 * 
 * 5. Add up all the individual problem results = Grand Total!
 * 
 * Why this works:
 * - I pad all lines to the same length (so no index out of bounds)
 * - Using long instead of int (multiplication can get HUGE)
 * - The column-scanning approach handles varying column widths naturally
 * 
 * Time Complexity: O(rows * cols) - just scanning the grid once
 * Space Complexity: O(rows * cols) - storing the padded lines
 * 
 * ============================================================================
 */

import java.io.*;
import java.util.*;

public class TrashCompactor1 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        // Step 1: Read all input lines into a list
        List<String> lines = new ArrayList<>();
        while (true) {
            String line = sc.nextLine();
            if (line == null) break;
            lines.add(line);
        }
        
        // Clean up: Remove any trailing empty lines
        while (!lines.isEmpty() && lines.get(lines.size() - 1).trim().isEmpty()) {
            lines.remove(lines.size() - 1);
        }
        
        // Edge case: empty input
        if (lines.isEmpty()) {
            out.println(0);
            return;
        }
        
        // Step 2: Separate number rows from the operator row (last row)
        String operatorLine = lines.get(lines.size() - 1);
        List<String> numberLines = lines.subList(0, lines.size() - 1);
        
        // Step 3: Find max line length and pad all lines to same length
        // This makes column-by-column scanning easier (no bounds checking needed)
        int maxLen = 0;
        for (String line : lines) {
            maxLen = Math.max(maxLen, line.length());
        }
        
        List<String> paddedLines = new ArrayList<>();
        for (String line : lines) {
            StringBuilder sb = new StringBuilder(line);
            while (sb.length() < maxLen) {
                sb.append(' ');  // Pad with spaces on the right
            }
            paddedLines.add(sb.toString());
        }
        
        String paddedOperatorLine = paddedLines.get(paddedLines.size() - 1);
        List<String> paddedNumberLines = paddedLines.subList(0, paddedLines.size() - 1);
        
        // Step 4: Scan columns to find and solve each problem
        long grandTotal = 0;
        int col = 0;
        
        while (col < maxLen) {
            // Skip separator columns (columns where ALL rows have a space)
            while (col < maxLen && isAllSpace(paddedLines, col)) {
                col++;
            }
            
            if (col >= maxLen) break;  // We've processed everything
            
            // Found start of a problem! Find where it ends
            int startCol = col;
            while (col < maxLen && !isAllSpace(paddedLines, col)) {
                col++;
            }
            int endCol = col;
            
            // Extract numbers from this problem's column range
            List<Long> numbers = new ArrayList<>();
            char operator = ' ';
            
            for (String line : paddedNumberLines) {
                String segment = line.substring(startCol, endCol).trim();
                if (!segment.isEmpty()) {
                    numbers.add(Long.parseLong(segment));
                }
            }
            
            // Extract the operator for this problem
            String opSegment = paddedOperatorLine.substring(startCol, endCol).trim();
            if (!opSegment.isEmpty()) {
                operator = opSegment.charAt(0);
            }
            
            // Step 5: Calculate the result based on operator
            long result;
            if (operator == '+') {
                // Addition: start with 0, add all numbers
                result = 0;
                for (long num : numbers) {
                    result += num;
                }
            } else {
                // Multiplication: start with 1, multiply all numbers
                result = 1;
                for (long num : numbers) {
                    result *= num;
                }
            }
            
            // Add this problem's result to our grand total
            grandTotal += result;
        }
        
        // Output the answer!
        out.println(grandTotal);
    }
    
    /**
     * Helper: Check if a specific column is ALL spaces across all lines.
     * If true -> this column is a separator between problems.
     */
    private static boolean isAllSpace(List<String> lines, int col) {
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                return false;  // Found a non-space character
            }
        }
        return true;  // Every row has a space at this column
    }

    public static void main(String[] args) {
        try (FastScanner sc = new FastScanner(); FastOutput out = new FastOutput()) {
            solve(sc, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fast I/O helpers (standard template for competitive programming)
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
