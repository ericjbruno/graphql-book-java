package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.AccountType;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class AccountsByCustomerDataFetcher implements DataFetcher<List<Account>> {

    @Override
    public List<Account> get(DataFetchingEnvironment environment) throws Exception {
        String customerId = environment.getArgument("customerId");

        return DataRepository.getInstance().getAllAccounts()
                .stream()
                .filter(account -> account.getOwner().getId().equals(customerId))
                .toList();
    }
}
