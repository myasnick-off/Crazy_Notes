package com.example.crazynotes.ui.details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;
import com.example.crazynotes.domain.FireStoreNotesRepository;
import com.example.crazynotes.domain.Note;
import com.example.crazynotes.ui.dialog.ImageDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class NoteEditFragment extends Fragment {

    public static final String KEY_NOTE_EDIT = "KEY_NOTE_EDIT";
    public static final String KEY_IS_NEW = "KEY_IS_NEW";
    public static final String KEY_NOTE_RES = "KEY_NOTE_RES";

    private EditText noteNameEdit;
    private EditText noteContentEdit;
    private TextView noteDateEdit;
    private ImageView noteImage;

    private Note note;
    private String imgUrl;
    private Calendar calendar;
    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private boolean isNewNote;

    public NoteEditFragment() {
    }

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
        noteImage = view.findViewById(R.id.img_note);
        calendar = Calendar.getInstance();

        // Если открыта существующая заметка для редактирования, загружаем ее данные из Bundle
        if (!requireArguments().isEmpty()) {
            note = requireArguments().getParcelable(KEY_NOTE_EDIT);
            imgUrl = note.getImgUrl();
            displayNote(note);
            initDate(note);
        } else {
            // если создается новая заметка:
            note = new Note();
            initDate(note);
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

        // обработка нажатия кнопки выбора картинки
        noteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageDialogFragment().show(getChildFragmentManager(), "ImageDialogFragment");
            }
        });

        // получаем результат от диалогового окна выбора картинки
        getChildFragmentManager().setFragmentResultListener(ImageDialogFragment.KEY_IMG, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                imgUrl = result.getString(ImageDialogFragment.ARG_IMG);
                Glide.with(noteImage).load(imgUrl).into(noteImage);
                noteImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        });

        // обработка нажатия кнопки "Сохранить"
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note.setName(noteNameEdit.getText().toString());
                note.setDate(calendar.getTime());
                note.setContent(noteContentEdit.getText().toString());
                note.setImgUrl(imgUrl);

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

    private void displayNote(Note note) {
        noteNameEdit.setText(note.getName());
        noteContentEdit.setText(note.getContent());
        Glide.with(noteImage).load(imgUrl).into(noteImage);
        noteImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    // метод инициализации полей даты и вывода даты в текстовом поле
    @SuppressLint("SetTextI18n")
    private void initDate(Note note) {
        calendar.setTime(note.getDate());
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        noteDateEdit.setText(currentDay + "." + (currentMonth + 1) + "." + currentYear);
    }
}