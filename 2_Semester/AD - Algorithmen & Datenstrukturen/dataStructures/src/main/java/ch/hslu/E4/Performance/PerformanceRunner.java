package ch.hslu.E4.Performance;

public class PerformanceRunner {
    public static void main(String[] args) {
        int dataSize = 10_000_000;

        System.out.println("=== Stack Performance Comparison (" + dataSize + " elements) ===");

        String[] testData = PerformanceUtils.generateArray(dataSize);
        long arrayDequeTime = StackPerformanceTests.arrayDeque(testData);
        long customStackTime = StackPerformanceTests.customStack(testData);
        long javaStackTime = StackPerformanceTests.javaStack(testData);


        System.out.printf("%-20s : %d ms%n", "Java Stack", javaStackTime);
        System.out.printf("%-20s : %d ms%n", "Custom Stack", customStackTime);
        System.out.printf("%-20s : %d ms%n", "ArrayDeque", arrayDequeTime);
    }
}
