package com.quotation.quo.quot;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rezeq on 3/2/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class App implements Parcelable{
    private String nameEn;
    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    private String img;
    private String url;
    private String packageId;

    public App() {
    }

    public App(String nameEn, String nameAr, String descriptionEn, String descriptionAr, String img, String url, String packageId) {
        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.descriptionEn = descriptionEn;
        this.descriptionAr = descriptionAr;
        this.img = img;
        this.url = url;
        this.packageId = packageId;
    }

    protected App(Parcel in) {
        nameEn = in.readString();
        nameAr = in.readString();
        descriptionEn = in.readString();
        descriptionAr = in.readString();
        img = in.readString();
        url = in.readString();
        packageId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameEn);
        dest.writeString(nameAr);
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
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

    public String getName() {
        if(MyApplication.language.equalsIgnoreCase("ar")){
            return getNameAr();
        } else {
            return getNameEn();
        }
    }

    public String getDescription(){
        if(MyApplication.language.equalsIgnoreCase("ar")){
            return getDescriptionAr();
        } else {
            return getDescriptionEn();
        }
    }
}
