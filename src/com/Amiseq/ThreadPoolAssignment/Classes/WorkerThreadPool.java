package com.Amiseq.ThreadPoolAssignment.Classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Amiseq.ThreadPoolAssignment.Interfaces.ThreadPool;

public class WorkerThreadPool implements ThreadPool{

private static final Logger logger = Logger.getLogger(WorkerThreadPool.class.getName());
    
    private final Queue<Runnable> taskQueue;
    private final List<WorkerThread> threads;
    private final AtomicBoolean isRunning;
    private final ReentrantLock queueLock;
    private final Condition taskAvailable;
    private final int maxQueueSize;
    
    public WorkerThreadPool(int threadCount, int maxQueueSize) {
        this.taskQueue = new LinkedList<>();
        this.threads = new ArrayList<>(threadCount);
        this.isRunning = new AtomicBoolean(true);
        this.queueLock = new ReentrantLock();
        this.taskAvailable = queueLock.newCondition();
        this.maxQueueSize = maxQueueSize;
        
        initializeWorkers(threadCount);
    }
    
    private void initializeWorkers(int count) {
        for (int i = 0; i < count; i++) {
            WorkerThread worker = new WorkerThread("PoolWorker-" + i);
            threads.add(worker);
            worker.start();
        }
        logger.info("Initialized thread pool with " + count + " workers");
    }
    
    @Override
    public boolean submitTask(Runnable task) {
        if (!isRunning.get()) {
            return false;
        }
        
        queueLock.lock();
        try {
            if (taskQueue.size() >= maxQueueSize) {
                logger.warning("Task queue is full, rejecting task");
                return false;
            }
            taskQueue.offer(task);
            taskAvailable.signal();
            return true;
        } finally {
            queueLock.unlock();
        }
    }
    
    @Override
    public void shutdown() {
        isRunning.set(false);
        queueLock.lock();
        try {
            taskAvailable.signalAll();
        } finally {
            queueLock.unlock();
        }
        
        for (WorkerThread worker : threads) {
            try {
                worker.join(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Interrupted while shutting down worker", e);
            }
        }
        logger.info("Thread pool shutdown completed");
    }
    
    @Override
    public boolean isShutdown() {
        return !isRunning.get();
    }
    
    private class WorkerThread extends Thread {
        public WorkerThread(String name) {
            super(name);
        }
        
        @Override
        public void run() {
            while (isRunning.get() || !taskQueue.isEmpty()) {
                Runnable task = getNextTask();
                if (task != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Task execution failed", e);
                    }
                }
            }
            logger.fine(getName() + " terminated");
        }
        
        private Runnable getNextTask() {
            queueLock.lock();
            try {
                while (taskQueue.isEmpty() && isRunning.get()) {
                    try {
                        taskAvailable.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }
                return taskQueue.poll();
            } finally {
                queueLock.unlock();
            }
        }
    }

}
