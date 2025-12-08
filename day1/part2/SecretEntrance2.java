/*
 * Author  : Ankan Misra
 * Created : 2025-12-08
 *
 * AoC Java Boilerplate
 */

import java.io.*;
import java.util.*;

public class SecretEntrance2 {

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        long totalHits = 0;
        int currentPos = 50;
        final int DIAL_SIZE = 100;

        while (true) {
            String line = sc.nextLine();
            if (line == null) break; 
            
            line = line.trim();
            if (line.isEmpty()) continue;

            char direction = line.charAt(0);
            long amount = Long.parseLong(line.substring(1));

            // 1. Count hits from full rotations (every 360 spin passes 0 once)
            totalHits += amount / DIAL_SIZE;

            // 2. Simulate the remaining partial rotation
            int rem = (int) (amount % DIAL_SIZE);

            if (direction == 'R') {
                // Right (Clockwise): numbers increase (e.g. 50 -> 99 -> 0 -> 10)
                // We hit 0 if the rotation takes us past the 99->0 boundary.
                // Effectively, if current + remainder >= 100, we wrapped.
                if (currentPos + rem >= DIAL_SIZE) {
                    totalHits++;
                }
                currentPos = (currentPos + rem) % DIAL_SIZE;

            } else if (direction == 'L') {
                // Left (Counter-Clockwise): numbers decrease (e.g. 10 -> 0 -> 99 -> 50)
                // We hit 0 if we reach 0 or go past it downwards.
                // Note: If we START at 0, moving Left immediately goes to 99 (no hit).
                // So we only count a hit if we were strictly positive and the move covers the distance to 0.
                if (currentPos > 0 && rem >= currentPos) {
                    totalHits++;
                }
                
                // Update position (handle negative modulo in Java)
                currentPos = (currentPos - rem) % DIAL_SIZE;
                if (currentPos < 0) {
                    currentPos += DIAL_SIZE;
                }
            }
        }
        
        out.println(totalHits);
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