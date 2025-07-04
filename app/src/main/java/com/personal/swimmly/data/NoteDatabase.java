// Swimmly\app\src\main\java\com.personal.swimmly\data\NoteDatabase.java

package com.personal.swimmly.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 3)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() // this is the magic fix
                    .allowMainThreadQueries() // NOT for production; just for testing
                    .build();
        }
        return instance;
    }
}
