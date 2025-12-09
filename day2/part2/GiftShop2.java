/*
 * Author  : Ankan Misra
 * Created : 2025-12-09
 * AoC Java GiftShop2 
 */

import java.io.*;
import java.util.*;

public class GiftShop2 {
    /*
     * My Solution Intuition:
     * 
     * When I looked at the problem, I realized that an "invalid" ID is essentially a string that is periodic.
     * That is, the entire string is composed of a smaller substring repeated multiple times.
     * 
     * For example:
     * - "123123" is "123" repeated twice.
     * - "121212" is "12" repeated three times.
     * - "1111" is "1" repeated four times (or "11" repeated twice).
     * 
     * My approach was to iterate through every number in the given ranges. For each number, I convert it to a string.
     * Then, I check for potential lengths of the repeating unit (let's call it 'sub').
     * The length of 'sub' must be a divisor of the total length of the string, and it must be at most half the length
     * (since it needs to repeat at least twice).
     * 
     * I iterate through all possible lengths 'i' from 1 to len/2.
     * If 'len' is divisible by 'i', I take the prefix of length 'i' as my candidate pattern.
     * Then I check if the rest of the string is just repetitions of this candidate pattern.
     * If I find such a pattern, I mark the ID as invalid and add it to my total sum.
     */

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        long totalSum = 0;
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) break;

            String[] ranges = line.split(",");
            for (String range : ranges) {
                String[] parts = range.split("-")Owo;
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);

                for (long id = start; id <= end; id++) {
                    if (isInvalid(id)) {
                        totalSum += id;
                    }
                }
            }
        }
        out.println(totalSum);
    }

    private static boolean isInvalid(long id) {
        String s = Long.toString(id);
        int len = s.length();
        for (int i = 1; i <= len / 2; i++) {
            if (len % i == 0) {
                String sub = s.substring(0, i);
                boolean match = true;
                for (int j = i; j < len; j += i) {
                    if (!s.startsWith(sub, j)) {
                        match = false;
                        break;
                    }
                }
                if (match) return true;
            }
        }
        return false;
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
