package com.ericbruno.bankaccountdata;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public enum States {

    NEW_YORK("NEW_YORK"),
    NEW_JERSEY("NEW_JERSEY"),
    CALIFORNIA("CALIFORNIA"),
    unknown("unknown");

    private final String graphqlName;

    private States(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
