// Swimmly\app\src\main\java\com.personal.swimmly\AddNoteActivity.java

package com.personal.swimmly;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.personal.swimmly.data.Note;
import com.personal.swimmly.data.NoteDatabase;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

    // UI elements
    private EditText editTitle;
    private EditText editDesc;
    private EditText editUrgency;    // estimated time to complete
    private Spinner spinnerEffort;   // difficulty level
    private EditText editDeadline;   // due date
    private CheckBox checkDone;      // completion status
    private Button btnSave;

    // Holds note ID if editing; -1 means new note
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note); // updated layout

        // Bind views
        editTitle     = findViewById(R.id.editTitle);
        editDesc      = findViewById(R.id.editDesc);
        editUrgency   = findViewById(R.id.editUrgency);
        spinnerEffort = findViewById(R.id.spinnerEffort);
        editDeadline  = findViewById(R.id.editDeadline);
        checkDone     = findViewById(R.id.checkDone);
        btnSave       = findViewById(R.id.btnSave);

        // Populate difficulty spinner
        ArrayAdapter<CharSequence> effortAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.effort_levels,             // define this string-array in strings.xml
                android.R.layout.simple_spinner_item
        );
        effortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEffort.setAdapter(effortAdapter);

        // Show date picker when deadline field is tapped
        editDeadline.setFocusable(false);
        editDeadline.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        String formatted = String.format("%04d-%02d-%02d", year, month+1, dayOfMonth);
                        editDeadline.setText(formatted);
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        // Check for editing an existing note
        if (getIntent().hasExtra("noteId")) {
            noteId = getIntent().getIntExtra("noteId", -1);
            editTitle.setText(getIntent().getStringExtra("noteTitle"));
            editDesc.setText(getIntent().getStringExtra("noteDesc"));
            editUrgency.setText(getIntent().getStringExtra("noteUrgency"));
            editDeadline.setText(getIntent().getStringExtra("noteDeadline"));
            checkDone.setChecked(getIntent().getBooleanExtra("noteDone", false));

            // Set spinner selection based on passed effort
            String effortVal = getIntent().getStringExtra("noteEffort");
            int pos = effortAdapter.getPosition(effortVal);
            spinnerEffort.setSelection(pos);

            btnSave.setText("Update");
        }

        // Save or update note on button click
        btnSave.setOnClickListener(v -> {
            String title   = editTitle.getText().toString().trim();
            String desc    = editDesc.getText().toString().trim();
            String urgency = editUrgency.getText().toString().trim();
            String effort  = spinnerEffort.getSelectedItem().toString();
            String deadline= editDeadline.getText().toString().trim();
            boolean done   = checkDone.isChecked();

            // Validate required field
            if (title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
                return;
            }

            Note note = new Note(title, desc);
            note.urgency   = urgency;
            note.effort    = effort;
            note.deadline  = deadline;
            note.isDone    = done;

            NoteDatabase db = NoteDatabase.getInstance(this);

            if (noteId != -1) {
                // Update existing
                note.id = noteId;
                db.noteDao().update(note);
                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();
            } else {
                // Insert new
                db.noteDao().insert(note);
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    /*
     * Summary:
     * - Extended to capture and save urgency, effort, deadline, and done status.
     * - Spinner for effort, date picker for deadline, checkbox for completion.
     * - Loads values when editing and updates via Room DAO.
     */
}
