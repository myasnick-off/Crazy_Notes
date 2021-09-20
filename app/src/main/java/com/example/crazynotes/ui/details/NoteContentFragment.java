package com.example.crazynotes.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.list.NotesListFragment;

import java.text.SimpleDateFormat;

public class NoteContentFragment extends Fragment {

    private static final String KEY_NOTE = "KEY_NOTE";

    private TextView noteName;
    private TextView noteDate;
    private TextView noteContent;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    public static Fragment newInstance(Note note) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteName = view.findViewById(R.id.note_name);
        noteDate = view.findViewById(R.id.note_date);
        noteContent = view.findViewById(R.id.note_content);

        getParentFragmentManager().setFragmentResultListener(NotesListFragment.KEY_SELECTED_NOTE, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(NotesListFragment.KEY_NOTE);
                displayNote(note);
            }
        });

        if (getArguments() != null && getArguments().containsKey(KEY_NOTE)) {
            Note note = getArguments().getParcelable(KEY_NOTE);
            if (note != null) {
                displayNote(note);
            }
        }

    }

    private void displayNote(Note note) {
        noteName.setText(note.getName());
        noteDate.setText(dateFormatter.format(note.getDate()));
        noteContent.setText(note.getContent());
    }
}
