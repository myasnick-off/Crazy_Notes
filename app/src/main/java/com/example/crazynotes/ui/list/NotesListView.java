package com.example.crazynotes.ui.list;

import com.example.crazynotes.domain.Note;

import java.util.List;

public interface NotesListView {

    void showNotes(List<Note> notesList);

    void scrollToPosition(int position);

    void showProgress();

    void hideProgress();
}
