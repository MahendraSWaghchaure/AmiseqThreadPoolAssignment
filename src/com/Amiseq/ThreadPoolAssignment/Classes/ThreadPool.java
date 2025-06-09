package com.Amiseq.ThreadPoolAssignment.Classes;

import java.util.Scanner;
import java.util.logging.Logger;

import com.Amiseq.ThreadPoolAssignment.Interfaces.DataCollect;
import com.Amiseq.ThreadPoolAssignment.Interfaces.NumGenerator;


public class ThreadPool {
    private static final Logger logger = Logger.getLogger(ThreadPool.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Concurrent Processing Application");

        WorkerThreadPool threadPool = new WorkerThreadPool(4, 50);
        DataCollect<String> collector = new OutputCollection();
        
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Sequence1 starting point and difference between them:");
        int s1 = sc.nextInt();
        int d1 = sc.nextInt();

        System.out.println("Enter the Sequence2 starting point and difference between them:");
        int s2 = sc.nextInt();
        int d2 = sc.nextInt();
        
        NumGenerator generator1 = new NumSequenceGenerator(s1, d1, "SERIES_A", threadPool, collector);
        NumGenerator generator2 = new NumSequenceGenerator(s2, d2, "SERIES_B", threadPool, collector);

        ConsoleOutput printer = new ConsoleOutput(collector);

        Thread gen1Thread = new Thread(generator1, "Generator-1");
        Thread gen2Thread = new Thread(generator2, "Generator-2");
        Thread printerThread = new Thread(printer, "Printer");

        gen1Thread.start();
        gen2Thread.start();
        printerThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown initiated");
            generator1.stop();
            generator2.stop();
            printer.stop();
            threadPool.shutdown();
        }));

        try {
            Thread.sleep(30000);
            sc.close();
            System.exit(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
