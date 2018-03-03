package com.quotation.quo.quot;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rezeq on 3/2/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class App implements Parcelable{
    private String name;
    private String descriptionEn;
    private String descriptionAr;
    private String img;
    private String url;
    private String packageId;

    public App() {
    }

    public App(String name, String descriptionEn, String descriptionAr, String img, String url, String packageId) {
        this.name = name;
        this.descriptionEn = descriptionEn;
        this.descriptionAr = descriptionAr;
        this.img = img;
        this.url = url;
        this.packageId = packageId;
    }

    protected App(Parcel in) {
        name = in.readString();
        descriptionEn = in.readString();
        descriptionAr = in.readString();
        img = in.readString();
        url = in.readString();
        packageId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(descriptionEn);
        dest.writeString(descriptionAr);
        dest.writeString(img);
        dest.writeString(url);
        dest.writeString(packageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<App> CREATOR = new Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
