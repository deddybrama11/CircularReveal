package com.bram.circularreveal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bram.circularreveal.Utils.iUploadCallBacks;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bram.circularreveal.Retrofit.IUploadAPI;
import com.bram.circularreveal.Retrofit.RetrofitClient;
import com.bram.circularreveal.Utils.Common;
import com.bram.circularreveal.Utils.ProgressRequestBody;
import com.bram.circularreveal.Utils.iUploadCallBacks;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements iUploadCallBacks {
    private static final int PICK_FILE_REQUEST = 1000;

    IUploadAPI mService;
    Button btnUpload;
    ImageView imageView;
    Uri selectedFileUri;
    public String kode;

    ProgressDialog dialog;

    private IUploadAPI getAPIUpload(){
        return RetrofitClient.getClient().create(IUploadAPI.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home,container,false);
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(getActivity(),"Permission accpeted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getActivity(),"you should accept permision", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        //create m Service
        mService = getAPIUpload();

        //initview
        btnUpload = view.findViewById(R.id.btn_upload);
        imageView = view.findViewById(R.id.image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity().getApplicationContext(), DiagnoseActivity.class);
                        Log.d(TAG, "onResponseeeeee3: "+kode);
                        i.putExtra("koini", kode);
                        startActivity(i);
                    }
                },500);



            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == PICK_FILE_REQUEST)
            {
                if(data != null)
                {
                    selectedFileUri = data.getData();
                    if(selectedFileUri != null && !selectedFileUri.getPath().isEmpty())
                        imageView.setImageURI(selectedFileUri);
                    else
                        Toast.makeText(getActivity(),"File not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadFile() {
        if(selectedFileUri!=null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Uploading...");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setCancelable(false);
            dialog.show();
            File file = null;

            try {
                file = new File(Common.getFilePath(getActivity(), selectedFileUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (file != null) {
                String setering;
                final ProgressRequestBody requestBody = new ProgressRequestBody(file, this);

                final MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mService.uploadFile(body)
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        kode =  ""+response.body();
                                        Log.d(TAG, "onResponseeeeee2: "+kode);
                                        dialog.dismiss();

//                                        String image_processed_link = new StringBuilder("http://192.168.1.3:5000/" +
//                                                response.body().replace("\"", "")).toString();
//                                        System.out.print("image_processed_link == "+image_processed_link);

//                                        Picasso.get().load(image_processed_link)
//                                                .into(imageView);
//                                        Log.d(TAG, "onResponseeeeee: "+image_processed_link);


//                                        Toast.makeText(getActivity(), "Detected!!!", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }).start();

            }
        }
        else
        {
            Toast.makeText(getActivity(),"Cannot upload this file!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_FILE_REQUEST);
    }

    @Override
    public void onProgressUpdate(int percent) {

    }
}
