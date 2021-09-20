package com.example.crazynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

import java.text.SimpleDateFormat;


public class NotesListAdapter extends ListAdapter<Note, NotesListAdapter.NotesViewHolder> {

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return newItem.getId().equals(oldItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return newItem.equals(oldItem);
        }
    };

    // Интерфейс для обработки событий нажатия на элементы списка
    interface OnNoteClickedListener {

        void onNoteClicked(Note note);

        void onNoteLongClicked(Note note);
    }

    private final Fragment fragment;
    private OnNoteClickedListener listener;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    public NotesListAdapter(Fragment fragment) {
        super(DIFF_CALLBACK);
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
        Note note = getCurrentList().get(position);

        // заполнение элемента списка данными заметки
        holder.getNoteName().setText(note.getName());
        holder.getNoteDate().setText(dateFormatter.format(note.getDate()));
        Glide.with(holder.getNoteImage()).load(note.getImgUrl()).into(holder.getNoteImage());
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteName;
        private final TextView noteDate;
        private final ImageView noteImage;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            noteName = itemView.findViewById(R.id.note_text_view);
            noteDate = itemView.findViewById(R.id.note_date_view);
            noteImage = itemView.findViewById(R.id.note_image);

            // регистрируем элемент списка для контекстного меню
            fragment.registerForContextMenu(itemView);

            // отрабатываем короткое нажатие не элемент списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onNoteClicked(getCurrentList().get(getAdapterPosition()));
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
                        getListener().onNoteLongClicked(getCurrentList().get(getAdapterPosition()));
                    }
                    return true;
                }
            });
        }

        // геттеры для вьюшек элемента сиска
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
