package be.vdab.muziek.repositories;

import be.vdab.muziek.domain.Album;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlbumRepository {
    private final EntityManager manager;

    public AlbumRepository(EntityManager manager) {
        this.manager = manager;
    }

/*    public void create(Album album) {
        manager.persist(album);
    }*/

    public List<Album> findAll() {
        return manager
                .createNamedQuery("Album.findAll", Album.class)
                .setHint("jakarta.persistence.loadgraph", manager.createEntityGraph(Album.MET_ARTIEST_EN_LABEL))
                .getResultList();

    }

    public List<Album> findByJaar(int jaar) {
        return manager
                .createNamedQuery("Album.findByJaar",
                        Album.class)
                .setHint("jakarta.persistence.loadgraph", manager.createEntityGraph(Album.MET_ARTIEST_EN_LABEL))
                .setParameter("jaar", jaar)
                .getResultList();
    }

    public List<Album> findByArtiestId(long artiestId) {
        return manager
                .createNamedQuery("Album.findByArtiestId",
                        Album.class)
                .setHint("jakarta.persistence.loadgraph", manager.createEntityGraph(Album.MET_ARTIEST_EN_LABEL))
                .setParameter("artiestId", artiestId)
                .getResultList();
    }

    public Optional<Album> findById(long id) {
        return Optional.ofNullable(manager.find(Album.class, id));
    }
}
