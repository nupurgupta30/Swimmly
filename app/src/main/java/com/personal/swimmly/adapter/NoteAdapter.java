// Swimmly\app\src\main\java\com.personal.swimmly\adapter\NoteAdapter.java

package com.personal.swimmly.adapter;

import android.graphics.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.personal.swimmly.R;
import com.personal.swimmly.data.Note;
import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of Note items.
 * Handles click and long-click events via the OnNoteClickListener interface.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    /**
     * List of Note objects to display
     */
    private final List<Note> notes;

    /**
     * Listener for handling note click and long-click events
     */
    private final OnNoteClickListener listener;

    /**
     * Callback interface for note interactions
     */
    public interface OnNoteClickListener {
        /**
         * Called when the user long-presses a note
         * @param note The Note that was long-clicked
         */
        void onNoteLongClick(Note note);

        /**
         * Called when the user taps a note
         * @param note The Note that was clicked
         */
        void onNoteClick(Note note);
    }

    /**
     * Constructor: initializes adapter with data and listener
     * @param notes List of notes to display
     * @param listener Listener for note interactions
     */
    public NoteAdapter(List<Note> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    /**
     * Inflates the layout for each item and creates a ViewHolder
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the XML layout for a single note item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    /**
     * Binds note data to the ViewHolder and sets up click listeners
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.textTitle.setText(note.title);
        holder.textDesc.setText(note.description);

        // Populate your new fields
        holder.textUrgency.setText("⏱︎ " + note.urgency);
        holder.textEffort.setText("💪 " + note.effort);
        holder.textDeadline.setText("📅 " + note.deadline);
        holder.checkViewDone.setChecked(note.isDone);

        // Optional: strike-through title when done
        if (note.isDone) {
            holder.textTitle.setPaintFlags(
                    holder.textTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.textTitle.setPaintFlags(
                    holder.textTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG
            );
        }

        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onNoteLongClick(note);
            return true;
        });
    }

    /**
     * Returns the total number of notes
     */
    @Override
    public int getItemCount() {
        return notes.size();
    }

    /**
     * ViewHolder class holds references to the views for each note item
     */
    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDesc, textUrgency, textEffort, textDeadline;
        CheckBox checkViewDone;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            // Link the TextViews to their XML counterparts
            textTitle     = itemView.findViewById(R.id.textViewTitle);
            textDesc      = itemView.findViewById(R.id.textViewDesc);
            textUrgency   = itemView.findViewById(R.id.textViewUrgency);
            textEffort    = itemView.findViewById(R.id.textViewEffort);
            textDeadline  = itemView.findViewById(R.id.textViewDeadline);
            checkViewDone = itemView.findViewById(R.id.checkViewDone);
        }

    }

    /*
     * Summary:
     * - NoteAdapter bridges the list of Note data with the RecyclerView.
     * - onCreateViewHolder() inflates each item's layout.
     * - onBindViewHolder() populates the views and wires up interaction callbacks.
     * - getItemCount() tells RecyclerView how many items to display.
     * - Listeners allow MainActivity to respond to taps (edit) and long-presses (delete).
     */
}
