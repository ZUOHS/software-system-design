package org.example.judge.thread;

import java.util.LinkedList;
import java.util.List;

public class SimpleThreadPool {
    private final List<Task> taskQueue = new LinkedList<>();
    private final List<WorkerThread> threads = new LinkedList<>();
    private final int nThreads;

    public SimpleThreadPool(int nThreads) {
        this.nThreads = nThreads;
        for (int i = 0; i < nThreads; i++) {
            WorkerThread worker = new WorkerThread(taskQueue);
            worker.start();
            threads.add(worker);
        }
    }

    public void submit(Task task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }
}