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

    static final String[] csvColumnName = {"id", "firstName", "lastName", "country", "age"};

    public static void main(String[] args) {
        final String csvFileName = "CSVtoJSON/src/main/resources/data.csv";
        final String jsonFileName = "CSVtoJSON/src/main/resources/new_json.json";

        Main main = new Main();
        List<Employee> list = main.csvToBean(csvFileName);
        list.stream().map(Employee::toString).forEach(System.out::println);
        main.listToJSON(list, jsonFileName);
    }

    public List<Employee> csvToBean(String csvFileName) {
        if (new File(csvFileName).length() != 0) {

            try (CSVReader reader = new CSVReader(new FileReader(csvFileName))) {

                var strategy = new ColumnPositionMappingStrategy<Employee>();
                strategy.setType(Employee.class);
                strategy.setColumnMapping(csvColumnName);

                var csv = new CsvToBeanBuilder<Employee>(reader).withMappingStrategy(strategy).build();

                return csv.parse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("CSV Файл пустой");
            return null;
        }
    }

    public void listToJSON(List<Employee> list, String jsonFileName) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();

        try (FileWriter fw = new FileWriter(jsonFileName)) {
            String json = gson.toJson(list, listType);
            fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
