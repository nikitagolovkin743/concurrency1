package com.golovkin.concurrency;

public class RequestHandler {
    private static int requestHandlerCount;
    private final FrontSystem frontSystem;
    private final BackSystem backSystem;
    private final String name;
    private Thread thread;

    public RequestHandler(FrontSystem frontSystem, BackSystem backSystem) {
        requestHandlerCount++;
        name = String.format("Обработчик заявок №%d", requestHandlerCount);

        this.thread = new Thread(this::handleRequests);
        this.thread.setName(name);

        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    private void handleRequests() {
        Request request = null;
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (RequestHandler.class) {
                if (frontSystem.isAnyRequestAvailable()) {
                    request = frontSystem.dequeue();
                }
            }

            if (request != null) {
                System.out.printf("%s: Получена заявка на обработку по клиенту - %S\n", name, request.getClientName());
                backSystem.handleRequest(request, this);
                request = null;
            }
        }
    }

    public void start() {
        thread.start();
    }

    public String getName() {
        return name;
    }
}
