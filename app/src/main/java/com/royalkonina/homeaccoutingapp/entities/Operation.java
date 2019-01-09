package com.royalkonina.homeaccoutingapp.entities;

import java.util.Date;

public class Operation {

    private long id;
    private Date date;
    private OperationType operationType;
    private long value; // always positive
    private long sourceAccountId;
    private long destinationAccountId;
    private String description;

    public Operation(long id,
                     Date date,
                     OperationType operationType,
                     long value,
                     long sourceAccountId,
                     long destinationAccountId,
                     String description) {
        this.id = id;
        this.date = date;
        this.operationType = operationType;
        this.value = value;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public long getValue() {
        return value;
    }

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setDestinationAccountId(long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
