package dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import entities.Anwsers;
import entities.Question;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AnwsersDAO {
    static private final Gson gson = new Gson();
    static private Anwsers anw;
        static public void load() throws FileNotFoundException {
            try {
                 anw = gson.fromJson(new JsonReader(new FileReader("src/resources/anwsers.json")), Anwsers.class);
            }catch(FileNotFoundException e){
                JOptionPane.showMessageDialog(null,"nie znaleziono pliku pod wskazaną ścieżką");
                throw e;
            }
        }
        static public Anwsers getAnw(){
            return anw;
        }
        static public ArrayList<Question> getAllQuestions(){
            return anw.getQuestions();
        }
        static public int getLastId(){
            return anw.getLastId();
        }
        static public void addQuestion(Question question) throws IOException {
            try {
                FileWriter fw = new FileWriter("src/resources/anwsers.json");
                anw.getQuestions().add(question);
                anw.setLastId(anw.getLastId()+1);
                gson.toJson(anw,fw);
                fw.close();
            }catch(IOException e){
                throw e;
            }
        }
        static public ArrayList<Question> searchByTags(String [] tags){
            ArrayList<ArrayList<Question>> arr = new ArrayList<>();
            ArrayList<Question> qw = anw.getQuestions();

            for (int i = 0; i < qw.size(); i++) {
                int j = 0;
                for (String tag : qw.get(i).getTags()) {

                    for (String s : tags) {
                        if(tag.contentEquals(s)) j++;
                        if(s.contentEquals(" "+tag)) j++;
                    }
                }
                while(arr.size() < j){
                    arr.add(new ArrayList<>());
                }
                if(j>=1){arr.get(j-1).add(qw.get(i));}
            }
            ArrayList<Question> temp = new ArrayList<>();

            for (int i = arr.size()-1; i >= 0; i--) {
                for (int i1 = 0; i1 < arr.get(i).size(); i1++) {
                    temp.add(arr.get(i).get(i1));
                }
            }
            return temp;
        }

    static public ArrayList<Question> searchByTags(String str){
            return searchByTags(str.split("\\s+"));
    }

    public static void main(String[] args) throws FileNotFoundException {

            load();
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String txt = scanner.nextLine();
                String[] str = {"drukarka"};
                ArrayList<Question> temp = searchByTags(txt);
                for (Question question : temp) {
                    System.out.println(question);
                }
            }
    }
}
