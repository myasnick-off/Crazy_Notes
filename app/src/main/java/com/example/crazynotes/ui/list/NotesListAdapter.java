package com.example.crazynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    // Интерфейс для обработки событий нажатия на элементы списка
    interface OnNoteClickedListener {

        void onNoteClicked(Note note);

        void onNoteLongClicked(Note note);
    }

    private final List<Note> data = new ArrayList<>();
    private OnNoteClickedListener listener;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    private final Fragment fragment;

    public NotesListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    // геттер для listener
    public OnNoteClickedListener getListener() {
        return listener;
    }

    // сеттер для listener
    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesListAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(noteItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.NotesViewHolder holder, int position) {
        Note note = data.get(position);

        holder.getNoteName().setText(note.getName());
        holder.getNoteDate().setText(dateFormatter.format(note.getDate()));
        Glide.with(holder.getNoteImage()).load(note.getImgUrl()).into(holder.getNoteImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // метод загрузки данных из репозитория во внутренний список
    public void setData(List<Note> noteList) {
        data.clear();
        this.data.addAll(noteList);
    }

    // метод добавления новой заметки в текущий список
    public void addNoteToList(Note note) {
//        data.add(note);
    }

    // метод редактирования выбранной заметки в текущем списке
    public int updateNote(Note note) {
        int index = data.indexOf(note);
            data.set(index, note);          // заменяем ее на новую
            notifyItemChanged(index);
        return index;
    }

    // метод копирования выбранной заметки в текущем списке
    public int copySelectedNote(Note note) {
        int index = data.indexOf(note);
        index++;
        try {
            Note copy = note.clone();
            data.add(index, copy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return index;
    }

    // метод удаления заметки из текущего списка
    public int removeNote(Note note) {
        int index = data.indexOf(note);
        data.remove(index);
        return index;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteName;
        private final TextView noteDate;
        private final ImageView noteImage;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            // регистрируем элемент списка для контекстного меню
            fragment.registerForContextMenu(itemView);

            // отрабатываем короткое нажатие не элемент списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onNoteClicked(data.get(getAdapterPosition()));
                    }
                }
            });

            // отрабатываем длинное нажатие не элемент списка
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();         // показываем контекстное меню
                    //
                    if (getListener() != null) {
                        getListener().onNoteLongClicked(data.get(getAdapterPosition()));
                    }
                    return true;
                }
            });

            noteName = itemView.findViewById(R.id.note_text_view);
            noteDate = itemView.findViewById(R.id.note_date_view);
            noteImage = itemView.findViewById(R.id.note_image);
        }

        public TextView getNoteName() {
            return noteName;
        }

        public TextView getNoteDate() {
            return noteDate;
        }

        public ImageView getNoteImage() {
            return noteImage;
        }
    }
}
