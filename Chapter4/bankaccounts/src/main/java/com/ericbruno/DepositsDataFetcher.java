package com.ericbruno;

import com.ericbruno.bankaccountdata.Balance;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

/**
 * @author ebruno
 */
@Singleton
public class DepositsDataFetcher implements DataFetcher<Double> {

    @Override
    public Double get(DataFetchingEnvironment environment) throws Exception {
        String customerId = environment.getArgument("customerId");
        return DataRepository.getInstance().getTotalDeposits(customerId);
    }    
}
