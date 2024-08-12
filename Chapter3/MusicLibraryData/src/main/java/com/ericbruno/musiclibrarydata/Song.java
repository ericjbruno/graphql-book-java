package com.ericbruno.musiclibrarydata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-06-09T15:40:28-0400"
)
public class Song implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Integer length;
    private Artist artist;

    public Song() {
    }

    public Song(String id, String name, Integer length, Artist artist) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }

    public Artist getArtist() {
        return artist;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (id != null) {
            joiner.add("id: \"" + id + "\"");
        }
        if (name != null) {
            joiner.add("name: \"" + name + "\"");
        }
        if (length != null) {
            joiner.add("length: " + length);
        }
        if (artist != null) {
            joiner.add("artist: " + artist);
        }
        return joiner.toString();
    }

}
