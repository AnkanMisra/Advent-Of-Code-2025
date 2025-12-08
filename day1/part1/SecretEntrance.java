/*
 * author  : Ankan Misra
 * created : 2025-12-08 at 19:09:26 PM IST
 * problem : Advent of Code 2025 - Day 1 - Secret Entrance
 */


import java.io.*;
import java.util.*;

public class SecretEntrance {

    // ------------------------ main logic start ------------------------

    private static void solve(FastScanner sc, FastOutput out) throws Exception {
        // AoC inputs typically don't start with a test case count.
        // We just read tokens until the stream ends.
        
        int currentPos = 50;
        int zeroHits = 0;
        int instructionCount = 0;
        final int DIAL_SIZE = 100;

        while (true) {
            String instruction = sc.next();
            // FastScanner returns empty string on EOF
            if (instruction == null || instruction.isEmpty()) break;
            
            instructionCount++;

            char direction = instruction.charAt(0);
            
            // Parse amount and immediately modulo by DIAL_SIZE.
            // This handles very large numbers (rotations > 100) and prevents integer overflow.
            int amount = Integer.parseInt(instruction.substring(1));
            amount = amount % DIAL_SIZE;

            if (direction == 'R') {
                // Right rotation (Clockwise / Ascending)
                currentPos = (currentPos + amount) % DIAL_SIZE;
            } else if (direction == 'L') {
                // Left rotation (Counter-Clockwise / Descending)
                currentPos = (currentPos - amount) % DIAL_SIZE;
                // Java's % can return negative values for negative dividends
                if (currentPos < 0) currentPos += DIAL_SIZE;
            }

            if (currentPos == 0) {
                zeroHits++;
            }
        }

        // Debug output to verify if we read the full file or just the example
        System.err.println("Debug: Processed " + instructionCount + " instructions.");
        
        out.println(zeroHits);
    }

    // ------------------------ main logic end ------------------------

    public static void main(String[] args) {
        try (FastScanner sc = new FastScanner(); FastOutput out = new FastOutput()) {
            solve(sc, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------ Utility Methods ------------------------

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    static void sort(int[] arr) {
        Arrays.sort(arr);
    }

    static void sort(long[] arr) {
        Arrays.sort(arr);
    }

    static void YES(FastOutput out) {
        out.println("YES");
    }

    static void NO(FastOutput out) {
        out.println("NO");
    }

    static void printArray(FastOutput out, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            out.print(arr[i]);
            if (i < arr.length - 1) out.print(' ');
        }
        out.println();
    }

    static void printArray(FastOutput out, long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            out.print(arr[i]);
            if (i < arr.length - 1) out.print(' ');
        }
        out.println();
    }

    // ------------------------ FastOutput ------------------------

    static class FastOutput implements Closeable {
        private final OutputStream out = System.out;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private static final byte[] LINE_SEPARATOR = System.lineSeparator().getBytes();

        private void flushBuffer() {
            try {
                out.write(buffer, 0, ptr);
                ptr = 0;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        private void ensureCapacity(int n) {
            if (ptr + n > buffer.length) flushBuffer();
        }

        @Override
        public void close() throws IOException {
            flushBuffer();
            out.flush();
        }

        public void print(char c) {
            ensureCapacity(1);
            buffer[ptr++] = (byte) c;
        }

        public void print(String s) {
            byte[] bytes = s.getBytes();
            ensureCapacity(bytes.length);
            System.arraycopy(bytes, 0, buffer, ptr, bytes.length);
            ptr += bytes.length;
        }

        public void println(String s) {
            print(s);
            println();
        }

        public void println() {
            ensureCapacity(LINE_SEPARATOR.length);
            System.arraycopy(LINE_SEPARATOR, 0, buffer, ptr, LINE_SEPARATOR.length);
            ptr += LINE_SEPARATOR.length;
        } 

        public void print(int n) {
            if (n == Integer.MIN_VALUE) {
                print("-2147483648");
                return;
            }
            if (n < 0) {
                print('-');
                n = -n;
            }
            int start = ptr;
            do {
                ensureCapacity(1);
                buffer[ptr++] = (byte) ('0' + (n % 10));
                n /= 10;
            } while (n > 0);
            reverse(start, ptr - 1);
        }

        public void println(int n) {
            print(n);
            println();
        }

        public void print(long n) {
            if (n == Long.MIN_VALUE) {
                print("-9223372036854775808");
                return;
            }
            if (n < 0) {
                print('-');
                n = -n;
            }
            int start = ptr;
            do {
                ensureCapacity(1);
                buffer[ptr++] = (byte) ('0' + (n % 10));
                n /= 10;
            } while (n > 0);
            reverse(start, ptr - 1);
        }

        public void println(long n) {
            print(n);
            println();
        }

        private void reverse(int l, int r) {
            while (l < r) {
                byte tmp = buffer[l];
                buffer[l] = buffer[r];
                buffer[r] = tmp;
                l++;
                r--;
            }
        }
    }

    // ------------------------ FastScanner ------------------------

    static class FastScanner implements Closeable {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner() {
            in = System.in;
        }

        private int readByte() throws IOException {
            if (ptr >= len) {
                ptr = 0;
                len = in.read(buffer);
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = readByte()) != -1 && c <= ' ');
            while (c != -1 && c > ' ') {
                sb.append((char) c);
                c = readByte();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            int c = readByte();
            while (c <= ' ') c = readByte();
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = readByte();
            }
            int val = 0;
            while (c >= '0' && c <= '9') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return val * sign;
        }

        long nextLong() throws IOException {
            int c = readByte();
            while (c <= ' ') c = readByte();
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = readByte();
            }
            long val = 0;
            while (c >= '0' && c <= '9') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return val * sign;
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int[] readIntArray(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = nextInt();
            return arr;
        }

        long[] readLongArray(int n) throws IOException {
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) arr[i] = nextLong();
            return arr;
        }

        @Override
        public void close() throws IOException {
            if (in != System.in) in.close();
        }
    }
}
