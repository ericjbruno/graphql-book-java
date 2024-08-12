package com.ericbruno.musiclibrarydata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-06-09T15:40:28-0400"
)
public class Artist implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private java.util.List<Song> songs;

    public Artist() {
    }

    public Artist(String id, String name, java.util.List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
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

    public java.util.List<Song> getSongs() {
        return songs;
    }
    public void setSongs(java.util.List<Song> songs) {
        this.songs = songs;
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
        if (songs != null) {
            joiner.add("songs: " + songs);
        }
        return joiner.toString();
    }

}
