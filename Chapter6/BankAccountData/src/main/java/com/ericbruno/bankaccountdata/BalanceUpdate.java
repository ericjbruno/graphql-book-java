package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-21T20:10:42-0400"
)
public class BalanceUpdate implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Double balance;

    public BalanceUpdate() {
    }

    public BalanceUpdate(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (balance != null) {
            joiner.add("balance: " + balance);
        }
        return joiner.toString();
    }

}
