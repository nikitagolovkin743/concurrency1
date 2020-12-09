package com.golovkin.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestHandler {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static int requestHandlerCount;
    private final FrontSystem frontSystem;
    private final BackSystem backSystem;
    private final String name;

    public RequestHandler(FrontSystem frontSystem, BackSystem backSystem) {
        requestHandlerCount++;
        name = String.format("Обработчик заявок №%d", requestHandlerCount);

        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    private void handleRequests() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.currentThread().setName(name);
                Request request = frontSystem.dequeue();
                System.out.printf("%s: Получена заявка на обработку по клиенту - %S\n", name, request.getClientName());
                backSystem.handleRequest(request, this);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void start() {
        executorService.submit(this::handleRequests);
    }

    public String getName() {
        return name;
    }
}
