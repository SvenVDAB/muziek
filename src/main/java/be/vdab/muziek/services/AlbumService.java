package be.vdab.muziek.services;

import be.vdab.muziek.domain.Album;
import be.vdab.muziek.exceptions.AlbumNietGevondenException;
import be.vdab.muziek.repositories.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Optional<Album> findById(long id) {
        return albumRepository.findById(id);
    }

    @Transactional
    public void setScore(long id, int score) {
        albumRepository.findById(id)
                .orElseThrow(AlbumNietGevondenException::new)
                .setScore(score);
    }
}
