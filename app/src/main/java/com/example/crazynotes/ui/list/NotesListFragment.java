package com.example.crazynotes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.FireStoreNotesRepository;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.Router;
import com.example.crazynotes.ui.RouterHolder;
import com.example.crazynotes.ui.details.NoteEditFragment;
import com.example.crazynotes.ui.dialog.DeleteDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String KEY_SELECTED_NOTE = "SELECTED_NOTE";

    private NotesListPresenter presenter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NotesListAdapter adapter;
    private Note selectedNote;
    private Router router;

    private boolean isListRequested;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // передаем в презентор данный фрагмент и новый экземпляр репозитория
        presenter = new NotesListPresenter(this, new FireStoreNotesRepository());
        // передаем в адаптер данный фрагмент
        adapter = new NotesListAdapter(this);

        if (getParentFragment() instanceof RouterHolder) {
            router = ((RouterHolder) getParentFragment()).getRouter();
        }

        // разрешаем фрагменту свое меню в ToolBar
        setHasOptionsMenu(true);
    }

    // создание основного меню
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        // реализация работы виджета SearchView
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(requireContext(), "Text submitted", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // создание контекстного меню
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_context, menu);
    }

    // обработка выбора пунктов основного меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            if (router != null) {
                router.showNoteEdit(null);
            }
            return true;
        }
        // сюда добавить обработку пунктов основного меню, если будут еще
        return super.onOptionsItemSelected(item);
    }

    // обработка выбора пунктов из контекстного меню
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.context_delete) {      // выбрано удаление
            showDeleteDialog();
            return true;
        }
        if (item.getItemId() == R.id.context_update) {      // выбрано редактирование
            if (router != null) {
                router.showNoteEdit(selectedNote);
            }
            return true;
        }
        if (item.getItemId() == R.id.context_copy) {        // выбрано копирование
            presenter.addNewNote(selectedNote);
            return true;
        }
        return super.onContextItemSelected(item);
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

        if (savedInstanceState != null) {
            selectedNote = savedInstanceState.getParcelable(KEY_SELECTED_NOTE);
        }

        // Получаем результат от фрагмента для редактирования заметки
        getParentFragmentManager().setFragmentResultListener(NoteEditFragment.KEY_NOTE_RES, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(NoteEditFragment.KEY_NOTE_EDIT);
                boolean isNewNote = result.getBoolean(NoteEditFragment.KEY_IS_NEW);
                if (isNewNote) {
                    // если заметка новая, добавляем ее в список
                    presenter.addNewNote(note);
                } else {
                    // если заметка уже существует, обновляем ее
                    presenter.updateNote(note);
                }
            }
        });

        // Получаем результат от диалогового окна удаления
        getChildFragmentManager().setFragmentResultListener(DeleteDialogFragment.KEY_DEL_DIALOG, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                boolean isOkPressed = result.getBoolean(DeleteDialogFragment.ARG_OK);
                if (isOkPressed) {
                    // если была нажата кнопка "ОК", говорим презентору удалить эту заметку
                    presenter.removeNote(selectedNote);
                }
            }
        });

        adapter.setListener(new NotesListAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClicked(Note note) {
                if (router != null) {
                    router.showNoteContent(note);
                }
            }

            @Override
            public void onNoteLongClicked(Note note) {
                selectedNote = note;
            }
        });

        recyclerView = view.findViewById(R.id.notes_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(adapter);

        if (!isListRequested) {
            presenter.notesRequest();
            isListRequested = true;
        }

    }

    @Override
    public void showNotes(List<Note> notesList) {
        adapter.submitList(notesList);
    }

    @Override
    public void scrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    // Метод вызова диалогового окна с подтверждением удаления выбранной заметки
    private void showDeleteDialog() {
        new DeleteDialogFragment().show(getChildFragmentManager(), "deleteDialogFragment");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // сохраняем выбранную заметку (нужно при повороте экрна с вызванным диалоговым окном удаления)
        outState.putParcelable(KEY_SELECTED_NOTE, selectedNote);
        super.onSaveInstanceState(outState);
    }
}
