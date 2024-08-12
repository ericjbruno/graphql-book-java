package com.ericbruno.bankaccountdata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-21T20:10:42-0400"
)
public interface QueryResolver {

    java.util.List<Customer> customers() throws Exception;

    Customer customer(@javax.validation.constraints.NotNull String customerId) throws Exception;

    java.util.List<Account> accounts() throws Exception;

    Account account(@javax.validation.constraints.NotNull String accountId) throws Exception;

    java.util.List<Entity> entities() throws Exception;

    java.util.List<Account> accountsByCustomer(@javax.validation.constraints.NotNull String customerId) throws Exception;

    java.util.List<Account> accountsByType(@javax.validation.constraints.NotNull AccountType type, String customerId) throws Exception;

    Balance balance(@javax.validation.constraints.NotNull String accountId) throws Exception;

    Double totalDeposits(String customerId) throws Exception;

}
