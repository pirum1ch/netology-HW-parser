package ru.netology;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {

    private List<Employee> list;
    private static final String CSVDFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/CSVtoJSON/data.csv";
    private static final String JSONDFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/CSVtoJSON/new_json.json";
    private final String[] CSVCOLUMNMAPPING = {"id", "firstName", "lastName", "country", "age"};

    public static void main(String[] args) {
        Main main = new Main();
        main.csvToBean();
        main.listToJSON();
    }

    public void csvToBean() {
        if (new File(CSVDFILENAME).length() != 0) {

            try (CSVReader reader = new CSVReader(new FileReader(CSVDFILENAME))) {

                var strategy = new ColumnPositionMappingStrategy<Employee>();
                strategy.setType(Employee.class);
                strategy.setColumnMapping(CSVCOLUMNMAPPING);

                var csv = new CsvToBeanBuilder<Employee>(reader).withMappingStrategy(strategy).build();
                list = csv.parse();

                list.stream().map(Employee::toString).forEach(System.out::println);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("CSV Файл пустой");
        }
    }

    public void listToJSON() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();

        try (FileWriter fw = new FileWriter(JSONDFILENAME)) {
            String json = gson.toJson(list, listType);
            fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
