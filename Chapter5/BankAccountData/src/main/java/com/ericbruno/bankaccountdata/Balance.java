package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public class Balance implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Double amount;
    private String asOf;

    public Balance() {
    }

    public Balance(Double amount, String asOf) {
        this.amount = amount;
        this.asOf = asOf;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAsOf() {
        return asOf;
    }
    public void setAsOf(String asOf) {
        this.asOf = asOf;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (amount != null) {
            joiner.add("amount: " + amount);
        }
        if (asOf != null) {
            joiner.add("asOf: \"" + asOf + "\"");
        }
        return joiner.toString();
    }

}
