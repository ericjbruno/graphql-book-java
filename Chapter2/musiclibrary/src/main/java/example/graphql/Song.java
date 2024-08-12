package example.graphql;

import io.micronaut.core.annotation.Introspected;

/**
 * @author ebruno
 */
@Introspected
public class Song {
    private final String id;
    private final String name;
    private final int length;
    private final Artist artist;
    
    public Song(String id, String name, int length, Artist artist) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Artist getArtist() {
        return artist;
    }
}
