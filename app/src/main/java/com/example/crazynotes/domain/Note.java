package com.example.crazynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {

    private static final String[] IMG_URLS = {
            "https://i.ytimg.com/vi/j1HomFU9GAA/maxresdefault.jpg",
            "https://pbs.twimg.com/media/Eg6kMbfWAAY3cpW.jpg",
            "https://ufaved.info/upload/iblock/4f8/4f89d3dff002302d68645b8f2a303700.jpg",
            "https://pbs.twimg.com/media/EjmcgpeVkAAE2TZ.jpg",
            "https://secure.meetupstatic.com/photos/event/7/b/9/e/600_482911646.jpeg",
            "https://pbs.twimg.com/media/EvF3lTIWQAYpOOb.jpg"};

    private String id;
    private String name;
    private String content;
    private String imgUrl;
    private Date date;

    public Note() {
        this.name = "Empty note";
        this.content = "";
        int index = (int) Math.round(Math.random() * (IMG_URLS.length-1));
        this.imgUrl = IMG_URLS[index];
        this.date = new Date();
    }

    public Note(String id, String name, String content, String imgUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.date = new Date();
    }

    public Note(String id, String name, String content, String imgUrl, Date date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    protected Note(Parcel in) {
        id = in.readString();
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

    public String getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
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
        return Objects.equals(id, note.id) &&
                Objects.equals(name, note.name) &&
                Objects.equals(content, note.content) &&
                Objects.equals(imgUrl, note.imgUrl) &&
                Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, imgUrl, date);
    }

    @NonNull
    @Override
    public Note clone() throws CloneNotSupportedException {
        Note clone = new Note(this.id, this.name, this.content, this.imgUrl);
        return clone;
    }
}
