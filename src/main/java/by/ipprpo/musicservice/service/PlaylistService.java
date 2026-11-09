package by.ipprpo.musicservice.service;

import by.ipprpo.musicservice.model.Playlist;
import by.ipprpo.musicservice.model.Track;
import by.ipprpo.musicservice.repository.PlaylistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaylistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistService.class);
    private static final int RECOMMENDED_MIN_TRACKS = 2;

    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public void addTrackToPlaylist(int playlistId, Track track) {
        Playlist playlist = repository.findById(playlistId);
        if (playlist == null) {
            LOGGER.error("Could not add track {}: playlist {} not found", track.getId(), playlistId);
            throw new IllegalArgumentException("Ошибка: Плейлист с ID " + playlistId + " не найден!");
        }

        for (Track existing : playlist.getTracks()) {
            if (existing.equals(track)) {
                LOGGER.warn("Track '{}' is already in playlist {}", track.getTitle(), playlistId);
                throw new IllegalArgumentException(
                    "Композиция '" + track.getTitle() + 
                    "' исполнителя '" + track.getArtist() + "' уже есть в плейлисте!"
                );
            }
        }

        playlist.addTrack(track);
        repository.save(playlist);
        int trackCount = playlist.getTracks().size();
        LOGGER.info("Added track '{}' to playlist {}; total tracks: {}", track.getTitle(), playlistId, trackCount);
        if (trackCount < RECOMMENDED_MIN_TRACKS) {
            LOGGER.warn("Playlist {} has {} track(s); recommended minimum: {}",
                    playlistId, trackCount, RECOMMENDED_MIN_TRACKS);
        }
    }

    public Playlist getPlaylist(int playlistId) {
        return repository.findById(playlistId);
    }
}
