package ch.hslu.ad.exercise.n4.fibo;

import java.util.concurrent.RecursiveTask;

public final class FibonacciTask extends RecursiveTask<Long> {

    private static int threshold = 10;

    public static void setThreshold(int value) {
        threshold = value;
    }

    private final int n;

    public FibonacciTask(final int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= threshold) {
            return FibonacciCalc.fiboRecursive(n);
        } else {
            FibonacciTask f1 = new FibonacciTask(n - 1);
            f1.fork();
            FibonacciTask f2 = new FibonacciTask(n - 2);
            long result2 = f2.compute();
            long result1 = f1.join();
            return result1 + result2;
        }
    }
}
