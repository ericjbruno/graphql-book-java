package com.ericbruno.bankaccountdata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-21T20:10:42-0400"
)
public interface MutationResolver {

    String addCustomer(String lastName, String firstName, String email, String street, String city, States state, String zip, String phone, String ssn) throws Exception;

    String addAccount(AccountInput account) throws Exception;

    Boolean addTransaction(@javax.validation.constraints.NotNull String accountId, Double amount) throws Exception;

}
