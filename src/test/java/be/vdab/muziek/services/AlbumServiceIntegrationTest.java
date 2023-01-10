package be.vdab.muziek.services;

import be.vdab.muziek.repositories.AlbumRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import({ AlbumService.class, AlbumRepository.class })
//@Sql("")
@Sql({"/insertArtiest.sql", "/insertLabel.sql", "/insertAlbum.sql"})
public class AlbumServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ALBUMS = "albums";
    private final AlbumService service;
    private final EntityManager manager;

    public AlbumServiceIntegrationTest(AlbumService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }

    private long idVanTestAlbum() {
        return jdbcTemplate.queryForObject(
                "select id from albums where naam = 'test'", Long.class
        );
    }

    @Test
    void setScore() {
        var id = idVanTestAlbum();
        service.setScore(id, 7);
        manager.flush();
        assertThat(countRowsInTableWhere(ALBUMS, "score = 7 and id = " + id))
                .isOne();
    }
}
