package be.vdab.muziek.repositories;

import be.vdab.muziek.domain.Album;
import be.vdab.muziek.domain.Artiest;
import be.vdab.muziek.domain.Track;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql({"/insertArtiest.sql", "/insertLabel.sql", "/insertAlbum.sql"})
@Import(AlbumRepository.class)
public class AlbumRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final AlbumRepository repository;
    private final EntityManager manager;
    private static final String ALBUMS = "albums";

    public AlbumRepositoryTest(AlbumRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestAlbum() {
        return jdbcTemplate.queryForObject("select id from albums where naam = 'test'", Long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestAlbum()))
                .hasValueSatisfying(
                        album -> {
                            assertThat(album.getNaam()).isEqualTo("test");
                            assertThat(album.getArtiest().getNaam()).isEqualTo("test");
                            assertThat(album.getLabel().getNaam()).isEqualTo("test");
                        }
                );
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    void findAllGeeftAlleAlbumsGesorteerdOpNaam() {
        var albums = repository.findAll();
        manager.clear();
        assertThat(albums)
                .hasSize(countRowsInTable(ALBUMS))
                .extracting(Album::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
        assertThat(albums)
                .extracting(Album::getArtiest)
                .extracting(Artiest::getNaam)
                .isNotNull()
                .contains("test");
    }

    @Test
    void findByJaarGeeftAlbumsVanDatJaar() {
        var albums = repository.findByJaar(1);
        manager.clear();
        assertThat(albums)
                .hasSize(countRowsInTableWhere(ALBUMS, "jaar = 1"))
                .allSatisfy(
                        album -> assertThat(album.getJaar()).isEqualTo(1)
                        )
                .extracting(Album::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
        assertThat(albums)
                .extracting(Album::getArtiest)
                .extracting(Artiest::getNaam)
                .isNotNull()
                .contains("test");
    }

    @Test
    void tracksLezen() {
        assertThat(repository.findById(idVanTestAlbum()))
                .hasValueSatisfying(
                        album -> assertThat(album.getTracks()).extracting(Track::getNaam).containsOnly("test")
                );
    }

    @Test
    void albumLazyLoaded() {
        assertThat(repository.findById(idVanTestAlbum()))
                .hasValueSatisfying(
                        album -> assertThat(album.getArtiest().getNaam()).isEqualTo("test")
                );
    }
}
