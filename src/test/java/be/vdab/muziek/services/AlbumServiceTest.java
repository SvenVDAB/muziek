package be.vdab.muziek.services;

import be.vdab.muziek.domain.Album;
import be.vdab.muziek.domain.Artiest;
import be.vdab.muziek.domain.Label;
import be.vdab.muziek.exceptions.AlbumNietGevondenException;
import be.vdab.muziek.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
    private AlbumService service;
    @Mock
    private AlbumRepository repository;
    private Album album;

    @BeforeEach
    void beforeEach() {
        var artiest = new Artiest("test");
        var label = new Label("test");
        service = new AlbumService(repository);
        album = new Album(artiest, label, "test", 1, 2, 3);
    }

    @Test
    void setScore() {
        when(repository.findById(1)).thenReturn(Optional.of(album));
        service.setScore(1, 9);
        assertThat(album.getScore()).isEqualTo(9);
        verify(repository).findById(1);
    }

    @Test
    void setScoreVoorOnbestaandAlbum() {
        assertThatExceptionOfType(AlbumNietGevondenException.class).isThrownBy(
                () -> service.setScore(-1, 5)
        );
        verify(repository).findById(-1);
    }
}
