package com.personal.swimmly.data;

import androidx.room.*;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Note ORDER BY id DESC")
    List<Note> getAllNotes();

    @Update
    void update(Note note);

}
