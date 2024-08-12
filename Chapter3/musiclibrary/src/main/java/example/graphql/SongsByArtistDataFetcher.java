package example.graphql;

import com.ericbruno.musiclibrarydata.Song;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class SongsByArtistDataFetcher implements DataFetcher<List<Song>>{

    @Override
    public List<Song> get(DataFetchingEnvironment environment) throws Exception {
        String artistId = environment.getArgument("artistId");        
        return DataRepository.getInstance().getAllSongs()
                    .stream()
                    .filter(song -> song.getArtist().getId().equals(artistId))
                    .toList();

    }
    
}
