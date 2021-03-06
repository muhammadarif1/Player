package org.willemsens.player.filescanning;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * A background service that checks all entries of the music DB to see if they still exist,
 * purging all non-existing entries from the DB if necessary.
 */
public class PurgingService extends IntentService {
    public PurgingService() {
        super(PurgingService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // TODO
        // The intent should not have any parameters in it (is this good practice)?
        // AbstractArtist per AbstractArtist should be iterated over or AbstractAlbum per AbstractAlbum?
        // --> THEN song per song.
        // Afterwards, each album should be checked to see if there is still at least one song,
        // if not -> purge album
        // Afterwards, each artist should be checked to see if there is still at least one album
        // OR song, if not -> purge artist
    }
}
