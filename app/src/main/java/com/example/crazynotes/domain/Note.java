package com.example.crazynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Date;

public class Note implements Parcelable {

    @StringRes
    private int name;
    private int content;
    private Date date;

    public Note(int name, int content) {
        this.name = name;
        this.content = content;
        this.date = new Date();
    }

    protected Note(Parcel in) {
        name = in.readInt();
        content = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getName() {
        return name;
    }

    public int getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(content);
        dest.writeLong(date.getTime());
    }
}
