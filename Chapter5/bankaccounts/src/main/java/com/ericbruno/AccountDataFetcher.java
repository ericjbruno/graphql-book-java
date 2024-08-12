package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class AccountDataFetcher implements DataFetcher<Account> {

    @Override
    public Account get(DataFetchingEnvironment environment) throws Exception {
        String accountId = environment.getArgument("accountId");
        return DataRepository.getInstance().getAccount(accountId);
    }
    
}
