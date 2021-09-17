package com.example.crazynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {

    @StringRes
    private int id;
    private String name;
    private String content;
    private String imgUrl;
    private Date date;

    public Note(int id, String name, String content, String imgUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.date = new Date();
    }

    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readString();
        content = in.readString();
        imgUrl = in.readString();
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

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeString(imgUrl);
        dest.writeLong(date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                Objects.equals(name, note.name) &&
                Objects.equals(content, note.content) &&
                Objects.equals(imgUrl, note.imgUrl) &&
                Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, imgUrl, date);
    }
}
