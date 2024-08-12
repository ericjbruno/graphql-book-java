package com.ericbruno;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ebruno
 */
public class Transaction {
    private String id;
    TransactionType transactionType;
    double amount;
    String accountId;
    Date date;
    Transaction nextTransaction = null; // for this account

    public Transaction() {
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
