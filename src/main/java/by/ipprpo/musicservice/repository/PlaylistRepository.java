package by.ipprpo.musicservice.repository;

import by.ipprpo.musicservice.model.Playlist;
import java.util.List;

public interface PlaylistRepository {
    void save(Playlist playlist);
    Playlist findById(int id);
    List<Playlist> findAll();
}
