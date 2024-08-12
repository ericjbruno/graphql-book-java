package example.graphql;

import com.ericbruno.musiclibrarydata.Artist;
import com.ericbruno.musiclibrarydata.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ebruno
 */
public class DataRepository {
    private static DataRepository _INSTANCE = new DataRepository();
    public static DataRepository getInstance() { return _INSTANCE; }
    
    private List<Song> songs = new ArrayList<>();

    private DataRepository() {
        Artist beatles = 
                new Artist("artist1", "The Beatles", new ArrayList<Song>());

        Artist joni = 
                new Artist("artist2", "Joni Mitchell", new ArrayList<Song>());

        Song sun = new Song("song1",
                "Here Comes the Sun",
                186,
                beatles);

        Song taxi = new Song("song2",
                "Big Yellow Taxi",
                136,
                joni);

        beatles.getSongs().add(sun);
        joni.getSongs().add(taxi);
        
        songs.add(sun);
        songs.add(taxi);
    }

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
