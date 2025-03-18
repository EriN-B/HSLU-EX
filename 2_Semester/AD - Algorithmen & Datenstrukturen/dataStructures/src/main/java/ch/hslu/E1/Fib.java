package ch.hslu.E1;

public class Fib {
    public static int fiboRect1(int n) {
        //Rekursionsbasis
        if (n <= 2) {
            return 1;
        }
        //Rekursionsschritt
        else {
            return fiboRect1(n - 1) + fiboRect1(n - 2);
        }
    }

    private static long[] memo;

    public static long fiboRec2(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n muss >= 0 sein");
        }
        memo = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1;
        }
        memo[0] = 0;
        if (n >= 1) {
            memo[1] = 1;
        }
        return fiboRec2Helper(n);
    }

    public static long fiboRec2Helper(int n) {
        if (memo[n] != -1) {
            return memo[n];
        }
        memo[n] = fiboRec2Helper(n - 1) + fiboRec2Helper(n - 2);
        return memo[n];
    }

    public static int fiboIter(int n) {
        int carry = 0;
        int next = 1;

        for (int i = 2; i <= n; i++) {
            int oldCarry = carry;
            carry = next;
            next = oldCarry + next;
        }

        return next;
    }

    public static int factorialRec(final int n) {
        if ((n == 0) || (n == 1)) {
            return 1;
        } else {
            return (n * factorialRec(n - 1));
        }
    }
}

