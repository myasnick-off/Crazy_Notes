package com.example.crazynotes.ui.list;

import com.example.crazynotes.domain.NotesRepository;

public class NotesListPresenter {

    private final NotesListView listView;
    private final NotesRepository repository;

    public NotesListPresenter(NotesListView listView, NotesRepository repository) {
        this.listView = listView;
        this.repository = repository;
    }

    public void notesRequest() {
        listView.showNotes(repository.getNotes());
    }
}
