package by.ipprpo.musicservice;

import by.ipprpo.musicservice.model.Playlist;
import by.ipprpo.musicservice.model.Track;
import by.ipprpo.musicservice.model.User;
import by.ipprpo.musicservice.repository.InMemoryPlaylistRepository;
import by.ipprpo.musicservice.repository.PlaylistRepository;
import by.ipprpo.musicservice.service.PlaylistService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistServiceTest {

    private PlaylistRepository repository;
    private PlaylistService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPlaylistRepository();
        service = new PlaylistService(repository);
    }

    @Test
    @DisplayName("Успешное добавление трека в плейлист")
    void testAddTrackSuccess() {
        User user = new User(1, "Иван");
        Playlist playlist = new Playlist(1, "Любимое", user);
        repository.save(playlist);

        Track track = new Track(1, "Yesterday", "The Beatles", 125);
        service.addTrackToPlaylist(1, track);

        Playlist savedPlaylist = service.getPlaylist(1);
        assertNotNull(savedPlaylist, "Плейлист должен существовать");
        assertEquals(1, savedPlaylist.getTracks().size(), "В плейлисте должен быть ровно 1 трек");
        assertEquals("Yesterday", savedPlaylist.getTracks().get(0).getTitle());
    }

    @Test
    @DisplayName("Ошибка при попытке добавить трек в несуществующий плейлист")
    void testAddTrackToNonExistentPlaylist() {
        Track track = new Track(1, "Yesterday", "The Beatles", 125);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addTrackToPlaylist(999, track);
        });
        assertTrue(exception.getMessage().contains("не найден"));
    }

    @Test
    @DisplayName("Запрет повторного добавления трека в плейлист")
    void testAddDuplicateTrackThrowsException() {
        User user = new User(1, "Иван");
        Playlist playlist = new Playlist(1, "Любимое", user);
        repository.save(playlist);

        Track track1 = new Track(1, "Yesterday", "The Beatles", 125);
        Track track2 = new Track(2, "Yesterday", "The Beatles", 125);

        service.addTrackToPlaylist(1, track1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addTrackToPlaylist(1, track2);
        });

        assertTrue(exception.getMessage().contains("уже есть в плейлисте"));
        assertEquals(1, service.getPlaylist(1).getTracks().size());
    }

    @Test
@DisplayName("Добавление треков с одинаковым названием разных исполнителей")
void testSameTitleDifferentArtistsAllowed() {
    User user = new User(10, "Дарья");
    Playlist playlist = new Playlist(10, "Разные исполнители", user);
    repository.save(playlist);

    service.addTrackToPlaylist(
        10, new Track(101, "Hello", "Adele", 295)
    );
    service.addTrackToPlaylist(
        10, new Track(102, "Hello", "Lionel Richie", 251)
    );

    Playlist saved = service.getPlaylist(10);

    assertEquals(2, saved.getTracks().size());
    assertEquals("Adele", saved.getTracks().get(0).getArtist());
    assertEquals("Lionel Richie", saved.getTracks().get(1).getArtist());
}
}
