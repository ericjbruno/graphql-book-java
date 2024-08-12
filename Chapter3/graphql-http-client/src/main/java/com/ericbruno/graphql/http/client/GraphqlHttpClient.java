package com.ericbruno.graphql.http.client;

import com.ericbruno.musiclibrarydata.Artist;
import com.ericbruno.musiclibrarydata.Song;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

// To parse the HttpResponse to a String
import org.apache.commons.io.IOUtils;

// To pretty format the JSON and map to Java Objects
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * @author ebruno
 */
public class GraphqlHttpClient {
    //private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        String data = graphqlQueryPost(
                "http://localhost:8080/graphql",
                "query {artists{name}}" );
        System.out.println("JSON response:");
        System.out.println(data + "\n");

        Data dataObj = callGraphQLQuery(
                "http://localhost:8080/graphql",
                "query {artists{name}}" );
        System.out.println("Deserialized Data objects:");
        for ( Artist artist: dataObj.artists() ) {
            System.out.println("'"+artist.getName()+"'");
        }
        
        System.out.println("\n------------------\n");

        String artistQuery = 
                    "query {" +
                    "  artist(id: \"artist1\") {" +
                    "    name" +
                    "    songs {" +
                    "      id," +
                    "      name" +
                    "    }" +
                    "  }" +
                    "}";
        System.out.println(artistQuery);

        data = graphqlQueryPost(
                "http://localhost:8080/graphql",
                artistQuery );
        System.out.println("\nJSON response:\n" + data);
        
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = objectMapper.readValue(data, Response.class);
        
        Artist artist = response.getData().getArtist();
        System.out.println("Songs by " + artist.getName() + ":");
        for ( Song song: artist.getSongs()) {
            System.out.println("  '" + song.getName() + ":");
        }

        data = graphqlQueryPost(
                "http://localhost:8080/graphql",
                "query {\n"
                + "  song(id:\"song1\") {\n"
                + "    id\n"
                + "    name\n"
                + "  }\n"
                + "}");
        System.out.println("\nJSON response:\n" + data);
        
        System.out.println("Done");
    }
    
    public static Data callGraphQLQuery(String url, String query) {
        try {
            String data = graphqlQueryPost(url, query);
            ObjectMapper objectMapper = new ObjectMapper();
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

    public static String graphqlQueryPost(String url, String query) {
        String data = "";
        try {
            HttpPost request = new HttpPost( url );
            request.setHeader("Content-type", "application/json");
            query = graphQLToJSON(query);
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
