package com.quotation.quo.quot;

/**
 * Created by Rezeq on 3/5/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class Section {
    private int id;
    private String sectionNameAr;
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
