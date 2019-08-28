package model;

import controller.FileController;

import java.util.Map;
import java.util.TreeMap;

public class TuDien {
    private Map<String,String> danhSach;
    private String ten;
    private String url;

    public TuDien(){
        danhSach = new TreeMap<>();
    }

    public TuDien(Map<String, String> danhSach, String ten){
        this.danhSach = danhSach;
        this.ten = ten;
        this.url = this.ten + "." + FileController.DICT_EXTENSION;
    }

    public TuDien(Map<String, String> danhSach, String ten, String url) {
        this.danhSach = danhSach;
        this.ten = ten;
        this.url = url;
    }

    public Map<String, String> getDanhSach() {
        return danhSach;
    }

    public void setDanhSach(Map<String, String> danhSach) {
        this.danhSach = danhSach;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void show(){
        System.out.println("TU DIEN: " + ten + " - " + url);
        danhSach.forEach((key,value)-> System.out.println("-----\n" + "Tu: " + key + "\nNghia:\n" + value + "\n-----"));
    }

    public void add(String tu, String nghia){
        danhSach.put(tu,nghia);
    }

}
