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
                .createQuery("""
                        select a 
                        from Album a 
                        order by a.naam
                        """,
                Album.class)
                .setHint("jakarta.persistence.loadgraph", manager.createEntityGraph(Album.MET_ARTIEST_EN_LABEL))
                .getResultList();

    }

    public Optional<Album> findById(long id) {
        return Optional.ofNullable(manager.find(Album.class, id));
    }
}
