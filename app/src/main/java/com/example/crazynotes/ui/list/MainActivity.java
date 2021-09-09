package com.example.crazynotes.ui.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.details.NoteContentActivity;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNoteClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void noteOnClicked(Note note) {
        if (!getResources().getBoolean(R.bool.isLandscape)) {
            Intent intent = new Intent(this, NoteContentActivity.class);
            intent.putExtra(NoteContentActivity.KEY_NOTE_CONTENT, note);
            startActivity(intent);
        }
    }
}