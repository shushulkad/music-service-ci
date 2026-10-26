package by.ipprpo.musicservice.repository;

import by.ipprpo.musicservice.model.Playlist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPlaylistRepository implements PlaylistRepository {
    private final Map<Integer, Playlist> storage = new HashMap<>();

    @Override
    public void save(Playlist playlist) {
        storage.put(playlist.getId(), playlist);
    }

    @Override
    public Playlist findById(int id) {
        return storage.get(id);
    }

    @Override
    public List<Playlist> findAll() {
        return new ArrayList<>(storage.values());
    }
}
