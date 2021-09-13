package com.example.crazynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crazynotes.R;
import com.example.crazynotes.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    private final List<Note> data = new ArrayList<>();
    private OnNoteClickedListener listener;

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    public void setData(List<Note> noteList) {
        data.clear();
        this.data.addAll(noteList);
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
        holder.getNoteDate().setText(note.getDate().toString());
        Glide.with(holder.getNoteImage()).load(note.getImgUrl()).into(holder.getNoteImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoteClickedListener {
        void onNoteClicked(Note note);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteName;
        private TextView noteDate;
        private ImageView noteImage;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onNoteClicked(data.get(getAdapterPosition()));
                    }
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
