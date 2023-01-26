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
            ArrayList<Question> qw = anw.getQuestions();
            ArrayList<Integer> matchingTags = new ArrayList<>(qw.size());
            for (int i = 0; i < qw.size(); i++) {
                matchingTags.add(0);
                for (String tag : qw.get(i).getTags()) {
                    for (String s : tags) {
                        if(tag.contentEquals(s)) matchingTags.set(i,matchingTags.get(i)+1);
                        if(s.contentEquals(" "+tag)) matchingTags.set(i,matchingTags.get(i)+1);
                    }
                }
            }
            ArrayList<Question> temp = new ArrayList<>();
            for (int i = 0; i < matchingTags.size(); i++) {
                    if(matchingTags.get(i) > 0){
                        temp.add(qw.get(i));
                    }
            }
            return temp;
        }


    public static void main(String[] args) throws FileNotFoundException {

            load();
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String txt = scanner.nextLine();
                String[] str = {"drukarka"};
                ArrayList<Question> temp = searchByTags(txt.split("\\s+"));
                for (Question question : temp) {
                    System.out.println(question);
                }
            }
    }
}
