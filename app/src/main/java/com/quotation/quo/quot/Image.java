package com.quotation.quo.quot;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Image implements Parcelable{
    private int id;
    private String imageURL;
    private String textAr;
    private String textEn;

    public Image() {
    }

    public Image(int id, String imageURL, String textAr, String textEn) {
        this.id = id;
        this.imageURL = imageURL;
        this.textAr = textAr;
        this.textEn = textEn;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        imageURL = in.readString();
        textAr = in.readString();
        textEn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imageURL);
        dest.writeString(textAr);
        dest.writeString(textEn);
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
}
