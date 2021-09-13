package com.example.crazynotes.ui.list;

import com.example.crazynotes.domain.Callback;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.domain.NotesRepository;

import java.util.List;

public class NotesListPresenter {

    private final NotesListView listView;
    private final NotesRepository repository;

    public NotesListPresenter(NotesListView listView, NotesRepository repository) {
        this.listView = listView;
        this.repository = repository;
    }

    public void notesRequest() {
        listView.showProgress();
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                listView.showNotes(result);
                listView.hideProgress();
            }
        });
    }
}
