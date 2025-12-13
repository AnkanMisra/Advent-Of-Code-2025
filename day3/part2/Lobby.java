/*
 * Author  : Ankan Misra
 * Created : 2025-12-13
 * AoC Java Lobby - Part 2 solution
 *
 * Problem summary:
 * - Each input line is a bank of battery digit labels.
 * - For Part 2 we must select exactly 12 batteries (digits) from each
 *   bank, keeping their original order, to form the largest possible
 *   12-digit number. The total output is the sum of all those numbers.
 *
 * Approach:
 * - This is the classical "maximum subsequence of length k" problem.
 * - Greedy algorithm (monotonic stack): iterate the digits and maintain
 *   a stack representing the best prefix. We may remove (n - k) digits total
 *   across the whole number. While we still can remove digits and the next
 *   digit is greater than the stack top, we pop the stack to make room for
 *   a larger digit. Push the current digit. After processing all digits,
 *   truncate any remaining removals from the end of the stack and take the
 *   first k digits as the maximum subsequence.
 * - Correctness: this greedy approach produces the lexicographically (and
 *   thus numerically) largest subsequence of the requested length.
 *
 * Implementation details:
 * - We use a fixed integer PICK=12 for the number of digits to select.
 * - Each line is processed in O(n) time and O(n) extra space for the stack.
 * - The digits selected form numbers larger than fits in long, so we use
 *   BigInteger to accumulate the total safely.
 *
 * Example:
 * - For bank 234234234234278 and k = 12, the algorithm selects 434234234278.
 */

import java.io.*;
import java.util.*;
import java.math.*;

public class Lobby {

    private static final int PICK = 12;

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        BigInteger total = BigInteger.ZERO;
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.isEmpty()) break;

            line = line.trim();
            if (line.isEmpty()) break;

            String maxJoltage = maxSubsequenceOfLength(line, PICK);
            total = total.add(new BigInteger(maxJoltage));
        }

        out.println(total);
    }

    private static String maxSubsequenceOfLength(String digits, int keep) {
        int n = digits.length();
        if (n < keep) {
            throw new IllegalArgumentException(
                    "Bank length " + n + " is smaller than required " + keep + ".");
        }

        int toRemove = n - keep;
        char[] stack = new char[n];
        int size = 0;

        for (int i = 0; i < n; i++) {
            char c = digits.charAt(i);
            while (toRemove > 0 && size > 0 && stack[size - 1] < c) {
                size--;
                toRemove--;
            }
            stack[size++] = c;
        }

        size -= toRemove;
        return new String(stack, 0, keep);
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
