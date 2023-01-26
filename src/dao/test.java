package dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import entities.Anwsers;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            Anwsers anw = gson.fromJson(new JsonReader(new FileReader("src/resources/anwsers.json")),Anwsers.class);
            System.out.println(anw);
        }catch(FileNotFoundException e){
            System.out.println("zla sciezka");
        }
    }
}
