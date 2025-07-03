// Swimmly\app\src\main\java\com.personal.swimmly\MainActivity.java

package com.personal.swimmly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.*;
import com.personal.swimmly.adapter.NoteAdapter;
import com.personal.swimmly.data.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // UI components
    RecyclerView recyclerView;            // Displays list of notes
    Button btnAddNote;                    // Button to navigate to add-note screen
    NoteAdapter adapter;                  // Adapter to bind note data to RecyclerView
    FloatingActionButton fabMood;         // FAB to choose app-wide mood
    String currentMood = "Balanced";     // Default mood state

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // Refresh list when returning to this screen
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Inflate layout

        // Link Java fields to XML views
        recyclerView = findViewById(R.id.recyclerViewNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
        fabMood = findViewById(R.id.fabMood);

        // Set up RecyclerView with a vertical list layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Handle "Add Note" button tap
        btnAddNote.setOnClickListener(v -> {
            // Launch AddNoteActivity to create a new note
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });

        // Handle mood selection FAB tap
        fabMood.setOnClickListener(v -> {
            // Show a dialog to let user pick current mood
            new AlertDialog.Builder(this)
                    .setTitle("Select Your Mood")
                    .setItems(new String[]{"Focused","Relaxed", "Anxious", "Creative", "Balanced"},
                            (dialog, which) -> {
                                // Map selected index to mood string
                                currentMood = selectedMoodFromIndex(which);
                                loadNotes(); // Refresh notes (in case sorting/filtering changes)
                            })
                    .show();
        });

        // Initial load of notes when activity starts
        loadNotes();
    }

    /**
     * Maps dialog index to a mood name.
     */
    private String selectedMoodFromIndex(int index) {
        switch (index) {
            case 0: return "Focused";
            case 1: return "Relaxed";
            case 2: return "Anxious";
            case 3: return "Stressed";   // Note: this label differs from dialog array
            case 4: return "Creative";
            default: return "Balanced";
        }
    }

    /**
     * Loads notes from the Room database and displays them.
     */
    private void loadNotes() {
        // Retrieve all Note objects
        List<Note> notes = NoteDatabase.getInstance(this)
                .noteDao().getAllNotes();

        // Create adapter with item click and long-click listeners
        adapter = new NoteAdapter(notes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("noteId",       note.id);
                intent.putExtra("noteTitle",    note.title);
                intent.putExtra("noteDesc",     note.description);
                intent.putExtra("noteUrgency",  note.urgency);
                intent.putExtra("noteEffort",   note.effort);
                intent.putExtra("noteDeadline", note.deadline);
                intent.putExtra("noteDone",     note.isDone);

                startActivity(intent);
            }


            @Override
            public void onNoteLongClick(Note note) {
                // On long press: delete the note
                NoteDatabase.getInstance(MainActivity.this).noteDao().delete(note);
                loadNotes(); // Refresh list

                // Show a Snackbar confirmation of deletion
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        "Note deleted", Snackbar.LENGTH_SHORT).show();
            }

        });

        // Attach adapter to RecyclerView to render the notes
        recyclerView.setAdapter(adapter);
    }

    /*
     * Summary:
     * - This MainActivity shows a list of notes from a Room database.
     * - Users can add, edit (tap), or delete (long-press) notes.
     * - A FloatingActionButton lets the user select a global "mood".
     * - Notes are reloaded whenever the activity resumes or the mood changes,
     *   allowing for future sorting/filtering logic based on the selected mood.
     */
}
