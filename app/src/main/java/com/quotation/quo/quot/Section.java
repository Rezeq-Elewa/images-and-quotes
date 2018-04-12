package com.quotation.quo.quot;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rezeq on 3/5/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Section {

    @SerializedName("id")
    private int id;

    @SerializedName("name_ar")
    private String sectionNameAr;

    @SerializedName("name_en")
    private String sectionNameEn;

    public Section() {
    }

    public Section(int id, String sectionNameAr, String sectionNameEn) {
        this.id = id;
        this.sectionNameAr = sectionNameAr;
        this.sectionNameEn = sectionNameEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionNameAr() {
        return sectionNameAr;
    }

    public void setSectionNameAr(String sectionNameAr) {
        this.sectionNameAr = sectionNameAr;
    }

    public String getSectionNameEn() {
        return sectionNameEn;
    }

    public void setSectionNameEn(String sectionNameEn) {
        this.sectionNameEn = sectionNameEn;
    }

    public String getSectionName(){
        if(MyApplication.language.equalsIgnoreCase("ar")){
            return getSectionNameAr();
        } else {
            return getSectionNameEn();
        }
    }
}
