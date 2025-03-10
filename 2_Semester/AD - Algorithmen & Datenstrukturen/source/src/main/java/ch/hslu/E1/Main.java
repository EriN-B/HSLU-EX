package ch.hslu.E1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        task(100);
    }

    public static void task(final int n) throws InterruptedException {
        int task1Counter = 0;
        int task2Counter = 0;
        int task3Counter = 0;

        long task1Timer = 0;
        long task2Timer = 0;
        long task3Timer = 0;

        long startTime = System.nanoTime();
        task1();
        task1();
        task1();
        task1();
        long endTime = System.nanoTime();
        task1Timer = (endTime - startTime) / 1_000_000;
        task1Counter += 4;

        for (int i = 0; i < n; i++) {
            long startTime2 = System.nanoTime();
            task2();
            task2();
            task2();
            long endTime2  = System.nanoTime();
            task2Timer += (endTime2 - startTime2);
            task2Counter += 3;

            for (int j = 0; j < n; j++) {
                long startTime3 = System.nanoTime();
                task3();
                task3();
                long endTime3 = System.nanoTime();
                task3Timer += (endTime3 - startTime3);
                task3Counter += 2;
            }
        }

        long task2TimerMs = task2Timer / 1_000_000;
        long task3TimerMs = task3Timer / 1_000_000;

        logger.info("-----------------------------------------------------");
        logger.info("| Task   | Aufrufe   | Laufzeit (ms)            |");
        logger.info("-----------------------------------------------------");
        logger.info(String.format("| task1  | %4d      | %4d                     |", task1Counter, task1Timer));
        logger.info(String.format("| task2  | %4d      | %4d                     |", task2Counter, task2TimerMs));
        logger.info(String.format("| task3  | %4d      | %4d                     |", task3Counter, task3TimerMs));
        logger.info("-----------------------------------------------------");
    }

    public static void task1() throws InterruptedException {
        Thread.sleep(5);
    }

    public static void task2() throws InterruptedException {
        Thread.sleep(5);
    }

    public static void task3() throws InterruptedException {
        Thread.sleep(5);
    }
}
