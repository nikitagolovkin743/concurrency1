package com.golovkin.concurrency;

public class BackSystem {
    private static final int START_SUM = 100000;

    private int totalSum;

    public BackSystem() {
        this.totalSum = START_SUM;
    }

    public void handleRequest(Request request, RequestHandler requestHandler) {
        OperationType operationType = request.getOperationType();
        switch (operationType) {
            case REPAY_CREDIT:
                increaseTotalSum(request, requestHandler);
                break;
            case TAKE_CREDIT:
                decreaseTotalSum(request, requestHandler);
                break;
        }
    }

    private synchronized void increaseTotalSum(Request request, RequestHandler requestHandler) {
        totalSum += request.getSum();
        System.out.printf("Бэк система: %s УСПЕШНО ВЫПОЛНЕНА. Получена от %s. Баланс банка: %d\n", request, requestHandler.getName(), totalSum);
    }

    private synchronized void decreaseTotalSum(Request request, RequestHandler requestHandler) {
        if (totalSum - request.getSum() >= 0) {
            totalSum -= request.getSum();
            System.out.printf("Бэк система: %s УСПЕШНО ВЫПОЛНЕНА. Получена от %s. Баланс банка: %d\n", request, requestHandler.getName(), totalSum);
        } else {
            System.out.printf("Бэк система: %s НЕ ВЫПОЛНЕНА. Получена от %s. Сумма больше баланса банка. Баланс банка: %d\n", request, requestHandler.getName(), totalSum);
        }
    }
}
