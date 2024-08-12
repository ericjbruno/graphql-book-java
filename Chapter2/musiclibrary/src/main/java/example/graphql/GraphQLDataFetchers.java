package example.graphql;

import graphql.schema.DataFetcher;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class GraphQLDataFetchers {
    private final DataRepository repo;

    public GraphQLDataFetchers(DataRepository repo) { 
        this.repo = repo;
    }

    public DataFetcher<List<Artist>> getArtistsFetcher() {
        return dataFetchingEnv -> { 
            return repo.getAllArtists();
        };
    }

    public DataFetcher<Song> getSongByIdDataFetcher() {
        return dataFetchingEnvironment -> { 
            String songId = dataFetchingEnvironment.getArgument("id"); 
            return repo.getAllSongs()
                    .stream()
                    .filter(song -> song.getId().equals(songId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Song>> getSongsByArtistFetcher() {
        return dataFetchingEnv -> {
            String artistId = dataFetchingEnv.getArgument("artistId");
            return repo.getAllSongs()
                    .stream()
                    .filter(song -> song.getArtist().getId().equals(artistId))
                    .toList();
        };
    }
}
