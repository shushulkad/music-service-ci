package by.ipprpo.musicservice.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String title;
    private User owner;
    private List<Track> tracks = new ArrayList<>();

    public Playlist(int id, String title, User owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    @Override
    public String toString() {
        return "Плейлист '" + title + "' (Владелец: " + owner.getName() + ", треков: " + tracks.size() + ")";
    }
}
