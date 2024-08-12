package com.ericbruno.simple.graphql.http.client;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.StringEntity;

// To parse the HttpResponse to a String
import org.apache.commons.io.IOUtils;

// To pretty format the JSON and map to Java Objects
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ebruno
 */
public class SimpleGraphqlHttpClient {

    public static void main(String[] args) throws Exception {
        String query =  "query {" +
                        "  artist(id: \"artist1\") {" +
                        "    name" +
                        "    songs {" +
                        "      id," +
                        "      name" +
                        "    }" +
                        "  }" +
                        "}";

        System.out.println( getArtists(query) );
        System.out.println( getArtistsPost(query) );
    }

    public static String graphQLToJSON(String query) {
        query = query.replace("\"", "\\\"");
        if ( ! query.startsWith("{") ) {
            query = "{\"query\": \"" + query + "\"}";
        }
        
        return query;
    }

    public static String getArtists(String query) throws Exception {
        HttpGet request = new HttpGet("http://localhost:8080/graphql");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("query", query)
                .build();
        request.setURI(uri);

        return makeRequest( request );
    }

    public static String getArtistsPost(String query) throws Exception {
        HttpPost request  = new HttpPost("http://localhost:8080/graphql");
        request.setHeader("Content-type", "application/json");
        query = graphQLToJSON(query);
        StringEntity entity = new StringEntity(query);
        request.setEntity(entity);

        return makeRequest( request );
    }
    
    private static String makeRequest(HttpRequestBase request) 
            throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute( request );

        String json = IOUtils.toString(
                response.getEntity().getContent(),
                StandardCharsets.UTF_8.name());

        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        json = mapper.readTree(json).toPrettyString();

        return json;
    }
}
