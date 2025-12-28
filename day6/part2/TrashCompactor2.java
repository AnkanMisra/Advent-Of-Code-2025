/*
 * Author  : Ankan Misra
 * Created : 2025-12-28
 * AoC 2025 Day 6 Part 2 - Trash Compactor (Cephalopod Math!)

 * The Twist in Part 2:
 * --------------------
 * Cephalopod math is WILD! Here's how they write numbers:
 * 1. Read problems RIGHT-TO-LEFT (rightmost problem first!)
 * 2. Each COLUMN is a DIGIT of a number (not the whole number)
 * 3. Top digit = most significant, bottom digit = least significant
 * 
 * Example:
 *   123 328  51 64 
 *    45 64  387 23 
 *     6 98  215 314
 *   *   +   *   +  
 * 
 * Reading right-to-left, rightmost problem "64/23/314/+":
 *   - Column 1: digits 6,2,3 → number 623
 *   - Column 2: digits 4,3,1 → number 431  
 *   - Column 3: digit 4 → number 4
 *   Result: 4 + 431 + 623 = 1058
 * 
 * Wait no, looking more carefully at the example:
 *   "4 + 431 + 623" - so reading column by column from right:
 *   - Rightmost column of the problem: 4, 3, 4 → 4 (single digit visible)
 *   Actually the example says: 4 + 431 + 623 = 1058
 * 
 * So within each problem:
 *   - Each COLUMN becomes a separate NUMBER
 *   - Digits stack vertically: top=100s place, middle=10s, bottom=1s
 *   - Read these columns from RIGHT to LEFT
 * 
 * Time Complexity: O(rows * cols)
 * Space Complexity: O(rows * cols)
 * ============================================================================
 */

import java.io.*;
import java.util.*;

public class TrashCompactor2 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        // Step 1: Read all input lines
        List<String> lines = new ArrayList<>();
        while (true) {
            String line = sc.nextLine();
            if (line == null) break;
            lines.add(line);
        }
        
        // Clean up trailing empty lines
        while (!lines.isEmpty() && lines.get(lines.size() - 1).trim().isEmpty()) {
            lines.remove(lines.size() - 1);
        }
        
        if (lines.isEmpty()) {
            out.println(0);
            return;
        }
        
        // Step 2: Separate number rows from operator row
        String operatorLine = lines.get(lines.size() - 1);
        List<String> numberLines = lines.subList(0, lines.size() - 1);
        
        // Step 3: Pad all lines to same length
        int maxLen = 0;
        for (String line : lines) {
            maxLen = Math.max(maxLen, line.length());
        }
        
        List<String> paddedLines = new ArrayList<>();
        for (String line : lines) {
            StringBuilder sb = new StringBuilder(line);
            while (sb.length() < maxLen) {
                sb.append(' ');
            }
            paddedLines.add(sb.toString());
        }
        
        String paddedOperatorLine = paddedLines.get(paddedLines.size() - 1);
        List<String> paddedNumberLines = paddedLines.subList(0, paddedLines.size() - 1);
        
        // Step 4: Find all problems (separated by all-space columns)
        // Store start and end columns for each problem
        List<int[]> problems = new ArrayList<>();  // Each is [startCol, endCol]
        int col = 0;
        
        while (col < maxLen) {
            // Skip separator columns
            while (col < maxLen && isAllSpace(paddedLines, col)) {
                col++;
            }
            if (col >= maxLen) break;
            
            int startCol = col;
            while (col < maxLen && !isAllSpace(paddedLines, col)) {
                col++;
            }
            int endCol = col;
            
            problems.add(new int[]{startCol, endCol});
        }
        
        // Step 5: Process problems RIGHT-TO-LEFT
        long grandTotal = 0;
        
        for (int p = problems.size() - 1; p >= 0; p--) {
            int startCol = problems.get(p)[0];
            int endCol = problems.get(p)[1];
            
            // Get the operator for this problem
            String opSegment = paddedOperatorLine.substring(startCol, endCol).trim();
            char operator = opSegment.isEmpty() ? '+' : opSegment.charAt(0);
            
            // Step 6: Within this problem, each column is a NUMBER
            // Read digits vertically (top = most significant)
            // Process columns from RIGHT to LEFT
            List<Long> numbers = new ArrayList<>();
            
            for (int c = endCol - 1; c >= startCol; c--) {
                // Build number from this column: top digit is most significant
                StringBuilder numStr = new StringBuilder();
                for (String line : paddedNumberLines) {
                    char ch = line.charAt(c);
                    if (Character.isDigit(ch)) {
                        numStr.append(ch);
                    }
                }
                
                if (numStr.length() > 0) {
                    numbers.add(Long.parseLong(numStr.toString()));
                }
            }
            
            // Step 7: Apply the operator
            long result;
            if (operator == '+') {
                result = 0;
                for (long num : numbers) {
                    result += num;
                }
            } else {  // '*'
                result = 1;
                for (long num : numbers) {
                    result *= num;
                }
            }
            
            grandTotal += result;
        }
        
        out.println(grandTotal);
    }
    
    private static boolean isAllSpace(List<String> lines, int col) {
        for (String line : lines) {
            if (col < line.length() && line.charAt(col) != ' ') {
                return false;
            }
        }
        return true;
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
