package com.ericbruno.bankaccountdata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public interface AccountsByTypeQueryResolver {

    java.util.List<Account> accountsByType(@javax.validation.constraints.NotNull AccountType type, String customerId) throws Exception;

}
