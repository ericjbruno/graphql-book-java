package com.ericbruno.bankaccountdata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public interface MutationResolver {

    String addCustomer(String lastName, String firstName, String email, String street, String city, States state, String zip, String phone, String ssn) throws Exception;

    String addAccount(AccountInput account) throws Exception;

}
