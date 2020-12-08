package com.golovkin.concurrency;

import java.util.ArrayDeque;

public class FrontSystem {
    private ArrayDeque<Request> queue;

    public FrontSystem() {
        queue = new ArrayDeque<>(2);
    }

    public synchronized void enqueue(Request request) throws InterruptedException {
        while (queue.size() >= 2)
            wait();

        queue.add(request);
    }

    public boolean isAnyRequestAvailable() {
        return !queue.isEmpty();
    }

    public synchronized Request dequeue() {
        Request request = queue.remove();
        notifyAll();

        return request;
    }
}
