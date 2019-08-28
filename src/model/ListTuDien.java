package model;

import java.util.ArrayList;
import java.util.List;

public class ListTuDien {
    List<TuDien> listTuDien;

    public ListTuDien(){
        listTuDien = new ArrayList<>();
    }

    public ListTuDien(ArrayList<TuDien> listTuDien){
        this.listTuDien = listTuDien;
    }

    public List<TuDien> getListTuDien() {
        return listTuDien;
    }

    public void setListTuDien(List<TuDien> listTuDien) {
        this.listTuDien = listTuDien;
    }

    public boolean contains(String ten){
        for (TuDien x : listTuDien){
            if(x.getTen().equalsIgnoreCase(ten)) return true;
        }
        return false;
    }

    public boolean addNew(TuDien newTuDien){
        if(contains(newTuDien.getTen())) return false;
        listTuDien.add(newTuDien);
        return true;
    }

    public void show(){
        for(TuDien x : listTuDien){
            System.out.println("---------------");
            x.show();
            System.out.println("---------------");
        }
    }
}
