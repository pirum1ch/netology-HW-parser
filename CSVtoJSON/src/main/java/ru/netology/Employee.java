package ru.netology;

public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
    }


    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + country + " " + age;
    }

    @Override
    public boolean equals(Object obj) {
        Employee otherEmployee = (Employee) obj;
        return id == ((Employee) obj).id
                && firstName.equalsIgnoreCase(((Employee) obj).firstName)
                && lastName.equalsIgnoreCase(((Employee) obj).lastName)
                && country.equals(((Employee) obj).country)
                && age == ((Employee) obj).age;
    }
}
