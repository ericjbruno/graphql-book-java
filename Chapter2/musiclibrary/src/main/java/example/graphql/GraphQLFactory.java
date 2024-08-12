package example.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
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
                           GraphQLDataFetchers graphQLDataFetchers) {
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

        // Map the schema types to the data fetchers, so that client calls
        // create (for mutations) and return (for queries) actual data
        //
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring() 
                .type(newTypeWiring("Query")
                        .dataFetcher("artists", graphQLDataFetchers.getArtistsFetcher())
                        .dataFetcher("songById", graphQLDataFetchers.getSongByIdDataFetcher())
                        .dataFetcher("songsByArtist", graphQLDataFetchers.getSongsByArtistFetcher())) 
                .build();

        // Finally, load and return the actual GraphQL schema object model
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring); 
        return GraphQL.newGraphQL(graphQLSchema).build(); 
    }
}
