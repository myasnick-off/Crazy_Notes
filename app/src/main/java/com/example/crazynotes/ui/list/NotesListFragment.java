package com.example.crazynotes.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private RecyclerView container;
    private ProgressBar progressBar;
    private NotesListAdapter adapter = new NotesListAdapter();
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
        progressBar = view.findViewById(R.id.progress_circular);

        adapter.setListener(new NotesListAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClicked(Note note) {
                if (onNoteClickedContext != null) {
                    onNoteClickedContext.noteOnClicked(note);
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_NOTE, note);
                getParentFragmentManager().setFragmentResult(KEY_SELECTED_NOTE, bundle);
            }
        });

        container = view.findViewById(R.id.root);
        container.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,
                false));
        container.setAdapter(adapter);
        presenter.notesRequest();

    }

    @Override
    public void showNotes(List<Note> notesList) {
        adapter.setData(notesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
