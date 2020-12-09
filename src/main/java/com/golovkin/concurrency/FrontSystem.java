package com.golovkin.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class FrontSystem {
    public static final int MAX_QUEUE_CAPACITY = 2;
    private BlockingQueue<Request> queue;

    public FrontSystem() {
        queue = new LinkedBlockingDeque<>(MAX_QUEUE_CAPACITY);
    }

    public void enqueue(Request request) throws InterruptedException {
        queue.put(request);
    }

    public Request dequeue() throws InterruptedException {
        return queue.take();
    }
}
