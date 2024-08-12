/*
 * Copyright 2024 ebruno.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.AccountInput;
import com.ericbruno.bankaccountdata.AccountType;
import com.ericbruno.bankaccountdata.Balance;
import com.ericbruno.bankaccountdata.Customer;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.LinkedHashMap;

/**
 *
 * @author ebruno
 */
@Singleton
public class AddAccountDataFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        LinkedHashMap map = environment.getArgument("account");
        
        Customer customer = DataRepository.getInstance().getCustomer(
                (String)map.get("ownerId") );
        
        Balance balance = new Balance(
                //Double.valueOf((String)map.get("balance")),
                (Double)map.get("balance"),
                DataRepository.getInstance().getTodaysDate() );

        Account account = DataRepository.getInstance().addAccount(
                AccountType.valueOf((String)map.get("type")),
                (String)map.get("name"),
                customer,
                balance );
                        

        return account.getId();
        
    }
    
}
