package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.Customer;
import graphql.GraphQL;
import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Factory 
public class GraphQLFactory {

    @Bean
    @Singleton
    public GraphQL graphQL(ResourceResolver resourceResolver,
            CustomersDataFetcher customersDF,
            AccountsDataFetcher accountsDF,
            EntitiesDataFetcher entitiesDF,
            AccountsByCustomerDataFetcher accountsByCustomerDF,
            AccountsByTypeDataFetcher accountsByTypeDF,
            AccountDataFetcher accountDF,
            CustomerDataFetcher customerDF,
            BalanceDataFetcher balanceDF,
            DepositsDataFetcher depositsDF,
            AddCustomerDataFetcher addCustomerDF,
            AddAccountDataFetcher addAccountDF) 
    {
        // Make sure a GraphQL schema file exists for this project
        Optional<InputStream> graphqlSchemaFileIS = resourceResolver.getResourceAsStream("classpath:schema.graphqls"); 
        if (! graphqlSchemaFileIS.isPresent()) {
            System.out.println("No GraphQL services found, returning empty schema");
            return new GraphQL.Builder(GraphQLSchema.newSchema().build()).build();
        }

        // Load the GraphQL schema file, parse it, and register all the 
        // discovered types in the registry
        //
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        SchemaParser schemaParser = new SchemaParser(); 
        typeRegistry.merge(
                schemaParser.parse(
                        new BufferedReader(
                                new InputStreamReader(
                                        graphqlSchemaFileIS.get())))); 
        
        TypeResolver entityResolver = new TypeResolver() {
            @Override
            public GraphQLObjectType getType(TypeResolutionEnvironment env) {
                Object javaObject = env.getObject();
                if (javaObject instanceof Account) {
                    return env.getSchema().getObjectType("Account");
                } else if (javaObject instanceof Customer) {
                    return env.getSchema().getObjectType("Customer");
                } else {
                    return null;
                }
            }
        };

        // Map the schema types to the data fetchers, so that client calls
        // create (for mutations) and return (for queries) actual data
        //
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Entity", typeWriting -> typeWriting
                    .typeResolver(entityResolver))
                .type("Query", typeWiring -> typeWiring
                    .dataFetcher("customers", customersDF)
                    .dataFetcher("accounts", accountsDF)
                    .dataFetcher("entities", entitiesDF)
                    .dataFetcher("account", accountDF)
                    .dataFetcher("customer", customerDF)
                    .dataFetcher("accountsByCustomer", accountsByCustomerDF)
                    .dataFetcher("accountsByType", accountsByTypeDF)
                    .dataFetcher("balance", balanceDF)
                    .dataFetcher("totalDeposits", depositsDF))
                .type("Mutation", typeWiring -> typeWiring
                    .dataFetcher("addCustomer", addCustomerDF)
                    .dataFetcher("addAccount", addAccountDF))
                .directive("bankapp_auth", new AuthDirectiveImpl())
                .build();

        // Finally, load and return the actual GraphQL schema object model
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
