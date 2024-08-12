package com.ericbruno.bankaccountdata;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-01T13:10:55-0400"
)
public enum States {

    NEW_YORK("NEW_YORK"),
    NEW_JERSEY("NEW_JERSEY"),
    CALIFORNIA("CALIFORNIA");

    private final String graphqlName;

    private States(String graphqlName) {
        this.graphqlName = graphqlName;
    }

    @Override
    public String toString() {
        return this.graphqlName;
    }

}
