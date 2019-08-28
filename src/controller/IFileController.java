package controller;

import model.ListTuDien;
import model.TuDien;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileController {
    void readFile();
    void writeFile();
    boolean contains(String url);
    void addURL(String url);
    void ghiTuDien(TuDien tuDien);
    void readAllFile(ListTuDien listTuDien);
    void themTuDien(ListTuDien listTuDien, String url)throws FileNotFoundException, IOException;
}
