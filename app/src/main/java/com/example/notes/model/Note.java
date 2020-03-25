package com.example.notes.model;

public class Note {
    int id;
    String title, desc, timstamp;

    public Note(int id, String title, String desc, String timstamp) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.timstamp = timstamp;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTimstamp() {
        return timstamp;
    }
}
