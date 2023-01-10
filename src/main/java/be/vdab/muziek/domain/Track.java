package be.vdab.muziek.domain;

import jakarta.persistence.*;

import java.time.LocalTime;

@Embeddable
@Access(AccessType.FIELD)
public class Track {
    private String naam;
    private LocalTime tijd;

    protected Track() {
    }

    public String getNaam() {
        return naam;
    }

    public LocalTime getTijd() {
        return tijd;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Track track && naam.equalsIgnoreCase(track.naam);
    }

    @Override
    public int hashCode() {
        return naam.toUpperCase().hashCode();
    }
}
