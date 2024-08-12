package example.graphql;

import com.ericbruno.musiclibrarydata.Song;
import graphql.execution.directives.QueryDirectives;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.Map;

/**
 * @author ebruno
 */
@Singleton
public class SongDataFetcher implements DataFetcher<Song>{

    @Override
    public Song get(DataFetchingEnvironment environment) throws Exception {
        String songId = environment.getArgument("id"); 
        Map<String, Object> ret = environment.getArguments();
        QueryDirectives dirs = environment.getQueryDirectives();
        
        return DataRepository.getInstance().getAllSongs()
                    .stream()
                    .filter(song -> song.getId().equals(songId))
                    .findFirst()
                    .orElse(null);

    }
}
