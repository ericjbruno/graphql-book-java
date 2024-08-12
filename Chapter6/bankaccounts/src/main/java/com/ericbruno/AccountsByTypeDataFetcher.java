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
public class AccountsByTypeDataFetcher implements DataFetcher<List<Account>>{

    @Override
    public List<Account> get(DataFetchingEnvironment environment) throws Exception {
        String type = environment.getArgument("type");        
        String customerId = environment.getArgument("customerId");
        
        AccountType accountType = AccountType.valueOf(type);

        List<Account> list = 
                DataRepository.getInstance().getAllAccounts()
                    .stream()
                    .filter(account -> account.getType().equals(accountType))
                    .toList();

        // Only account type is required. Customer ID is optional
        //
        if ( customerId != null ) {
            list.stream()
                .filter(account -> account.getOwner().getId().equals(customerId))
                .toList();
        }
        return list;
    }
}
