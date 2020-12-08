package com.golovkin.concurrency;

import java.util.Random;

public class Client {
    private static final Random random = new Random();
    private static int clientCount;

    private Thread thread;
    private Request request;
    private String name;

    public Client(OperationType operationType, int sum) {
        clientCount++;

        this.name = String.format("Клиент №%d", clientCount);
        this.request = new Request(name, sum, operationType);
    }

    public static Client createRandomClient() {
        OperationType operationType = random.nextBoolean() ? OperationType.REPAY_CREDIT : OperationType.TAKE_CREDIT;
        int sum = (int) (15 * Math.random()) * 10000;

        return new Client(operationType, sum);
    }

    public void applyRequestTo(FrontSystem frontSystem) {
        if (thread == null) {
            thread = new Thread(getRunnable(frontSystem));
            thread.setName(name);
        }

        thread.start();
        System.out.printf("%s: %s отправлена в банк\n", name, request);
    }

    private Runnable getRunnable(FrontSystem frontSystem) {
        return () -> {
            try {
                frontSystem.enqueue(request);
            } catch (InterruptedException e) {
                return;
            }
        };
    }
}
