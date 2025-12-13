/*
 * Author  : Ankan Misra
 * Created : 2025-12-13
 * AoC Java Lobby 
 *
 *It was about batteries in banks, where each line of input represents a bank of digits, and I needed to find the maximum two-digit joltage by choosing the highest possible pair without rearranging them. The key insight was that for each position, I'd pair the current digit with the maximum digit to its right, then take the overall max across all such pairs per line, and sum those maxima.
 *
 * I started by sketching the logic in my mind: for a string like "987654321111111", the best is 98 from the first two. So, I used a suffix maximum array to track the highest digit from each position onward, then iterated through each possible left digit, computed the two-digit value with the suffix max, and kept the global best per line.
 *
 * In code, I implemented this in the solve method of Lobby.java, using a long for the total sum to handle large inputs. I removed the comments as requested and kept it clean. After compiling and running with "java Lobby < input.txt", it gave the correct total joltage. Felt good to optimize it to O(n) per line with the suffix array approach!
 */

import java.io.*;
import java.util.*;

public class Lobby {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        long total = 0L;
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) break;

            int n = line.length();
            if (n < 2) continue;

            int[] suffixMax = new int[n + 1];
            suffixMax[n] = -1;
            for (int i = n - 1; i >= 0; i--) {
                int d = line.charAt(i) - '0';
                suffixMax[i] = Math.max(suffixMax[i + 1], d);
            }

            int best = 0;
            for (int i = 0; i < n - 1; i++) {
                int a = line.charAt(i) - '0';
                int b = suffixMax[i + 1];
                int val = a * 10 + b;
                if (val > best) best = val;
            }

            total += best;
        }
        out.println(total);
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
