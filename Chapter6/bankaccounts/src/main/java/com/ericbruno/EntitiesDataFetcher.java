package com.ericbruno;

import com.ericbruno.bankaccountdata.Entity;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * @author ebruno
 */
@Singleton
public class EntitiesDataFetcher implements DataFetcher<List<Entity>>{

    @Override
    public List<Entity> get(DataFetchingEnvironment environment) throws Exception {
        return DataRepository.getInstance().getAllEntities();
    }
}
