package com.example.crazynotes.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreNotesRepository implements NotesRepository {

    public static final String COLLECTION_NAME = "notes";
    public static final String NOTE_NAME = "name";
    public static final String NOTE_CONTENT = "content";
    public static final String NOTE_IMG_URL = "imgUrl";
    public static final String NOTE_DATE = "noteDate";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(COLLECTION_NAME)
                .orderBy(NOTE_DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Note> noteList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String id = document.getId();
                                String name = document.getString(NOTE_NAME);
                                String content = document.getString(NOTE_CONTENT);
                                String imgUrl = document.getString(NOTE_IMG_URL);
                                Date date = document.getDate(NOTE_DATE);

                                noteList.add(new Note(id, name, content, imgUrl, date));
                            }
                            callback.onSuccess(noteList);
                        } else {
                            // код в случае неудачи при загрузке данных
                        }
                    }
                });
    }

    @Override
    public void addNote(Note note, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(NOTE_NAME, note.getName());
        data.put(NOTE_CONTENT, note.getContent());
        data.put(NOTE_IMG_URL, note.getImgUrl());
        data.put(NOTE_DATE, note.getDate());

        db.collection(COLLECTION_NAME)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            String noteId = task.getResult().getId();
                            note.setId(noteId);
                            callback.onSuccess(note);
                        } else {
                            // код в случае неудачи при отправке данных
                        }
                    }
                });
    }

    @Override
    public void deleteNote(Note note, Callback<Note> callback) {
        db.collection(COLLECTION_NAME)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(null);
                    }
                });
    }

    @Override
    public void copyNote(Note note, Callback<Note> callback) {
        addNote(note, callback);
    }

    @Override
    public void updateNote(Note note, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(NOTE_NAME, note.getName());
        data.put(NOTE_CONTENT, note.getContent());
        data.put(NOTE_IMG_URL, note.getImgUrl());
        data.put(NOTE_DATE, note.getDate());

        db.collection(COLLECTION_NAME)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess(note);
                    }
                });
    }
}
