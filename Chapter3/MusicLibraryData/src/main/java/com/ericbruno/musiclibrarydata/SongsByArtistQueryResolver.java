package com.ericbruno.musiclibrarydata;


@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-06-09T15:40:28-0400"
)
public interface SongsByArtistQueryResolver {

    java.util.List<Song> songsByArtist(String artistId) throws Exception;

}
