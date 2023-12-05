//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class Movie implements Parcelable {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    private String title, category, time, ages, languages, description, location, number, website,
            poster, header;

    //----------------------------------------------------------------------------------------------

    /*PARCELEABLE METHODS*/

    protected Movie(Parcel in) {
        title = in.readString();
        category = in.readString();
        time = in.readString();
        ages = in.readString();
        languages = in.readString();
        description = in.readString();
        location = in.readString();
        number = in.readString();
        website = in.readString();
        poster = in.readString();
        header = in.readString();
    } // END PARCELABLE CONSTRUCTOR

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    }; // END CREATOR

    @Override
    public int describeContents() {
        return 0;
    } // END DESCRIBE CONTENTS

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(time);
        dest.writeString(ages);
        dest.writeString(languages);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(number);
        dest.writeString(website);
        dest.writeString(poster);
        dest.writeString(header);
    } // END WRITE TO PARCEL

    //----------------------------------------------------------------------------------------------

    /*SET & GET METHODS*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------