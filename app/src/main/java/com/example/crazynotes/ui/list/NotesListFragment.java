package com.example.crazynotes.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.DeviceNotesRepository;
import com.example.crazynotes.domain.Note;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public interface OnNoteClicked {
        void noteOnClicked(Note note);
    }

    public static final String KEY_SELECTED_NOTE = "SELECTED_NOTE";
    public static final String KEY_NOTE = "NOTE";

    private NotesListPresenter presenter;
    private LinearLayout container;
    private OnNoteClicked onNoteClickedContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteClicked) {
            onNoteClickedContext = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClickedContext = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new DeviceNotesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = view.findViewById(R.id.root);
        presenter.notesRequest();

    }

    @Override
    public void showNotes(List<Note> notesList) {
        for (Note note : notesList) {
            View noteItem = LayoutInflater.from(requireContext()).inflate(R.layout.note_item, container, false);
            noteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoteClickedContext != null) {
                        onNoteClickedContext.noteOnClicked(note);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_NOTE, note);
                    getParentFragmentManager().setFragmentResult(KEY_SELECTED_NOTE, bundle);
                }
            });
            TextView noteName = noteItem.findViewById(R.id.note_text_view);
            noteName.setText(note.getName());
            container.addView(noteItem);
        }
    }
}
