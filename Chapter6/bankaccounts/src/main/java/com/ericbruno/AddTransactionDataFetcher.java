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

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

/**
 * @author ebruno
 */
@Singleton
public class AddTransactionDataFetcher implements DataFetcher<Boolean> {

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        String accountId = environment.getArgument("accountId");
        Double amount = environment.getArgument("amount");
        TransactionType type = TransactionType.CREDIT;
        if ( amount < 0.00D ) {
            type = TransactionType.DEBIT;
        }
        
        Transaction transaction = DataRepository.getInstance()
                .createTransaction(accountId, type, Math.abs(amount));
        
        if ( transaction == null ) {
            return false;
        }

        System.out.println("New transaction");
        return true;
    }
    
}
