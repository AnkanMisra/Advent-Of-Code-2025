/*
 * Author  : Ankan Misra
 * Created : 2025-12-08
 * AoC Java GiftShop1 
 */

/*
 * Algorithm / Intuition:
 * 
 * 1. Input Parsing:
 *    - The input is a single line containing multiple ranges separated by commas (e.g., "11-22,95-115").
 *    - Each range is defined by a start and end value separated by a hyphen.
 * 
 * 2. Processing Ranges:
 *    - We split the input string by ',' to get individual range strings.
 *    - For each range string, we split by '-' to extract the start and end integers.
 *    - We then iterate through every number from 'start' to 'end' (inclusive).
 * 
 * 3. Identifying Invalid IDs:
 *    - A number is considered "invalid" if it is formed by a sequence of digits repeated twice.
 *    - Examples: 55 (5 repeated), 1212 (12 repeated), 38593859 (3859 repeated).
 *    - Check:
 *      a. Convert the number to a string.
 *      b. If the string length is odd, it cannot be a repeated sequence.
 *      c. If even, compare the first half of the string with the second half.
 *      d. If both halves are identical, the ID is invalid.
 * 
 * 4. Result:
 *    - Sum up all the "invalid" IDs found across all ranges and print the total.
 */

import java.io.*;
import java.util.*;

public class GiftShop1 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        // Read the single line of input containing all the ID ranges.
        String line = sc.nextLine();
        if (line == null || line.isEmpty()) return;

        // Split the input string by commas to process each range independently.
        String[] ranges = line.split(",");
        long totalSum = 0;

        // Iterate through each range string.
        for (String range : ranges) {
            // Parse the start and end values from the "start-end" format.
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            // Loop through every ID in the current range to check for validity.
            for (long i = start; i <= end; i++) {
                // If the ID matches the invalid pattern (repeated sequence), add it to the sum.
                if (isInvalid(i)) {
                    totalSum += i;
                }
            }
        }
        out.println(totalSum);
    }

    private static boolean isInvalid(long n) {
        // Convert the number to a string to easily check for the repeated pattern.
        String s = Long.toString(n);
        int len = s.length();
        // Optimization: A string formed by repeating a sequence twice must have an even length.
        if (len % 2 != 0) return false;
        
        // Check if the first half of the string is identical to the second half.
        int mid = len / 2;
        for (int i = 0; i < mid; i++) {
            // Compare character at index i with the corresponding character in the second half.
            if (s.charAt(i) != s.charAt(mid + i)) {
                return false;
            }
        }
        // If the loop completes, the pattern holds true.
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
