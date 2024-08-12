package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-21T20:10:42-0400"
)
public class Account implements java.io.Serializable, Entity {

    private static final long serialVersionUID = 1L;

    @javax.validation.constraints.NotNull
    private String id;
    private Boolean active;
    private String name;
    private AccountType type;
    private Customer owner;
    private Balance availableBalance;
    private Balance totalBalance;

    public Account() {
    }

    public Account(String id, Boolean active, String name, AccountType type, Customer owner, Balance availableBalance, Balance totalBalance) {
        this.id = id;
        this.active = active;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.availableBalance = availableBalance;
        this.totalBalance = totalBalance;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
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

    public Customer getOwner() {
        return owner;
    }
    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Balance getAvailableBalance() {
        return availableBalance;
    }
    public void setAvailableBalance(Balance availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Balance getTotalBalance() {
        return totalBalance;
    }
    public void setTotalBalance(Balance totalBalance) {
        this.totalBalance = totalBalance;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (id != null) {
            joiner.add("id: \"" + id + "\"");
        }
        if (active != null) {
            joiner.add("active: " + active);
        }
        if (name != null) {
            joiner.add("name: \"" + name + "\"");
        }
        if (type != null) {
            joiner.add("type: " + type);
        }
        if (owner != null) {
            joiner.add("owner: " + owner);
        }
        if (availableBalance != null) {
            joiner.add("availableBalance: " + availableBalance);
        }
        if (totalBalance != null) {
            joiner.add("totalBalance: " + totalBalance);
        }
        return joiner.toString();
    }

}
