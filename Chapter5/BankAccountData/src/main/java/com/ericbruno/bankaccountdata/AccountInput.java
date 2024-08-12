package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public class AccountInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String ownerId;
    private String name;
    private AccountType type;
    private Double balance;

    public AccountInput() {
    }

    public AccountInput(String ownerId, String name, AccountType type, Double balance) {
        this.ownerId = ownerId;
        this.name = name;
        this.type = type;
        this.balance = balance;
    }

    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
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
        if (ownerId != null) {
            joiner.add("ownerId: \"" + ownerId + "\"");
        }
        if (name != null) {
            joiner.add("name: \"" + name + "\"");
        }
        if (type != null) {
            joiner.add("type: " + type);
        }
        if (balance != null) {
            joiner.add("balance: " + balance);
        }
        return joiner.toString();
    }

}
