/*
 * Author  : Ankan Misra
 * Created : 2025-12-23
 * AoC Day 5 Part 2 - Cafeteria
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * PROBLEM SUMMARY:
 * ═══════════════════════════════════════════════════════════════════════════════
 * Given a list of fresh ingredient ID ranges, count the TOTAL number of unique
 * ingredient IDs that are considered fresh. Ranges can overlap!
 * 
 * Example:
 *   Ranges: 3-5, 10-14, 16-20, 12-18
 *   Fresh IDs: 3,4,5, 10,11,12,13,14,15,16,17,18,19,20 → Answer: 14
 *   
 *   Note: Ranges 10-14, 16-20, 12-18 overlap → merged to 10-20 (11 IDs)
 *         Plus range 3-5 (3 IDs) = 14 total unique IDs
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * ALGORITHM: Interval Merging
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * APPROACH:
 *   Step 1: Parse all ranges as [start, end] pairs
 *   Step 2: Sort ranges by start value (ascending)
 *   Step 3: Merge overlapping/adjacent ranges:
 *           - If current range overlaps with previous → extend the previous
 *           - Otherwise → start a new merged range
 *   Step 4: Sum up (end - start + 1) for each merged range
 * 
 * WHY MERGING WORKS:
 *   - Sorting ensures we process ranges left-to-right
 *   - Two ranges [a,b] and [c,d] overlap if c <= b+1 (adjacent or overlapping)
 *   - Merged range becomes [a, max(b,d)]
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPLEXITY ANALYSIS:
 * ═══════════════════════════════════════════════════════════════════════════════
 *   Time:  O(M log M) where M = number of ranges (sorting dominates)
 *   Space: O(M) to store ranges
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */

import java.io.*;
import java.util.*;

public class Cafeteria2 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 1: Parse all fresh ingredient ID ranges
        // ─────────────────────────────────────────────────────────────────────
        List<long[]> ranges = new ArrayList<>();
        
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) break;  // Blank line = end of ranges
            
            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 2: Sort ranges by start value (ascending)
        // ─────────────────────────────────────────────────────────────────────
        // This allows us to merge overlapping ranges in a single left-to-right pass
        ranges.sort((a, b) -> Long.compare(a[0], b[0]));
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 3: Merge overlapping ranges
        // ─────────────────────────────────────────────────────────────────────
        // Two ranges [a,b] and [c,d] overlap if c <= b+1
        // When they overlap, merge into [a, max(b,d)]
        List<long[]> merged = new ArrayList<>();
        
        for (long[] range : ranges) {
            if (merged.isEmpty()) {
                // First range - just add it
                merged.add(new long[]{range[0], range[1]});
            } else {
                long[] last = merged.get(merged.size() - 1);
                
                // Check if current range overlaps or is adjacent to the last merged range
                // Overlap condition: current.start <= last.end + 1
                if (range[0] <= last[1] + 1) {
                    // Merge: extend the last range's end if needed
                    last[1] = Math.max(last[1], range[1]);
                } else {
                    // No overlap - add as a new merged range
                    merged.add(new long[]{range[0], range[1]});
                }
            }
        }
        
        // ─────────────────────────────────────────────────────────────────────
        // STEP 4: Count total unique fresh ingredient IDs
        // ─────────────────────────────────────────────────────────────────────
        // For each merged range [start, end], count = end - start + 1
        long totalFreshIds = 0;
        
        for (long[] range : merged) {
            totalFreshIds += (range[1] - range[0] + 1);
        }
        
        out.println(totalFreshIds);
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
