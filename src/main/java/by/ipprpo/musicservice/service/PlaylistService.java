package by.ipprpo.musicservice.service;

import by.ipprpo.musicservice.model.Playlist;
import by.ipprpo.musicservice.model.Track;
import by.ipprpo.musicservice.repository.PlaylistRepository;

public class PlaylistService {

    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public void addTrackToPlaylist(int playlistId, Track track) {
        Playlist playlist = repository.findById(playlistId);
        if (playlist == null) {
            throw new IllegalArgumentException("Ошибка: Плейлист с ID " + playlistId + " не найден!");
        }

        for (Track existing : playlist.getTracks()) {
            if (existing.equals(track)) {
                throw new IllegalArgumentException(
                    "Композиция '" + track.getTitle() + 
                    "' исполнителя '" + track.getArtist() + "' уже есть в плейлисте!"
                );
            }
        }

        playlist.addTrack(track);
        repository.save(playlist);
    }

    public Playlist getPlaylist(int playlistId) {
        return repository.findById(playlistId);
    }
}
