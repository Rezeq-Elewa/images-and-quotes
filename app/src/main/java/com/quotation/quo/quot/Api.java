package com.quotation.quo.quot;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rezeq on 4/6/2018.
 * Email : rezeq.elewa@gmail.com
 */
public class Api {

    private static Api instance;
    private ApiInterface client;

    private Api() {
        String API_BASE_URL = "http://www.rezeq-elewa.com/osama/";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        client = retrofit.create(ApiInterface.class);
    }

    public static Api getInstance(){
        if (instance == null)
            instance = new Api();
        return instance;
    }

    public void getImagesPage(int page, String order, final ApiCallback callback) {
        Call<ImagesResponse> mCall = client.allImagesPage(page, order);
        mCall.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImagesResponse> call, @NonNull Response<ImagesResponse> response) {

                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("unknown error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImagesResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getCategoryImagesPage(int categoryId, int page, String order, final ApiCallback callback) {
        Call<ImagesResponse> mCall = client.categoryImagesPage(categoryId, page, order);
        mCall.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImagesResponse> call, @NonNull Response<ImagesResponse> response) {

                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("unknown error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImagesResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getApps(final ApiCallback callback) {
        Call<AppsResponse> mCall = client.allApps();
        mCall.enqueue(new Callback<AppsResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppsResponse> call, @NonNull Response<AppsResponse> response) {

                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("unknown error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AppsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getCategories(final ApiCallback callback) {
        Call<CategoriesResponse> mCall = client.allCategories();
        mCall.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {

                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("unknown error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
