package be.vdab.muziek.repositories;

import be.vdab.muziek.domain.Album;
import be.vdab.muziek.domain.Artiest;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArtiestRepository {
    private final EntityManager manager;

    public ArtiestRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Optional<Artiest> findById(long id) {
        return Optional.ofNullable(manager.find(Artiest.class, id));
    }
}
