package com.golovkin.concurrency;

public class App {
    private static final int REQUEST_HANDLER_COUNT = 2;
    private static final int CLIENT_COUNT = 4;

    public static void main(String[] args) {
        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        for (int i = 0; i < REQUEST_HANDLER_COUNT; i++) {
            RequestHandler requestHandler = new RequestHandler(frontSystem, backSystem);
            requestHandler.start();
        }

        for (int i = 0; i < CLIENT_COUNT; i++) {
            Client client = Client.createRandomClient();
            client.applyRequestTo(frontSystem);
        }
    }
}
