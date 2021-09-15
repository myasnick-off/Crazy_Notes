package com.example.crazynotes.domain;

import android.os.Handler;
import android.os.Looper;

import com.example.crazynotes.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceNotesRepository implements NotesRepository {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> baseList = new ArrayList<>();
                baseList.add(new Note(1, R.string.note1, R.string.content1, "https://i.ytimg.com/vi/j1HomFU9GAA/maxresdefault.jpg"));
                baseList.add(new Note(2, R.string.note2, R.string.content2, "https://pbs.twimg.com/media/Eg6kMbfWAAY3cpW.jpg"));
                baseList.add(new Note(3, R.string.note3, R.string.content3, "https://ufaved.info/upload/iblock/4f8/4f89d3dff002302d68645b8f2a303700.jpg"));
                baseList.add(new Note(4, R.string.note4, R.string.content4, "https://pbs.twimg.com/media/EjmcgpeVkAAE2TZ.jpg"));
                baseList.add(new Note(5, R.string.note5, R.string.content5, "https://secure.meetupstatic.com/photos/event/7/b/9/e/600_482911646.jpeg"));
                baseList.add(new Note(6, R.string.note6, R.string.content6, "https://pbs.twimg.com/media/EvF3lTIWQAYpOOb.jpg"));
                List<Note> noteList = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    int index = (int) Math.round(Math.random() * 5);
                    noteList.add(baseList.get(index));
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(noteList);
                    }
                });
            }
        }).start();
    }
}
