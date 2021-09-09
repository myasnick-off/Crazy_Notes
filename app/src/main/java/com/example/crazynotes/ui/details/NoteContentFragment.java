package com.example.crazynotes.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.list.NotesListFragment;

public class NoteContentFragment extends Fragment {

    private static final String KEY_NOTE = "KEY_NOTE";

    private TextView noteName;
    private TextView noteDate;
    private TextView noteContent;

    public static Fragment newInstance(Note note) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, note);
        fragment.setArguments(bundle);
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

        // Вызов контекстного меню при нажатии на заголовок заметки
        noteName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                requireActivity().getMenuInflater().inflate(R.menu.menu_title_pop_up, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.pop_up_rename) {
                            Toast.makeText(requireContext(), "Rename selected", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.pop_up_copy) {
                            Toast.makeText(requireContext(), "Copy selected", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (item.getItemId() == R.id.pop_up_delete) {
                            Toast.makeText(requireContext(), "Delete selected", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    // метод заполнения содержимого заметки
    private void displayNote(Note note) {
        noteName.setText(note.getName());
        noteDate.setText(note.getDate().toString());
        noteContent.setText(note.getContent());
    }
}
