package com.example.crazynotes.domain;

import android.os.Handler;
import android.os.Looper;

import com.example.crazynotes.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceNotesRepository implements NotesRepository {

    private final Handler handler = new Handler(Looper.getMainLooper());
    List<Note> noteList;

    public DeviceNotesRepository() {
        noteList = new ArrayList<>();
        noteList.add(new Note("1", "Some note", "some content", "https://i.ytimg.com/vi/j1HomFU9GAA/maxresdefault.jpg"));
        noteList.add(new Note("2", "To do", "to do something", "https://pbs.twimg.com/media/Eg6kMbfWAAY3cpW.jpg"));
        noteList.add(new Note("3", "To buy", "to buy something", "https://ufaved.info/upload/iblock/4f8/4f89d3dff002302d68645b8f2a303700.jpg"));
        noteList.add(new Note("4", "Don't forget", "Do not forget to smile!", "https://pbs.twimg.com/media/EjmcgpeVkAAE2TZ.jpg"));
        noteList.add(new Note("5", "Daily", "Have fun all day", "https://secure.meetupstatic.com/photos/event/7/b/9/e/600_482911646.jpeg"));
        noteList.add(new Note("6", "Self-development", "Build yourself every day!", "https://pbs.twimg.com/media/EvF3lTIWQAYpOOb.jpg"));
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(noteList);
                    }
                });
            }
        }).start();
    }

    @Override
    public void addNote(Note note, Callback<Note> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteList.add(note);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });
            }
        }).start();
    }

    @Override
    public void updateNote(Note note, Callback<Note> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = noteList.indexOf(note);
                noteList.set(index, note);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });
            }
        }).start();
    }

    @Override
    public void deleteNote(Note note, Callback<Note> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteList.remove(note);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        }).start();
    }
}
