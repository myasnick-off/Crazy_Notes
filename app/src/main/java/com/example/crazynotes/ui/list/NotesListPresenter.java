package com.example.crazynotes.ui.list;

import com.example.crazynotes.domain.Callback;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.domain.NotesRepository;

import java.util.ArrayList;
import java.util.List;

public class NotesListPresenter {

    private final NotesListView listView;
    private final NotesRepository repository;
    private List<Note> noteList = new ArrayList<>();

    public NotesListPresenter(NotesListView listView, NotesRepository repository) {
        this.listView = listView;
        this.repository = repository;
    }

    // Метод запроса списка заметок из репозитория и передачи его во View для отображения
    public void notesRequest() {
        listView.showProgress();
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                listView.hideProgress();
                noteList.clear();
                noteList.addAll(result);
                listView.showNotes(new ArrayList<>(result));
            }
        });
    }

    public void addNewNote(Note note) {
        listView.showProgress();
        repository.addNote(note, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                listView.hideProgress();
                noteList.add(result);
                listView.showNotes(new ArrayList<>(noteList));
                listView.scrollToPosition(noteList.size());
            }
        });
    }

    public void removeNote(Note note) {
        listView.showProgress();
        repository.deleteNote(note, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                listView.hideProgress();
                noteList.remove(note);
                listView.showNotes(new ArrayList<>(noteList));
            }
        });
    }

    public void updateNote(Note note) {
        listView.showProgress();
        repository.updateNote(note, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                listView.hideProgress();
                int index = noteList.indexOf(note);
                noteList.set(index, result);
                listView.showNotes(new ArrayList<>(noteList));
                listView.scrollToPosition(index);
            }
        });
    }
}
