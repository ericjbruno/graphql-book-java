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

import com.ericbruno.bankaccountdata.Address;
import com.ericbruno.bankaccountdata.Customer;
import com.ericbruno.bankaccountdata.States;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

/**
 * @author ebruno
 */
@Singleton
public class AddCustomerDataFetcher implements DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        String lastName = environment.getArgument("lastName");
        String firstName = environment.getArgument("firstName");
        String email = environment.getArgument("email");
        String street = environment.getArgument("street");
        String state = environment.getArgument("state");
        String city = environment.getArgument("city");
        String zip = environment.getArgument("zip");
        String phone = environment.getArgument("phone");        
        String ssn = environment.getArgument("ssn");
       
        States stateVal = States.valueOf(state);
        Address address = new Address(street, city, stateVal, zip);

        Customer customer = DataRepository.getInstance().addCustomer(
                Boolean.TRUE, 
                firstName,
                lastName, 
                email, 
                address, 
                phone, 
                ssn);

        return customer.getId();
    }
    
}

