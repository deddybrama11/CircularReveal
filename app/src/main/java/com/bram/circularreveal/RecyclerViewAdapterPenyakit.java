package com.bram.circularreveal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterPenyakit extends RecyclerView.Adapter<RecyclerViewAdapterPenyakit.ViewHolder>{

    private static final String TAG = "RecyclerPenyakit";
    //vars
    private ArrayList<Getter> mNamaPenyakit = new ArrayList<>();
    private ArrayList<Getter> mKodePenyakit = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterPenyakit(Context mContext, ArrayList<Getter> mNamaPenyakit) {
        this.mNamaPenyakit = mNamaPenyakit;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitempenyakit,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.nama_penyakit.setText(mNamaPenyakit.get(position).getNamaPenyakit());

        holder.cdview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,mNamaPenyakit.get(position).getKodePenyakit().toString(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext, DiagnoseActivity.class);
                i.putExtra("daftar_penyakit", ""+mNamaPenyakit.get(position).getKodePenyakit().toString());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNamaPenyakit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama_penyakit;
        CardView cdview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_penyakit = itemView.findViewById(R.id.nama_penyakit);
            cdview = itemView.findViewById(R.id.cdview);

        }
    }
}

