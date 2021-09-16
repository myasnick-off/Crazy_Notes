package com.example.crazynotes.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void addNote(String title, String imgUrl, Callback<Note> callback);

    void deleteNote(Note note, Callback<Note> callback);
}
