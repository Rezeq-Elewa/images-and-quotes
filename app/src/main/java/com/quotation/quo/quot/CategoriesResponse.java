package com.quotation.quo.quot;

import java.util.ArrayList;

/**
 * Created by Rezeq on 4/10/2018.
 * Email : rezeq.elewa@gmail.com
 */
public class CategoriesResponse {
    private boolean status;
    private ArrayList<Section> data;

    public CategoriesResponse() {
    }

    public CategoriesResponse(boolean status, ArrayList<Section> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Section> getData() {
        return data;
    }

    public void setData(ArrayList<Section> data) {
        this.data = data;
    }
}
