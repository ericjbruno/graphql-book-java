package com.ericbruno;

import com.ericbruno.bankaccountdata.Balance;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

/**
 * @author ebruno
 */
@Singleton
public class BalanceDataFetcher implements DataFetcher<Balance> {

    @Override
    public Balance get(DataFetchingEnvironment environment) throws Exception {
        String accountId = environment.getArgument("accountId");
        return DataRepository.getInstance().getBalance(accountId);
    }    
}
