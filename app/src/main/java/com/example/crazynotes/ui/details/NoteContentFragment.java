package com.example.crazynotes.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

import java.text.SimpleDateFormat;

public class NoteContentFragment extends DialogFragment {

    private static final String ARG_NOTE = "KEY_NOTE";

    private TextView noteName;
    private TextView noteDate;
    private TextView noteContent;
    private ImageView noteImage;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    public static NoteContentFragment newInstance(Note note) {
        NoteContentFragment dialogFragment = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        dialogFragment.setArguments(args);
        return dialogFragment;
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
        noteImage = view.findViewById(R.id.content_img_note);

        // получаем заметку из переданных аргументов
        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            if (note != null) {
                displayNote(note);
            }
        }
    }

    // метод заполнения вьюшек фрагмента данными из заметки
    private void displayNote(Note note) {
        noteName.setText(note.getName());
        noteDate.setText(dateFormatter.format(note.getDate()));
        noteContent.setText(note.getContent());
        Glide.with(noteImage).load(note.getImgUrl()).into(noteImage);
        noteImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
