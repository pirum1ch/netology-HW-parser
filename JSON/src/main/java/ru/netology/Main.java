package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String JSONFILE = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/JSON/json.json";


    public static void main(String[] args) {
        Main main = new Main();
        List<Employee> list = main.jsonToList(main.readString());
        System.out.println(list);

    }

    public String readString() {
        try (BufferedReader br = new BufferedReader(new FileReader(JSONFILE))) {
            String s;
            String a = null;
            while ((s = br.readLine()) != null) {
                a = s;
            }
            return a;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> jsonToList(String string) {
        List<Employee> listEmployee = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonParser jsp = new JsonParser();
        JsonArray jsonArray = jsp.parse(string).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            Employee m = gson.fromJson(jsonArray.get(i), Employee.class);
            listEmployee.add(m);
        }
        return listEmployee;
    }
}