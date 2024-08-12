package com.ericbruno.graphql.http.client;

import com.ericbruno.musiclibrarydata.Artist;
import com.ericbruno.musiclibrarydata.QueryResolver;
import com.ericbruno.musiclibrarydata.Song;
import com.ericbruno.musiclibrarydata.Version;
import java.util.List;

/**
 * @author ebruno
 */
public class Data implements QueryResolver 
{
    private Artist artist;
    private List<Artist> artists;
    private Song song;
    private Version version;
    
    public Data() {
    }

//    public Data(Artist artist) {
//        this.artist = artist;
//    }
//
//    public Data(List<Artist> artists) {
//        this.artists = artists;
//    }
//
//    public Data(Song song) {
//        this.song = song;
//    }

    //////////////////////////////////////////////////////////////////////////

    @Override
    public Version version() throws Exception {
        return null;
    }

    @Override
    public List<Artist> artists() throws Exception {
        return this.getArtists();
    }

    @Override
    public List<Song> songsByArtist(String artistId) throws Exception {
        return this.getSongsByArtist();
    }

    @Override
    public Song song(String id) throws Exception {
        return getSong();
    }

    @Override
    public Artist artist(String id) throws Exception {
        return getArtist();
    }

    public Artist getArtist() throws Exception {
        return artist;
    }

    public Song getSong() throws Exception {
        return song;
    }
    
    public List<Song> getSongsByArtist() throws Exception {
        return artist.getSongs();
    }
    
    public List<Artist> getArtists() throws Exception {
        return artists;
    }
}
