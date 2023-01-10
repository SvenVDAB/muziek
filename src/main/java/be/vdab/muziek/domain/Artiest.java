package be.vdab.muziek.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "artiesten")
public class Artiest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;

    protected Artiest() {
    }

    public Artiest(String naam) {
        this.naam = naam;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
