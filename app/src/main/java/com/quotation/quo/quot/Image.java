package com.quotation.quo.quot;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Image implements Parcelable{
    private int id;
    private String imageUrl;
    private String textAr;
    private String textEn;
    private String fullTextAr;
    private String fullTextEn;

    public Image() {
    }

    public Image(int id, String imageUrl, String textAr, String textEn) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.textAr = textAr;
        this.textEn = textEn;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        imageUrl = in.readString();
        textAr = in.readString();
        textEn = in.readString();
        fullTextAr = in.readString();
        fullTextEn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageUrl);
        dest.writeString(textAr);
        dest.writeString(textEn);
        dest.writeString(fullTextAr);
        dest.writeString(fullTextEn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTextAr() {
        return textAr;
    }

    public void setTextAr(String textAr) {
        this.textAr = textAr;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getFullTextAr() {
        return fullTextAr;
    }

    public void setFullTextAr(String fullTextAr) {
        this.fullTextAr = fullTextAr;
    }

    public String getFullTextEn() {
        return fullTextEn;
    }

    public void setFullTextEn(String fullTextEn) {
        this.fullTextEn = fullTextEn;
    }
}
