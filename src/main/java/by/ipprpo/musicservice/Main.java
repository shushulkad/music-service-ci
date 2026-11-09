package by.ipprpo.musicservice;

import by.ipprpo.musicservice.model.Playlist;
import by.ipprpo.musicservice.model.Track;
import by.ipprpo.musicservice.model.User;
import by.ipprpo.musicservice.repository.InMemoryPlaylistRepository;
import by.ipprpo.musicservice.repository.PlaylistRepository;
import by.ipprpo.musicservice.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Music service: Lab 5 demo");

        PlaylistRepository repository = new InMemoryPlaylistRepository();
        PlaylistService service = new PlaylistService(repository);

        User user = new User(1, "Дарья");
        Playlist playlist = new Playlist(1, "Favorite Hits", user);
        repository.save(playlist);

        Track track1 = new Track(1, "Yesterday", "The Beatles", 125);
        Track track2 = new Track(2, "Bohemian Rhapsody", "Queen", 354);

        service.addTrackToPlaylist(1, track1);
        service.addTrackToPlaylist(1, track2);

        try {
            service.addTrackToPlaylist(1, new Track(3, "yesterday", "The Beatles", 125));
        } catch (IllegalArgumentException expected) {
            // Сервис уже записал предупреждение о дубликате.
        }

        try {
            service.addTrackToPlaylist(999, track1);
        } catch (IllegalArgumentException expected) {
            // Сервис уже записал ошибку о несуществующем плейлисте.
        }

        Playlist currentPlaylist = service.getPlaylist(1);
        LOGGER.info("Final playlist '{}': {}", currentPlaylist.getTitle(), currentPlaylist.getTracks());
    }
}
