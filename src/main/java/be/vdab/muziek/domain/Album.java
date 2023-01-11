package be.vdab.muziek.domain;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
@NamedEntityGraph(name = Album.MET_ARTIEST_EN_LABEL,
attributeNodes = {
        @NamedAttributeNode("artiest"),
        @NamedAttributeNode("label")
}
)
public class Album {
    public static final String MET_ARTIEST_EN_LABEL = "Album.metArtiestEnLabel";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artiestId")
    private Artiest artiest;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "labelId")
    private Label label;
    private  String naam;
    private int jaar;
    private long barcode;

    @Range(min=0, max=10)
    private int score;

    @ElementCollection
    @CollectionTable(name = "tracks",
            joinColumns = @JoinColumn(name = "albumId"))
    private Set<Track> tracks;

    protected Album() {
    }

    public Album(Artiest artiest, Label label, String naam, int jaar, long barcode, int score) {
        this.artiest = artiest;
        this.label = label;
        this.naam = naam;
        this.jaar = jaar;
        this.barcode = barcode;
        this.score = score;
        this.tracks = new LinkedHashSet<>();
    }

    public long getId() {
        return id;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public Label getLabel() {
        return label;
    }

    public String getNaam() {
        return naam;
    }

    public int getJaar() {
        return jaar;
    }

    public long getBarcode() {
        return barcode;
    }

    public int getScore() {
        return score;
    }

    public Set<Track> getTracks() {
        return Collections.unmodifiableSet(tracks);
    }

    public LocalTime getTotalTime() {
        var tijd = LocalTime.of(0, 0, 0);
        for(var track : tracks) {
            var tijdTemp = track.getTijd();
            tijd = tijd
                    .plusHours(tijdTemp.getHour())
                    .plusMinutes(tijdTemp.getMinute())
                    .plusSeconds(tijdTemp.getSecond());
        }
        return tijd;
    }

    public void setScore(int score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException();
        }
        this.score = score;
    }
}
