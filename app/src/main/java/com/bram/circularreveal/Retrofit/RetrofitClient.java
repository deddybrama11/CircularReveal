package com.bram.circularreveal.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitClient;

    public static Retrofit getClient()
    {
        if(retrofitClient == null)
        {
            retrofitClient= new Retrofit.Builder()
                    .baseUrl("http://192.168.1.3:5000")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofitClient;
    }
}
