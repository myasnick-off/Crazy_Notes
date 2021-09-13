package com.example.crazynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Date;

public class Note implements Parcelable {

    @StringRes
    private int id;
    private int name;
    private int content;
    private String imgUrl;
    private Date date;

    public Note(int id, int name, int content, String imgUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.date = new Date();
    }

    protected Note(Parcel in) {
        id = in.readInt();
        name = in.readInt();
        content = in.readInt();
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

    public int getName() {
        return name;
    }

    public int getContent() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(name);
        dest.writeInt(content);
        dest.writeString(imgUrl);
        dest.writeLong(date.getTime());
    }
}
