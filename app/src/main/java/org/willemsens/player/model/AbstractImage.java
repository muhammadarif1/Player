package org.willemsens.player.model;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public abstract class AbstractImage {
    @Key
    @Generated
    Long id;

    @Column(nullable = false)
    ImageSource source;

    @Column(nullable = false)
    String url;

    @Column(nullable = false)
    byte[] imageData;
}
