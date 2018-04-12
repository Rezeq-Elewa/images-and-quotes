package com.quotation.quo.quot;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Image implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("text_ar")
    private String textAr;

    @SerializedName("text_en")
    private String textEn;

    @SerializedName("description_ar")
    private String fullTextAr;

    @SerializedName("description_en")
    private String fullTextEn;

    public Image() {
    }

    public Image(int id, int categoryId, String imageUrl, String textAr, String textEn, String fullTextAr, String fullTextEn) {
        this.id = id;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.textAr = textAr;
        this.textEn = textEn;
        this.fullTextAr = fullTextAr;
        this.fullTextEn = fullTextEn;
    }

    protected Image(Parcel in) {
        id = in.readInt();
        categoryId = in.readInt();
        imageUrl = in.readString();
        textAr = in.readString();
        textEn = in.readString();
        fullTextAr = in.readString();
        fullTextEn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(categoryId);
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getText(){
        if(MyApplication.language.equalsIgnoreCase("ar")){
            return getTextAr();
        } else {
            return getTextEn();
        }
    }

    public String getFullText(){
        if(MyApplication.language.equalsIgnoreCase("ar")){
            return getFullTextAr();
        } else {
            return getFullTextEn();
        }
    }
}
