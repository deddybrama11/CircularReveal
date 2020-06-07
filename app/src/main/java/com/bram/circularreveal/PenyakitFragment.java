package com.bram.circularreveal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bram.circularreveal.Retrofit.IUploadAPI;
import com.bram.circularreveal.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class PenyakitFragment extends AppCompatActivity {

    private static final String TAG = "PenyakitFragment";
    private ArrayList<Getter> mKodePenyakit = new ArrayList<>();
    TextView namapenyakit, kodepenyakit;
    IUploadAPI mService;

    private IUploadAPI getAPIUpload() {
        return RetrofitClient.getClientGson().create(IUploadAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyakit_fragment);
        Log.d(TAG, "MASU AKTIFITI: ");
        getData();


    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mService.getListPenyakit().enqueue(new Callback<List<Getter>>() {
                    @Override
                    public void onResponse(Call<List<Getter>> call, Response<List<Getter>> response) {
                        mKodePenyakit = new ArrayList<>(response.body());
                        Log.d(TAG, "respon body mkodepenyakit: "+mKodePenyakit);
//                mNamaPenyakit = new ArrayList<>(response.body());
                        initRecyclerView(mKodePenyakit);
                    }

                    @Override
                    public void onFailure(Call<List<Getter>> call, Throwable t) {

                    }
                });
            }
        }).start();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.5:5000")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        IUploadAPI  request = retrofit.create(IUploadAPI.class);
//        Call<List<Getter>> call = request.getListPenyakit();
//        call.enqueue(new Callback<List<Getter>>() {
//            @Override
//            public void onResponse(Call<List<Getter>> call, Response<List<Getter>> response) {
//                Toast.makeText(PenyakitFragment.this, "masuk", Toast.LENGTH_LONG).show();
//                mKodePenyakit = new ArrayList<>(response.body());
//                Log.d(TAG, "respon body mkodepenyakit: "+mKodePenyakit);
////                mNamaPenyakit = new ArrayList<>(response.body());
//                initRecyclerView(mKodePenyakit);
//            }
//
//            @Override
//            public void onFailure(Call<List<Getter>> call, Throwable t) {
//                Toast.makeText(PenyakitFragment.this, "gagal gk masuk", Toast.LENGTH_LONG).show();
//
//            }
//        });



    }
    private void initRecyclerView(ArrayList<Getter> kodepenyakit){
        Log.d(TAG, "initRecyclerView:  ini recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.rpenyakit);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterPenyakit adapter = new RecyclerViewAdapterPenyakit(this, kodepenyakit);
        recyclerView.setAdapter(adapter);
    }
}
