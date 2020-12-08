package com.golovkin.concurrency;

public class Request {
    private String clientName;
    private int sum;
    private OperationType operationType;

    public Request(String clientName, int sum, OperationType operationType) {
        this.clientName = clientName;
        this.sum = sum;
        this.operationType = operationType;
    }

    public String getClientName() {
        return clientName;
    }

    public int getSum() {
        return sum;
    }


    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return "Заявка{" +
                "clientName='" + clientName + '\'' +
                ", sum=" + sum +
                ", operationType=" + operationType +
                '}';
    }
}
