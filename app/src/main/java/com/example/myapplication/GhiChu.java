package com.example.myapplication;

import java.io.Serializable;

public class GhiChu implements Serializable {
    private String tieude, ngaythang, noidung, gio, tgiannhap;
    //    public GhiChu(String tieude, String ngay, String noidung) {
//    }

    public GhiChu() {
    }
    //Cũ
//    public GhiChu( String tieude, String ngaythang, String noidung) {
//        this.tieude = tieude;
//        this.ngaythang = ngaythang;
//        this.noidung = noidung;
//    }

    //Lưu thêm giờ

    public GhiChu(String tieude, String ngaythang, String noidung, String gio, String tgiannhap) {
        this.tieude = tieude;
        this.ngaythang = ngaythang;
        this.noidung = noidung;
        this.gio = gio;
        this.tgiannhap = tgiannhap;
    }

    /*public GhiChu( String tieude, String ngaythang, String noidung, String gio) {
        this.tieude = tieude;
        this.ngaythang = ngaythang;
        this.noidung = noidung;
        this.gio = gio;
    }*/

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getTgiannhap() {
        return tgiannhap;
    }

    public void setTgiannhap(String tgiannhap) {
        this.tgiannhap = tgiannhap;
    }
}
