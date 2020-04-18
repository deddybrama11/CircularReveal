package com.bram.circularreveal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getter {
    String diagnosa;
    String deskripsi_diagnosa;

    public String getDiagnosa(){
        return diagnosa;
    }
    public String getDeskripsi_diagnosa(){
        return deskripsi_diagnosa;
    }


    @SerializedName("penyakit")
    @Expose
    private String penyakit;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("pencegahan")
    @Expose
    private String pencegahan;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getGambar() {
        return gambar;
    }

    public String getPencegahan(){
        return pencegahan;
    }
    public void setPencegahan(String pencegahan){
        this.pencegahan = pencegahan;
    }

    public String getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(String penyakit) {
        this.penyakit = penyakit;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String penyakit) {
        this.deskripsi = deskripsi;
    }

}
