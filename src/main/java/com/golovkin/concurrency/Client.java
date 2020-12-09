package com.golovkin.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final Random random = new Random();
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static int clientCount;

    private final Request request;
    private final String name;
    private boolean isRequestApplied;

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
        if (isRequestApplied) {
            throw new IllegalStateException("Заявка уже была подана!");
        } else {
            Runnable runnable = getRunnable(frontSystem);
            executorService.submit(runnable);
            isRequestApplied = true;

            System.out.printf("%s: %s отправлена в банк\n", name, request);
        }
    }

    private Runnable getRunnable(FrontSystem frontSystem) {
        return () -> {
            try {
                Thread.currentThread().setName(name);
                frontSystem.enqueue(request);
            } catch (InterruptedException e) {
                return;
            }
        };
    }
}
