package org.example.judge.thread;

import java.util.List;

class WorkerThread extends Thread {
    private final List<Task> taskQueue;

    public WorkerThread(List<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            Task task = null;
            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                task = taskQueue.remove(0);
            }
            try {
                task.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}