package com.quotation.quo.quot;

import java.util.ArrayList;

/**
 * Created by Rezeq on 4/6/2018.
 * Email : rezeq.elewa@gmail.com
 */
public class ImagesResponse {
    boolean status;
    ArrayList<Image> data;

    public ImagesResponse() {
    }

    public ImagesResponse(boolean status, ArrayList<Image> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Image> getData() {
        return data;
    }

    public void setData(ArrayList<Image> data) {
        this.data = data;
    }
}
