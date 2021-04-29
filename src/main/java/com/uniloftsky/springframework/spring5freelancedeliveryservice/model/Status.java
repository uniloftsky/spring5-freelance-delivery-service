package com.uniloftsky.springframework.spring5freelancedeliveryservice.model;

public enum Status {

    ACTIVE("Активно"),
    IN_PROCESS("Виконується"),
    READY("Виконано"),
    BLOCKED("Заблоковано");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
