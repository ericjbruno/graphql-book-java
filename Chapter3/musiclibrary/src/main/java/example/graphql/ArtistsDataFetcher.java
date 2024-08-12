package example.graphql;

import com.ericbruno.musiclibrarydata.Artist;
import graphql.schema.*;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class ArtistsDataFetcher implements DataFetcher<List<Artist>> {

    @Override
    public List<Artist> get(DataFetchingEnvironment environment) throws Exception {
        return DataRepository.getInstance().getAllArtists();
    }
}
