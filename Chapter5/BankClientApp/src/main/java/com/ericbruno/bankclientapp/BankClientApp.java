package com.ericbruno.bankclientapp;

import com.ericbruno.bankaccountdata.AccountInput;
import com.ericbruno.bankaccountdata.AccountType;
import com.ericbruno.bankaccountdata.States;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author ebruno
 */
public class BankClientApp {
    static String query = 
            "query ($id:ID!, $showBalance: Boolean = true) {" +
            "  account(accountId: $id) {" +
            "    id" +
            "    name" +
            "    availableBalance @include(if: $showBalance) {" +
            "       amount" +
            "    }" +
            "  }" +
            "}";
    
    static String variables = 
            "\"variables\": { \"id\": 2, \"showBalance\": true }";
  
    static String query2 = 
            "{\"query\": \"query ($id:ID!, $showBalance: Boolean = true) {" +
            "  account(accountId: $id) {" +
            "    id" +
            "    name" +
            "    availableBalance @include(if: $showBalance) {" +
            "       amount" +
            "    }" +
            "  }" +
            "}\"," +
            "\"variables\": { \"id\": 2, \"showBalance\": true }}";

    public static void main(String[] args) {
//        String data = graphqlQueryPost(
//                "http://localhost:8080/graphql",
//                query, variables);
//        System.out.println("JSON response:");
//        System.out.println(data + "\n");
        String query =  "query {" +
                        "  customers {" +
                        "    id" +
                        "  }" +
                        "}";
                
        String data = graphqlQueryPost(
                "http://localhost:8080/graphql",
                query);
        
        String id = addCustomer("John",
    				"Doe",
    				"john.doe@gmail.com",
    				"123 Main Street",
    				"Anywhere",
    				States.NEW_JERSEY,
    				"11111",
      				"616-555-1515",
    				"123-45-6789");
        System.out.println("addCustomer mutation response:");
        System.out.println(id + "\n");
        
        AccountInput input = new AccountInput(
            id, "NewAccount", AccountType.BASIC_CHECKING, 1.23D);
        
        id = addAccount(input);
        System.out.println("addAcccount mutation response:");
        System.out.println(id + "\n");
    }
    
    public static String addCustomer(String firstName,
                                    String lastName,
                                    String email,
                                    String street,
                                    String city,
                                    States state,
                                    String zip,
                                    String phone,
                                    String ssn) {
        String mutation = 
                "mutation {" +
                "  addCustomer(firstName: \"" + firstName + "\"," +
                "  lastName: \"" + lastName + "\"," +
                "  email: \"" + email + "\"," +
                "  street: \""+ street + "\"," +
                "  city: \"" +city + "\"," +
                "  state: " + state.toString() + "," +
                "  zip: \"" + zip + "\"," +
                "  phone: \"" + phone + "\"," +
                "  ssn: \"" + ssn + "\"" +
                "  )" +
                "}";
        try {
            Data data = callGraphQLQuery(
                    "http://localhost:8080/graphql",
                    mutation);

            return data.getAddCustomer();
        }
        catch ( Exception e ) {
            return null;
        }
    }

    public static String addAccount(AccountInput accountInput) {
        String mutation = 
                "mutation newAccount($input: AccountInput) {" +
                "  addAccount(account: $input) " +
                "}";
        
        String input =  
                "\"variables\": {" +
                "  \"input\": { " +
                "    \"ownerId\": \""+ accountInput.getOwnerId() +"\"," +
                "    \"name\": \"" + accountInput.getName() + "\"," +
                "    \"type\": \""+ accountInput.getType() + "\"," +
                "    \"balance\": " + accountInput.getBalance() +
                "  } "+
                "}";

        try {
            Data data = callGraphQLQuery(
                    "http://localhost:8080/graphql",
                    mutation,
                    input);
            return data.getAddAccount();
        }
        catch ( Exception e ) {
            return null;
        }
    }
    
    public static Data callGraphQLQuery(String url, String query) {
        return callGraphQLQuery(url, query, null);
//        try {
//            String data = graphqlQueryPost(url, query);
//            System.out.println("JSON result:" + data);
//            ObjectMapper objectMapper = new ObjectMapper();
//            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            Response response = objectMapper.readValue(data, Response.class);
//            return response.getData();
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public static Data callGraphQLQuery(String url, String query, String vars) {
        try {
            String data = graphqlQueryPost(url, query, vars);
            System.out.println("JSON result:" + data);
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Response response = objectMapper.readValue(data, Response.class);
            return response.getData();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public static String graphqlQuery(String url, String query) {
        String data = "";
        try {
            HttpGet request = new HttpGet( url );
            URI uri = new URIBuilder( request.getURI() )
                    .addParameter( "query", query )
                    .build();
            request.setURI( uri );

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute( request );

            data = IOUtils.toString(
                            response.getEntity().getContent(), 
                            StandardCharsets.UTF_8.name());

            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readTree(data).toPrettyString();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return data;
    }
    
    public static String graphQLToJSON(String query) {
        query = query.replace("\"", "\\\"");
        query = query.replace("\n", "");
        if ( ! query.startsWith("{") ) {
            query = "{\"query\": \"" + query + "\"}";
        }
        
        return query;
    }

    public static String graphqlQueryPost(  String url, 
                                            String query) {
        return graphqlQueryPost( url, query, null);
    }

    public static String graphqlQueryPost(  String url, 
                                            String query,
                                            String vars) {
        String data = "";
        try {
            HttpPost request = new HttpPost( url );
            request.setHeader("Content-type", "application/json");
            if ( vars != null ) {
                query = query + "\"," + vars + "}";
            }
            else {
                query = query.replace("\"", "\\\"");
            }
            query = query.replace("\n", "");
            if ( ! query.startsWith("{") ) {
                query = "{\"query\": \"" + query + "\"}";
            }
            System.out.println(query);
            StringEntity entity = new StringEntity(query);
            request.setEntity(entity);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute( request );

            data = IOUtils.toString(
                            response.getEntity().getContent(), 
                            StandardCharsets.UTF_8.name());

            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readTree(data).toPrettyString();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return data;
    }

    public static String graphqlMutationPost(   String url, 
                                                String query) {
        return graphqlMutationPost(url, query, null);
    }

    public static String graphqlMutationPost(   String url, 
                                                String query,
                                                String input) {
        String data = "";
        try {
            HttpPost request = new HttpPost( url );
            request.setHeader("Content-type", "application/json");
            query = query.replace("\n", "");
            if ( input != null ) {
                query = query + "\"," + input + "}";
            }
            else {
                query = query.replace("\"", "\\\"");
            }
            if ( ! query.startsWith("{") ) {
                query = "{\"query\": \"" + query + "\"}";
            }
            System.out.println(query);
            StringEntity entity = new StringEntity(query);
            request.setEntity(entity);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute( request );

            data = IOUtils.toString(
                            response.getEntity().getContent(), 
                            StandardCharsets.UTF_8.name());

            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readTree(data).toPrettyString();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return data;
    }
}
