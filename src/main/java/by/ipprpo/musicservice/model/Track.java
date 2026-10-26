package by.ipprpo.musicservice.model;

import java.util.Objects;

public class Track {
    private int id;
    private String title;
    private String artist;
    private int durationSeconds;

    public Track(int id, String title, String artist, int durationSeconds) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        if (id == track.id) return true;
        return title.equalsIgnoreCase(track.title) && artist.equalsIgnoreCase(track.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), artist.toLowerCase());
    }

    @Override
    public String toString() {
        int min = durationSeconds / 60;
        int sec = durationSeconds % 60;
        return String.format("[%d] %s - %s (%d:%02d)", id, artist, title, min, sec);
    }
}
