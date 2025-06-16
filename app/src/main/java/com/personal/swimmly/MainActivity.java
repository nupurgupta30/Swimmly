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

    RecyclerView recyclerView;
    Button btnAddNote;
    NoteAdapter adapter;
    FloatingActionButton fabMood;
    String currentMood = "Focused"; // default mood



    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // refresh list
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewNotes);
        btnAddNote = findViewById(R.id.btnAddNote);
        fabMood = findViewById(R.id.fabMood);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddNote.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });
        

        fabMood.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Select Your Mood")
                    .setItems(new String[]{"Tired", "Focused", "Lazy", "Stressed", "Energized"}, (dialog, which) -> {
                        currentMood = selectedMoodFromIndex(which);
                        loadNotes(); // refresh with sorted order
                    })
                    .show();
        });




        loadNotes();
    }

    private String selectedMoodFromIndex(int index) {
        switch (index) {
            case 0: return "Tired";
            case 1: return "Focused";
            case 2: return "Lazy";
            case 3: return "Stressed";
            case 4: return "Energized";
            default: return "Focused";
        }
    }




    private void loadNotes() {
        List<Note> notes = NoteDatabase.getInstance(this).noteDao().getAllNotes();

        adapter = new NoteAdapter(notes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("noteId", note.id);
                intent.putExtra("noteTitle", note.title);
                intent.putExtra("noteDesc", note.description);
                startActivity(intent);
            }

            @Override
            public void onNoteLongClick(Note note) {
                NoteDatabase.getInstance(MainActivity.this).noteDao().delete(note);
                loadNotes();

                Snackbar.make(findViewById(R.id.coordinatorLayout), "Note deleted", Snackbar.LENGTH_SHORT).show();
            }

        });

        recyclerView.setAdapter(adapter); // this comes after adapter is created
    }



}
