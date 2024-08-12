package example.graphql;

import io.micronaut.core.annotation.Introspected;

/**
 * @author ebruno
 */
@Introspected
public class Artist {
    private final String id;
    private final String name;
    
    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
