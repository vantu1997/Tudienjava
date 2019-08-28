package controller;

import model.ListTuDien;
import model.TuDien;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class FileController implements IFileController{
    private List<String> listUrl = new ArrayList<>();
    private final String filename = "url.txt";
    public static final String DICT_EXTENSION = "dict";

    public FileController(){
        readFile();
    }

    public List<String> getListUrl() {
        return listUrl;
    }

    public void setListUrl(List<String> listUrl) {
        this.listUrl = listUrl;
    }

    public boolean contains(String url){
        for(String x : listUrl){
            if(x.equalsIgnoreCase(url)) return true;
        }
        return false;
    }

    public void addURL(String url){
        if(contains(url)){
            System.out.println("URL da ton tai");
            return;
        }
        listUrl.add(url);
    }

    public String getFilename() {
        return filename;
    }

    public void readFile(){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String string;
            while((string = br.readLine()) != null){
                listUrl.add(string);
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found " + " - line 30");
            File file = new File(filename);
            try {
                file.createNewFile();
                System.out.println("Da tao file " + filename);
            } catch (IOException ex) {
                System.out.println("Khong the tao file " + filename);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " - line 32");
        }
    }

    public void writeFile(){
        while(true) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
                for (String x : listUrl) {
                    bw.write(x);
                    bw.newLine();
                }
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found " + " - line 55");
            } catch (IOException e) {
                System.out.println(e.getMessage() + " - line 57");
            }
        }
    }

    public void ghiTuDien(TuDien tuDien){
        try(BufferedWriter br = new BufferedWriter(new FileWriter(tuDien.getUrl()))){
            Map<String,String> map = tuDien.getDanhSach();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                br.write("#"+key);
                br.newLine();
                br.write(value.trim());
                br.newLine();
                br.write("+-*/");
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "File controller - line 87");
        }
    }

    public void readAllFile(ListTuDien listTuDien){
        for(int i =0; i<listUrl.size();i++){
            String url = listUrl.get(i);
            try{
                themTuDien(listTuDien,url);
            } catch (FileNotFoundException e) {
                System.out.println("Khong tim thay file " + url);
                listUrl.remove(i--);
            } catch (IOException e) {
                System.out.println(e.getMessage() + " - line 79");
            }
        }
        writeFile();
    }

    public void themTuDien(ListTuDien listTuDien,String url) throws FileNotFoundException,IOException{
        BufferedReader br = new BufferedReader(new FileReader(url));
        File file = new File(url);
        StringTokenizer token = new StringTokenizer(file.getName(),".");
        String name = token.nextToken();
        TreeMap<String,String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        String dong;
        StringBuffer tu = new StringBuffer();
        StringBuilder nghia = new StringBuilder();
        while((dong=br.readLine())!=null){
            if(dong.charAt(0) == '#'){
                tu.append(dong.substring(1));
            }
            else if(dong.equals("+-*/")){
                map.put(tu.toString(),nghia.toString());
                nghia.delete(0,nghia.length());
                tu.delete(0,tu.length());
            }else{
                nghia.append(dong+"\n");
            }
        }
        TuDien tuDien = new TuDien(map,name,url);
        if(!listTuDien.addNew(tuDien)){
            JOptionPane.showMessageDialog(null, "Tu dien da ton tai");
            br.close();
            return;
        }
        if(!listUrl.contains(url)){
            addURL(url);
            writeFile();
        }
        br.close();
    }
}
