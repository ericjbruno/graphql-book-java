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
public class AccountsDataFetcher implements DataFetcher<List<Account>> {

    @Override
    public List<Account> get(DataFetchingEnvironment environment) throws Exception {
        return DataRepository.getInstance().getAllAccounts();
    }
    
}
