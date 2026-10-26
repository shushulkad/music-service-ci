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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionalPlaylistServiceTest {

    private PlaylistRepository repository;
    private PlaylistService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPlaylistRepository();
        service = new PlaylistService(repository);
    }

    @Test
    @DisplayName("Дополнительный тест: Получение существующего плейлиста через сервис")
    void testGetPlaylistSuccess() {
        User user = new User(10, "Алексей");
        Playlist playlist = new Playlist(100, "Рок-хиты", user);
        repository.save(playlist);

        Playlist found = service.getPlaylist(100);

        assertNotNull(found, "Плейлист должен быть найден");
        assertEquals("Рок-хиты", found.getTitle());
        assertEquals("Алексей", found.getOwner().getName());
    }

    @Test
    @DisplayName("Дополнительный тест: Получение несуществующего плейлиста возвращает null")
    void testGetPlaylistNotFoundReturnsNull() {
        Playlist found = service.getPlaylist(9999);
        assertNull(found, "Для несуществующего ID должен возвращаться null");
    }

    @Test
    @DisplayName("Дополнительный тест: Добавление нескольких разных треков в плейлист")
    void testAddMultipleUniqueTracks() {
        User user = new User(2, "Мария");
        Playlist playlist = new Playlist(20, "Утренняя пробежка", user);
        repository.save(playlist);

        Track track1 = new Track(1, "Eye of the Tiger", "Survivor", 245);
        Track track2 = new Track(2, "Stronger", "Kanye West", 311);
        Track track3 = new Track(3, "Lose Yourself", "Eminem", 326);

        service.addTrackToPlaylist(20, track1);
        service.addTrackToPlaylist(20, track2);
        service.addTrackToPlaylist(20, track3);

        Playlist saved = service.getPlaylist(20);
        assertEquals(3, saved.getTracks().size(), "В плейлисте должно быть 3 трека");
        assertEquals("Eye of the Tiger", saved.getTracks().get(0).getTitle());
        assertEquals("Stronger", saved.getTracks().get(1).getTitle());
        assertEquals("Lose Yourself", saved.getTracks().get(2).getTitle());
    }

    @Test
    @DisplayName("Дополнительный тест: Запрет добавления дубликата с другим регистром букв")
    void testAddDuplicateTrackCaseInsensitive() {
        User user = new User(3, "Ольга");
        Playlist playlist = new Playlist(30, "Чилаут", user);
        repository.save(playlist);

        Track track1 = new Track(1, "Weightless", "Marconi Union", 480);
        Track track2 = new Track(2, "weightless", "MARCONI UNION", 480);

        service.addTrackToPlaylist(30, track1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addTrackToPlaylist(30, track2);
        });

        assertTrue(exception.getMessage().contains("уже есть в плейлисте"));
        assertEquals(1, service.getPlaylist(30).getTracks().size());
    }

    @Test
    @DisplayName("Дополнительный тест: Проверка метода findAll в репозитории плейлистов")
    void testRepositoryFindAll() {
        User user = new User(4, "Дмитрий");
        Playlist p1 = new Playlist(1, "Джаз", user);
        Playlist p2 = new Playlist(2, "Блюз", user);

        repository.save(p1);
        repository.save(p2);

        List<Playlist> all = repository.findAll();
        assertEquals(2, all.size(), "Должно быть найдено 2 сохраненных плейлиста");
    }

    @Test
    @DisplayName("Дополнительный тест: Корректность форматирования продолжительности трека в toString")
    void testTrackToStringFormatting() {
        Track track = new Track(1, "Bohemian Rhapsody", "Queen", 354); // 354 сек = 5 мин 54 сек
        String str = track.toString();
        assertTrue(str.contains("5:54"), "Строковое представление должно содержать время 5:54");
        assertTrue(str.contains("Queen"));
        assertTrue(str.contains("Bohemian Rhapsody"));
    }
}
