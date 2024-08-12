package example.graphql;

import com.ericbruno.musiclibrarydata.Artist;
import com.ericbruno.musiclibrarydata.Song;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class ArtistDataFetcher implements DataFetcher<Artist> {

    @Override
    public Artist get(DataFetchingEnvironment environment) throws Exception {
        String artistId = environment.getArgument("id");        
        
        Artist artistRec = DataRepository.getInstance().getAllArtists()
                    .stream()
                    .filter(artist -> artist.getId().equals(artistId))
                    .findFirst()
                    .orElse(null);

        if ( artistRec != null ) {
            List<Song> songs = DataRepository.getInstance().getAllSongs()
                    .stream()
                    .filter(song -> song.getArtist().getId().equals(artistId))
                    .toList();
            artistRec.setSongs(songs);
        }
        
        return artistRec;
    }
}
