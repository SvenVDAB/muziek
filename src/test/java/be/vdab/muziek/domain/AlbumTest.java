package be.vdab.muziek.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AlbumTest {
    private Album album1;
    private Artiest artiest1;
    private Label label1;

    @BeforeEach
    void beforeEach() {
        label1 = new Label("test");
        artiest1 = new Artiest("test");
        album1 = new Album(artiest1, label1, "test", 1, 2, 3);
    }

    @Test
    void setScore() {
        album1.setScore(5);
        assertThat(album1.getScore()).isEqualTo(5);
    }

    @Test
    void setScoreMetNegatieveWaardeOfWaardeGroterDan10Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> album1.setScore(-1)
        );
        assertThatIllegalArgumentException().isThrownBy(
                () -> album1.setScore(11)
        );
        assertThatNoException().isThrownBy(
                () -> album1.setScore(10)
        );
    }
}
