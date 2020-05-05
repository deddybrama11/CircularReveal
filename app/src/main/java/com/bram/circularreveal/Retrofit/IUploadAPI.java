package com.bram.circularreveal.Retrofit;

import com.bram.circularreveal.Getter;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IUploadAPI {
    @Multipart
    @POST("/api/upload")
    Call<String> uploadFile(@Part MultipartBody.Part file);

    @GET("/api/diagnosa/{kode}")
    Call<Getter> view(@Path("kode") Integer kode);

    @GET("api/image/{img}")
    Call<List<Getter>> getImage(@Path("img") Integer kode);

    @GET("api/list_penyakit")
    Call<List<Getter>> getListPenyakit();
}
