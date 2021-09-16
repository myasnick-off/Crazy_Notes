package com.example.crazynotes.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

public class NoteEditFragment extends Fragment {

    public static final String KEY_NOTE_EDIT = "KEY_NOTE_EDIT";
    public static final String KEY_NOTE_RES = "KEY_NOTE_RES";

    private TextView noteNameEdit;
    private TextView noteContentEdit;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static NoteEditFragment newInstance(Note note) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_NOTE_EDIT, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteNameEdit = view.findViewById(R.id.edit_note_name);
        noteContentEdit = view.findViewById(R.id.edit_note_content);
        Note note = requireArguments().getParcelable(KEY_NOTE_EDIT);
        displayNote(note);

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setName(noteNameEdit.getText().toString());
                note.setContent(noteContentEdit.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_NOTE_EDIT, note);

                getParentFragmentManager()
                        .setFragmentResult(KEY_NOTE_RES, bundle);

                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void displayNote(Note note) {
        noteNameEdit.setText(note.getName());
        noteContentEdit.setText(note.getContent());
    }
}