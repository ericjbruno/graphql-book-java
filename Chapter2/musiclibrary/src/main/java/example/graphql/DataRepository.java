package example.graphql;

import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ebruno
 */
@Singleton
public class DataRepository {
    private static final List<Song> songs = Arrays.asList( 
            new Song(   "song1", 
                        "Here Comes the Sun", 
                        186, 
                        new Artist("artist1", "The Beatles")),
            new Song(   "song2", 
                        "Big Yellow Taxi", 
                        136, 
                        new Artist("artist2", "Joni Mitchell"))
    );
    
    List<Song> getAllSongs() {
        return songs;
    }
    
    List<Artist> getAllArtists() {
        return songs
                .stream()
                .map(Song::getArtist)
                .collect(Collectors.toList());
    }
}
