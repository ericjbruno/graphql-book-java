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

import com.ericbruno.bankaccountdata.Balance;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 *
 * @author ebruno
 */

public class AuthDirectiveImpl implements SchemaDirectiveWiring {
    String userRole = "owner";

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher originalFetcher = 
                environment.getCodeRegistry().getDataFetcher(parentType, field);

        // Define a new DataFetcher that checks the user auth role 
        // before returning the account balance
        //
        DataFetcher authDataFetcher = (DataFetcher)
                (DataFetchingEnvironment dataFetchingEnvironment) -> {
            if (userRole.equalsIgnoreCase("owner") ) {
                return originalFetcher.get(dataFetchingEnvironment);
            }
            else {
                Balance balance = new Balance();
                balance.setAmount(0.00);
                balance.setAsOf("NA");
                return balance;
            }
        };

        // Need to wire in a new Fetcher so that the new one always gets
        // called and checks for authentication
        //
        FieldCoordinates coordinates = FieldCoordinates.coordinates(
                parentType, field);
        environment.getCodeRegistry().dataFetcher(
                coordinates, authDataFetcher);
        return field;
    }
}
