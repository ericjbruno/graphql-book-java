package com.ericbruno.bankaccountdata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-06T11:51:10-0400"
)
public interface BalanceQueryResolver {

    Balance balance(@javax.validation.constraints.NotNull String accountId) throws Exception;

}
