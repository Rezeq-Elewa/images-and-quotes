package com.quotation.quo.quot;

import java.io.Serializable;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Image implements Serializable{
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
