package com.Amiseq.ThreadPoolAssignment.Classes;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import com.Amiseq.ThreadPoolAssignment.Interfaces.DataCollect;
import com.Amiseq.ThreadPoolAssignment.Interfaces.OutputProcess;

public class ConsoleOutput implements OutputProcess {
	private static final Logger logger = Logger.getLogger(ConsoleOutput.class.getName());
    private static final Random random = new Random();
    
    private final DataCollect<String> collector;
    private final AtomicBoolean active;
    
    public ConsoleOutput(DataCollect<String> collector) {
        this.collector = collector;
        this.active = new AtomicBoolean(true);
    }
    
    @Override
    public void run() {
        logger.info("Console printer started");
        
        while (active.get()) {
            try {
                int waitTime = 500 + random.nextInt(1500);
                Thread.sleep(waitTime);
                
                if (random.nextInt(100) < 30) {
                    executePrivateWindow();
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.info("Console printer was interrupted");
                break;
            }
        }
        logger.info("Console printer stopped");
    }
    
    private void executePrivateWindow() {
        logger.fine("Print window opened");
        List<String> outputs = collector.retrieveAllData();
        
        if (!outputs.isEmpty()) {
            System.out.println("\n=== PRINT WINDOW OPENED ===");
            for (String output : outputs) {
                System.out.println("OUTPUT: " + output);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("=== PRINT WINDOW CLOSED ===\n");
            logger.info("Printed " + outputs.size() + " items");
        }
    }
    
    @Override
    public void stop() {
        active.set(false);
    }
    
    @Override
    public boolean isActive() {
        return active.get();
    }
}
