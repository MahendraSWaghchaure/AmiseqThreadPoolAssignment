package com.Amiseq.ThreadPoolAssignment.Classes;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import com.Amiseq.ThreadPoolAssignment.Interfaces.DataCollect;
import com.Amiseq.ThreadPoolAssignment.Interfaces.NumGenerator;
import com.Amiseq.ThreadPoolAssignment.Interfaces.ThreadPool;  // Use interface

public class NumSequenceGenerator implements NumGenerator {
    private static final Logger logger = Logger.getLogger(NumSequenceGenerator.class.getName());
    private static final Random random = new Random();

    private final int initialValue;
    private final int increment;
    private final String generatorId;
    private final ThreadPool threadPool;
    private final DataCollect<String> collector;
    private final AtomicBoolean active;

    public NumSequenceGenerator(int initialValue, int increment, String generatorId, 
                                ThreadPool threadPool, DataCollect<String> collector) {
        this.initialValue = initialValue;
        this.increment = increment;
        this.generatorId = generatorId;
        this.threadPool = threadPool;
        this.collector = collector;
        this.active = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        int currentValue = initialValue;
        logger.info("Starting sequence generator: " + generatorId);

        while (active.get()) {
            final int valueToProcess = currentValue;
            final String output = String.format("[%s] Generated: %d", generatorId, valueToProcess);

            threadPool.submitTask(() -> collector.addData(output));
            currentValue += increment;

            try {
                Thread.sleep(100 + random.nextInt(1900));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Generator " + generatorId + " was interrupted");
                break;
            }
        }

        logger.info("Sequence generator " + generatorId + " stopped");
    }

    @Override
    public void stop() {
        active.set(false);
    }

    @Override
    public String getGeneratorId() {
        return generatorId;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }
}
