package com.quotation.quo.quot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rezeq on 4/6/2018.
 * Email : rezeq.elewa@gmail.com
 */
public interface ApiInterface {

    @GET("category_image.php")
    Call<ImagesResponse> categoryImagesPage (
            @Query("category_id") int categoryId,
            @Query("page") int page,
            @Query("order") String order
    );

    @GET("image.php")
    Call<ImagesResponse> allImagesPage (
            @Query("page") int page,
            @Query("order") String order
    );

    @GET("app.php")
    Call<AppsResponse> allApps ();

    @GET("categories.php")
    Call<CategoriesResponse> allCategories ();


}
