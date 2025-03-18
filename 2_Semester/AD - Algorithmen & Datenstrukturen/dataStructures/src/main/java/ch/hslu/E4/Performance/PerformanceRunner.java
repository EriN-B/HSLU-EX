package ch.hslu.E4.Performance;

public class PerformanceRunner {
    public static void main(String[] args) {
        int dataSize = 100_000_000;

        System.out.println("=== Stack Performance Comparison (" + dataSize + " elements) ===");

        String[] testData = PerformanceUtils.generateArray(dataSize);
        long arrayDequeTime = StackPerformanceTests.arrayDeque(testData);
        long javaStackTime = StackPerformanceTests.javaStack(testData);
        long customStackTime = StackPerformanceTests.customStack(testData);

        System.out.printf("%-20s : %d ms%n", "Java Stack", javaStackTime);
        System.out.printf("%-20s : %d ms%n", "Custom Stack", customStackTime);
        System.out.printf("%-20s : %d ms%n", "ArrayDeque", arrayDequeTime);
    }
}
