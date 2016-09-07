package qgpapplications.com.br.fasttask.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by c1284521 on 09/11/2015.
 */
public class Task implements Parcelable {

    private Long id;
    private String day;
    private String month;
    private String year;
    private String message;
    private String photo;


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id == null? -1 : this.id);
        dest.writeString(this.day == null ? "" : this.day);
        dest.writeString(this.month == null? "" : this.month);
        dest.writeString(this.year == null? "" : this.year);
        dest.writeString(this.message == null?"": this.message);
        dest.writeString(this.photo == null?"": this.photo);
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.day = in.readString();
        this.month = in.readString();
        this.year = in.readString();
        this.message = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public String toString() {
        return message;
    }
}


