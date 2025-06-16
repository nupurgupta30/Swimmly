package com.personal.swimmly.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

