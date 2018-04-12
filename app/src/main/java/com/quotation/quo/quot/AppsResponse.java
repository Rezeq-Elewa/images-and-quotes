package com.quotation.quo.quot;

import java.util.ArrayList;

/**
 * Created by Rezeq on 4/10/2018.
 * Email : rezeq.elewa@gmail.com
 */
public class AppsResponse {

    boolean status;
    ArrayList<App> data;

    public AppsResponse() {
    }

    public AppsResponse(boolean status, ArrayList<App> data) {
        this.status = status;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<App> getData() {
        return data;
    }

    public void setData(ArrayList<App> data) {
        this.data = data;
    }
}
