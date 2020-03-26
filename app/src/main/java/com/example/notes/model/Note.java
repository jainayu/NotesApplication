package com.example.notes.model;

public class Note {
    int id;
    String title;
    String desc;
    String timstamp;
    String colorCode;

    public Note(int id, String title, String desc, String timstamp, String colorCode) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.timstamp = timstamp;
        this.colorCode = colorCode;
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
    public String getColorCode() {
        return colorCode;
    }

    public String getTimstamp() {
        return timstamp;
    }
}
