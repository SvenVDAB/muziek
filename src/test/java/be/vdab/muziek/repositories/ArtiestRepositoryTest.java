package be.vdab.muziek.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql("/insertArtiest.sql")
@Import(ArtiestRepository.class)
class ArtiestRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final ArtiestRepository repository;

    public ArtiestRepositoryTest(ArtiestRepository repository) {
        this.repository = repository;
    }

    private long idVanTestArtiest() {
        return jdbcTemplate.queryForObject("select id from artiesten where naam = 'test'", Long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArtiest()))
                .hasValueSatisfying(
                        artiest -> assertThat(artiest.getNaam()).isEqualTo("test")
                );
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isEmpty();
    }
}
