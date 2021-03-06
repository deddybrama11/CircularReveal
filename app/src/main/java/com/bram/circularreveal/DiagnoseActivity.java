package com.bram.circularreveal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bram.circularreveal.Retrofit.IUploadAPI;
import com.bram.circularreveal.Retrofit.RetrofitClient;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DiagnoseActivity extends AppCompatActivity {
    IUploadAPI mService;

    private static final String TAG = "DiagnoseActivity";
    private RecyclerViewAdapter radapter;
    //vars

    private ArrayList<Getter> mImageUrls = new ArrayList<>();
    private TextView kd, deskrp, pencegahan,tulisan;
    String kda;
    private  String newString;

    private IUploadAPI getAPIUpload(){
        return RetrofitClient.getClientGson().create(IUploadAPI.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        mService = getAPIUpload();
        kd = (TextView)findViewById(R.id.nama_penyakit);
        deskrp = (TextView)findViewById(R.id.deskripsi_diagnosa);
        pencegahan = (TextView) findViewById(R.id.solusi);
        tulisan = findViewById(R.id.tulisanAtas);

        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                if(extras.getString("koini")!= null){
                    newString = extras.getString("koini");
                    Log.d(TAG, "extras diagnoseactivity: "+newString);
                }else if(extras.getString("daftar_penyakit")!=null) {
                    tulisan.setText("");
                    newString = extras.getString("daftar_penyakit");
                    Log.d(TAG, "extras diagnoseactivity: " + newString);
                }
            }
        }
        TextView thasil_diagnosa;
        getData();
    }

    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mService.view(Integer.parseInt(newString)).enqueue(new Callback<Getter>() {
                    @Override
                    public void onResponse(Call<Getter> call, Response<Getter> response) {
                        if(response.isSuccessful()){
                            getImages();
                            kd.setText(response.body().getPenyakit());
                            deskrp.setText(response.body().getDeskripsi());
                            pencegahan.setText(response.body().getPencegahan());


                        }
                    }

                    @Override
                    public void onFailure(Call<Getter> call, Throwable t) {

                    }
                });
            }
        }).start();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.5:5000")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        IUploadAPI request = retrofit.create(IUploadAPI.class);
//        Call<Getter> call = request.view(Integer.parseInt(newString));
//        call.enqueue(new Callback<Getter>() {
//            @Override
//            public void onResponse(Call<Getter> call, Response<Getter> response) {
//                if(response.isSuccessful()){
//                    getImages();
//                    Toast.makeText(DiagnoseActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
//                    kd.setText(response.body().getPenyakit());
//                    deskrp.setText(response.body().getDeskripsi());
//                    pencegahan.setText(response.body().getPencegahan());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Getter> call, Throwable t) {
//                Toast.makeText(DiagnoseActivity.this, "gagal", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    public void getImages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mService.getImage(Integer.parseInt(newString)).enqueue(new Callback<List<Getter>>() {
                    @Override
                    public void onResponse(Call<List<Getter>> call, Response<List<Getter>> response) {
                        if(response.isSuccessful()){
                            mImageUrls = new ArrayList<>(response.body());
                            initRecyclerView(mImageUrls);

                        }
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
//        IUploadAPI request =  retrofit.create(IUploadAPI.class);
//        Call<List<Getter>>  call = request.getImage(Integer.parseInt(newString));
//        call.enqueue(new Callback<List<Getter>>() {
//            @Override
//            public void onResponse(Call<List<Getter>> call, Response<List<Getter>> response) {
//                 if(response.isSuccessful()){
//                     Toast.makeText(DiagnoseActivity.this, "sukses gambar", Toast.LENGTH_SHORT).show();
//                     mImageUrls = new ArrayList<>(response.body());
//                     initRecyclerView(mImageUrls);
//
//                 }
//            }
//
//            @Override
//            public void onFailure(Call<List<Getter>> call, Throwable t) {
////                Toast.makeText(DiagnoseActivity.this, "gagal gambar", Toast.LENGTH_SHORT).show();
//            }
//        });


//        Log.d(TAG, "initImageBitmaps: preparing bitmap");



    }

    private void initRecyclerView(ArrayList<Getter> gambar){
        Log.d(TAG, "initRecyclerView:  ini recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, gambar);
        recyclerView.setAdapter(adapter);
    }
}
