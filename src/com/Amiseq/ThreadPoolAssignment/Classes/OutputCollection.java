package com.Amiseq.ThreadPoolAssignment.Classes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.Amiseq.ThreadPoolAssignment.Interfaces.DataCollect;

public class OutputCollection implements DataCollect<String> {

    private static final Logger logger = Logger.getLogger(OutputCollection.class.getName());

    private final Deque<String> outputStack;
    private final ReentrantLock stackLock;

    public OutputCollection() {
        this.outputStack = new ArrayDeque<>();
        this.stackLock = new ReentrantLock();
    }

    @Override
    public void addData(String data) {
        stackLock.lock();
        try {
            outputStack.push(data);
            logger.fine("Added to stack: " + data);
        } finally {
            stackLock.unlock();
        }
    }

    @Override
    public List<String> retrieveAllData() {
        stackLock.lock();
        try {
            List<String> outputs = new ArrayList<>();
            while (!outputStack.isEmpty()) {
                outputs.add(outputStack.pop());
            }
            return outputs;
        } finally {
            stackLock.unlock();
        }
    }

    @Override
    public boolean hasData() {
        stackLock.lock();
        try {
            return !outputStack.isEmpty();
        } finally {
            stackLock.unlock();
        }
    }

    public void addOutput(String output) {
        addData(output);
    }

    public List<String> retrieveAllOutputs() {
        return retrieveAllData();
    }
}
