package by.ipprpo.musicservice;

import by.ipprpo.musicservice.model.Playlist;
import by.ipprpo.musicservice.model.Track;
import by.ipprpo.musicservice.model.User;
import by.ipprpo.musicservice.repository.InMemoryPlaylistRepository;
import by.ipprpo.musicservice.repository.PlaylistRepository;
import by.ipprpo.musicservice.service.PlaylistService;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   Музыкальный сервис (Вариант 11) - Автоматическая сборка");
        System.out.println("==================================================");

        PlaylistRepository repository = new InMemoryPlaylistRepository();
        PlaylistService service = new PlaylistService(repository);

        User user = new User(1, "Дарья");
        Playlist playlist = new Playlist(1, "Любимые хиты", user);
        repository.save(playlist);

        Track track1 = new Track(1, "Yesterday", "The Beatles", 125);
        Track track2 = new Track(2, "Bohemian Rhapsody", "Queen", 354);

        System.out.println("1. Добавление треков в плейлист...");
        service.addTrackToPlaylist(1, track1);
        service.addTrackToPlaylist(1, track2);
        System.out.println("Треки успешно добавлены: " + service.getPlaylist(1).getTracks().size() + " шт.");

        System.out.println("2. Проверка индивидуального задания: запрет дубликата...");
        try {
            service.addTrackToPlaylist(1, new Track(3, "yesterday", "The Beatles", 125));
            System.out.println("[ВНИМАНИЕ] Дубликат был добавлен ошибочно!");
        } catch (IllegalArgumentException e) {
            System.out.println("[УСПЕХ] Запрещено добавление дубликата! Сообщение: " + e.getMessage());
        }

        System.out.println("3. Состояние плейлиста:");
        Playlist currentPlaylist = service.getPlaylist(1);
        System.out.println(currentPlaylist);
        for (Track t : currentPlaylist.getTracks()) {
            System.out.println("  - " + t);
        }

        System.out.println("==================================================");
        System.out.println("   Приложение успешно завершило работу");
        System.out.println("==================================================");
    }
}
