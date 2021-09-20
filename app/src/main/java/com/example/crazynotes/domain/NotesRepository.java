package com.example.crazynotes.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void addNote(Note note, Callback<Note> callback);

    void deleteNote(Note note, Callback<Note> callback);

    void copyNote(Note note, Callback<Note> callback);

    void updateNote(Note note, Callback<Note> noteCallback);
}
