package com.personal.swimmly;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.personal.swimmly.data.*;

public class AddNoteActivity extends AppCompatActivity {

    EditText editTitle, editDesc;
    Button btnSave;
    int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String desc = editDesc.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
                return;
            }

            Note note = new Note(title, desc);
            NoteDatabase.getInstance(this).noteDao().insert(note);
            finish(); // Go back to main
        });

        if (getIntent().hasExtra("noteId")) {
            noteId = getIntent().getIntExtra("noteId", -1);
            String title = getIntent().getStringExtra("noteTitle");
            String desc = getIntent().getStringExtra("noteDesc");

            editTitle.setText(title);
            editDesc.setText(desc);
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String desc = editDesc.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
                return;
            }

            NoteDatabase db = NoteDatabase.getInstance(this);
            if (noteId != -1) {
                Note note = new Note(title, desc);
                note.id = noteId;
                db.noteDao().update(note); // you'll define this below
                Toast.makeText(this, noteId != -1 ? "Note updated!" : "Note saved!", Toast.LENGTH_SHORT).show();

            } else {
                db.noteDao().insert(new Note(title, desc));
            }
            finish();
        });
    }
}
