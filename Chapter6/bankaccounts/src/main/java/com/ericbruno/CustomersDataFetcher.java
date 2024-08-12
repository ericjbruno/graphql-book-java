package com.ericbruno;

import com.ericbruno.bankaccountdata.Customer;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class CustomersDataFetcher implements DataFetcher<List<Customer>>{

    @Override
    public List<Customer> get(DataFetchingEnvironment environment) throws Exception {
        return DataRepository.getInstance().getAllCustomers();
    }
    
}
