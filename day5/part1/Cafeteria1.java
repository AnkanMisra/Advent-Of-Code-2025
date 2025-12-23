/*
 * Author  : Ankan Misra
 * Created : 2025-12-23
 * AoC Day 5 Part 1 - Cafeteria
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * PROBLEM SUMMARY:
 * ═══════════════════════════════════════════════════════════════════════════════
 * Given:
 *   1. A list of "fresh" ingredient ID ranges (format: start-end, inclusive)
 *   2. A blank line separator  
 *   3. A list of available ingredient IDs to check
 * 
 * Task: Count how many available ingredient IDs are "fresh" (fall within ANY range)
 * 
 * Example:
 *   Ranges: 3-5, 10-14, 16-20, 12-18
 *   IDs to check: 1, 5, 8, 11, 17, 32
 *   Fresh IDs: 5 (in 3-5), 11 (in 10-14), 17 (in 16-20 & 12-18) → Answer: 3
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * ALGORITHM: Brute Force Range Checking
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * APPROACH:
 *   Step 1: Parse and store all fresh ranges as [start, end] pairs
 *   Step 2: For each available ingredient ID:
 *           - Check if it falls within ANY of the stored ranges
 *           - If ID >= range.start AND ID <= range.end → it's fresh
 *           - Once found in one range, no need to check others (break early)
 *   Step 3: Output the count of fresh ingredients
 * 
 * WHY THIS WORKS:
 *   - Ranges are INCLUSIVE on both ends (3-5 means 3, 4, 5 are all valid)
 *   - An ID is fresh if it matches AT LEAST ONE range (ranges can overlap)
 *   - Simple linear scan through ranges for each ID
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPLEXITY ANALYSIS:
 * ═══════════════════════════════════════════════════════════════════════════════
 *   Time:  O(N * M) where N = number of ingredient IDs, M = number of ranges
 *   Space: O(M) to store all ranges
 * 
 * IMPORTANT NOTE:
 *   - Using 'long' instead of 'int' because ingredient IDs can be up to ~560 trillion
 *   - int max is ~2.1 billion, long max is ~9.2 quintillion
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import java.io.*;
import java.util.*;

public class Cafeteria {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 1: Parse all fresh ingredient ID ranges
        // ─────────────────────────────────────────────────────────────────────
        // Store ranges as [start, end] pairs using long[] arrays
        // Using long because IDs can exceed int range (up to ~560 trillion)
        List<long[]> ranges = new ArrayList<>();
        
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) break;  // Blank line = end of ranges
            
            // Parse "start-end" format (e.g., "3-5" → start=3, end=5)
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 2: Check each available ingredient ID against all ranges
        // ─────────────────────────────────────────────────────────────────────
        int freshCount = 0;
        String line;
        
        while ((line = sc.nextLine()) != null) {
            if (line.isEmpty()) continue;
            
            long ingredientId = Long.parseLong(line);
            
            // Linear search through all ranges to find a match
            // An ingredient is "fresh" if it falls within ANY range (inclusive)
            for (long[] range : ranges) {
                // Check: range[0] <= ingredientId <= range[1]
                if (ingredientId >= range[0] && ingredientId <= range[1]) {
                    freshCount++;   // Found in a valid range!
                    break;          // No need to check remaining ranges
                }
            }
        }
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 3: Output the count of fresh ingredients
        // ─────────────────────────────────────────────────────────────────────
        out.println(freshCount);
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
