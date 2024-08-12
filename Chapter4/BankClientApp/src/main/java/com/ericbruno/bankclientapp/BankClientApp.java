package com.ericbruno.bankclientapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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
        String data = graphqlQueryPost(
                "http://localhost:8080/graphql",
                query, variables);
        System.out.println("JSON response:");
        System.out.println(data + "\n");
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
}
