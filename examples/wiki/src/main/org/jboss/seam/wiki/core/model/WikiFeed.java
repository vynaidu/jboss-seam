/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.seam.wiki.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Christian Bauer
 */
@Entity
@DiscriminatorValue("INTERNAL")
public class WikiFeed extends Feed implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECTORY_ID", nullable = true, updatable = false, unique = true)
    @org.hibernate.annotations.ForeignKey(name = "FK_FEED_WIKI_DIRECTORY_ID")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private WikiDirectory directory;

    public WikiFeed() {
        super();
    }

    public WikiDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(WikiDirectory directory) {
        this.directory = directory;
    }

    public int getReadAccessLevel() {
        return getDirectory().getReadAccessLevel();
    }
}
