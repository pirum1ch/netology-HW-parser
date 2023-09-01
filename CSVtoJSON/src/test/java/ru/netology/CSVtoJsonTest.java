package ru.netology;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CSVtoJsonTest {

    @Test
    void main() {
    }

    @Test
    void csvToBean() {
        String csvFileName = "src/test/resources/data.csv";
        List<Employee> list = new Main().csvToBean(csvFileName);

        //Проверяем что данные из файла корректно перенеслись в лист
        assertThat(list.stream().map(Employee::getFirstName).collect(Collectors.toList()), contains("John", "Ivan"));
        assertThat(list.stream().map(Employee::getLastName).collect(Collectors.toList()), contains("Smith", "Petrov"));

        //Проверяем что метод toString класса Employee возвращает корректную строку
        assertThat(list, hasItem(hasToString("1 John Smith USA 25")));
    }

    @Test
    void listToJSON() {
        //given
        String jsonFileName = "src/test/resources/new_json.json";
        List<Employee> reference = new ArrayList<>();
        reference.add(0, new Employee(1, "John", "Smith", "USA", 25));
        reference.add(1, new Employee(2, "Ivan", "Petrov", "RU", 23));

        //when
        Gson gson = new Gson();
        List<Employee> list;

        try (FileReader reader = new FileReader(jsonFileName)) {
            Employee[] employee = gson.fromJson(reader, Employee[].class);
            list = Arrays.stream(employee).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //than
        Assertions.assertEquals(list, reference);
    }
}