package com.example.crazynotes.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

public class NoteContentActivity extends AppCompatActivity {

    public static final String KEY_NOTE_CONTENT = "NOTE_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);

        if (getResources().getBoolean(R.bool.isLandscape)) {
            finish();
        } else {

            Note note = getIntent().getParcelableExtra(KEY_NOTE_CONTENT);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, NoteContentFragment.newInstance(note), "noteTag")
                    .commit();
        }
    }
}