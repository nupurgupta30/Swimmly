// Swimmly\app\src\main\java\com.personal.swimmly\data\Note.java

package com.personal.swimmly.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data entity representing a task or "note" in the Swimmly app.
 * Mapped to a Room database table.
 */
@Entity
public class Note {
    /**
     * Unique identifier for each note, auto-generated by Room.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * Main heading or title of the task.
     */
    public String title;

    /**
     * Detailed description or content of the task.
     */
    public String description;

    /**
     * Urgency indicator: estimated time needed to complete the task
     * (e.g., "15m", "2h"). Can drive urgency axis in the priority matrix.
     */
    public String urgency;

    /**
     * Effort level required: "easy", "medium", or "hard".
     * Maps to the difficulty axis in the matrix.
     */
    public String effort;

    /**
     * Deadline timestamp or formatted date string (e.g., "2025-07-05T17:00").
     * Helps calculate urgency relative to now.
     */
    public String deadline;

    /**
     * Completion flag: true if the task is marked done, false otherwise.
     */
    public boolean isDone;

    /**
     * Constructor for creating a minimal Note with only title and description.
     * Remaining metadata can be set via direct field access or later methods.
     *
     * @param title       Task heading
     * @param description Task details
     */
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /*
     * Summary:
     * - Note.java defines the schema for a task stored in Room DB.
     * - Fields:
     *    id        : auto-generated primary key
     *    title     : heading of the task
     *    description: details of the task
     *    urgency   : estimated time for completion (drives "urgent" axis)
     *    effort    : difficulty level for the "easy/hard" axis
     *    deadline  : due date/time (optional)
     *    isDone    : tracks completion status
     * - This structure supports sorting and filtering tasks in the mood-based
     *   priority matrix.
     */
}
