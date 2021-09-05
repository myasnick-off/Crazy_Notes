package com.example.crazynotes.domain;

import com.example.crazynotes.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceNotesRepository implements NotesRepository {

    @Override
    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        noteList.add(new Note(R.string.note1, R.string.content1));
        noteList.add(new Note(R.string.note2, R.string.content2));
        noteList.add(new Note(R.string.note3, R.string.content3));

        return noteList;
    }
}
