package com.example.crazynotes.ui.details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

import java.util.Calendar;

public class NoteEditFragment extends Fragment {

    public static final String KEY_NOTE_EDIT = "KEY_NOTE_EDIT";
    public static final String KEY_IS_NEW = "KEY_IS_NEW";
    public static final String KEY_NOTE_RES = "KEY_NOTE_RES";

    private EditText noteNameEdit;
    private EditText noteContentEdit;
    private TextView noteDateEdit;

    private Note note;
    private Calendar calendar;
    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private boolean isNewNote;

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
        if (note != null) {
            args.putParcelable(KEY_NOTE_EDIT, note);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        noteDateEdit = view.findViewById(R.id.edit_note_date);
        calendar = Calendar.getInstance();

        // Если открыта существующая заметка для редактирования, загружаем ее данные из Bundle
        if (!requireArguments().isEmpty()) {
            note = requireArguments().getParcelable(KEY_NOTE_EDIT);
            displayNote(note);
        } else {
            note = new Note();
            isNewNote = true;
        }

        // обработка нажатия на текстовое поле даты
        noteDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // инициализация DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                noteDateEdit.setText(dayOfMonth + "." + (month + 1) + "." + year);
                                calendar.set(year, month, dayOfMonth);
                            }
                        }, currentYear, currentMonth, currentDay);
                // запуск DatePickerDialog
                datePickerDialog.show();
            }
        });

        // обработка нажатия кнопки "Сохранить"
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setName(noteNameEdit.getText().toString());
                note.setDate(calendar.getTime());
                note.setContent(noteContentEdit.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_NOTE_EDIT, note);
                bundle.putBoolean(KEY_IS_NEW, isNewNote);

                getParentFragmentManager()
                        .setFragmentResult(KEY_NOTE_RES, bundle);

                // выходим из фрагмента
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayNote(Note note) {
        noteNameEdit.setText(note.getName());
        noteContentEdit.setText(note.getContent());
        initDate(note);
        noteDateEdit.setText(currentDay + "." + (currentMonth + 1) + "." + currentYear);
    }

    // метод инициализации полей даты
    private void initDate(Note note) {
        calendar.setTime(note.getDate());
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
    }
}